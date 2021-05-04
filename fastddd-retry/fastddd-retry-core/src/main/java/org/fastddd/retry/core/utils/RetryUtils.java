package org.fastddd.retry.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.SystemException;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.retry.core.constants.RetryConstants;
import org.fastddd.retry.core.constants.RetryLauncher;
import org.fastddd.retry.core.strategy.RetryStrategyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

    public static Object doWithRetry(Invocation invocation) {

        if (!checkTransaction(invocation)) {
            LOGGER.info("transaction check failed, will not retry: {}", invocation);
            return null;
        }

        InvocationHelper.beforeInvoke(invocation);
        return RetryStrategyHelper.doWithRetry(invocation);
    }

    public static Retryable getRetryable(Invocation invocation) {
        return ReflectionUtils.getAnnotation(invocation.getMethod(), Retryable.class);
    }

    public static boolean canRetry(Retryable retryable, Throwable t, int retriedCount) {
        return retriedCount < retryable.maxAttempts() && retryForThrowable(retryable, t);
    }

    private static boolean retryForThrowable(Retryable retryable, Throwable t) {

        if (retryable.notRetryableForExceptions().length > 0) {
            Throwable current = t;
            while (current != null) {
                for (Class<?> notRetryableException : retryable.notRetryableForExceptions()) {
                    if (notRetryableException.isAssignableFrom(current.getClass())) {
                        return false;
                    }
                }
                current = current.getCause();
            }
        }
        return true;
    }

    private static boolean checkTransaction(Invocation invocation) {

        RetryLauncher retryLauncher = invocation.getContextValue(RetryConstants.RETRY_LAUNCHER, RetryLauncher.class);
        if (RetryLauncher.JOB != retryLauncher) {
            // only check for job retry
            return true;
        }

        Retryable retryable = getRetryable(invocation);
        if (StringUtils.isNotBlank(retryable.transactionCheckMethod())) {
            Object target = invocation.getTarget();
            if (Object.class != retryable.transactionCheckClass()) {
                target = FactoryBuilder.getFactory(BeanFactory.class).getBean(retryable.transactionCheckClass());
                if (target == null) {
                    throw new SystemException("Failed to find the instance for transactionCheckClass: " +
                            retryable.transactionCheckClass().getSimpleName());
                }
            }

            try {
                Method transactionCheckMethod = target.getClass().getDeclaredMethod(
                        retryable.transactionCheckMethod(), invocation.getMethod().getParameterTypes());
                if (transactionCheckMethod.getReturnType() != Boolean.class) {
                    throw new SystemException("TransactionCheckMethod: " + retryable.transactionCheckMethod() +
                            " should return boolean value");
                }
                return (boolean)ReflectionUtils.invokeMethod(transactionCheckMethod, target, invocation.getParams());
            } catch (NoSuchMethodException e) {
                throw new SystemException("Failed to find the method: " + retryable.transactionCheckMethod());
            }
        }

        return true;
    }

}
