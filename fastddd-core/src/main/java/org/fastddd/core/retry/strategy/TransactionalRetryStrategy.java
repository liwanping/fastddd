package org.fastddd.core.retry.strategy;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.ReflectionRuntimeException;
import org.fastddd.common.exception.SystemException;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.retry.constants.RetryConstants;
import org.fastddd.core.retry.constants.RetryLauncher;
import org.fastddd.core.retry.model.RetryContext;
import org.fastddd.core.retry.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class TransactionalRetryStrategy implements RetryStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalRetryStrategy.class);

    @Override
    public boolean isQualified(Retryable retryable) {
        return RetryMode.TRANSACTIONAL == retryable.mode();
    }

    @Override
    public Object doWithRetry(Invocation invocation) {

        if (!checkTransaction(invocation)) {
            LOGGER.info("transaction check failed, will not retry: {}", invocation);
            return null;
        }

        try {
            // call directly since the next retry will be triggered by the job
            return InvocationHelper.doInvoke(invocation);
        } catch (ReflectionRuntimeException ex) {
            RetryContext retryContext = invocation.getContextValue(RetryConstants.RETRY_CONTEXT_KEY, RetryContext.class);
            if (retryContext != null && RetryLauncher.WORKFLOW == retryContext.getRetryLauncher()) {
                Retryable retryable = RetryUtils.getRetryable(invocation);
                if (retryable.enableFastRetry()) {
                    LOGGER.warn("Invocation execution failed but fast retry enabled, will retry immediately, invocation:{}", invocation);
                    return InvocationHelper.doInvoke(invocation);
                }
            }
            throw ex;
        }
    }

    private static boolean checkTransaction(Invocation invocation) {

        RetryContext retryContext = invocation.getContextValue(RetryConstants.RETRY_CONTEXT_KEY, RetryContext.class);
        if (retryContext == null || RetryLauncher.JOB != retryContext.getRetryLauncher()) {
            // only check for job retry
            return true;
        }

        Retryable retryable = RetryUtils.getRetryable(invocation);
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
                return (boolean) ReflectionUtils.invokeMethod(transactionCheckMethod, target, invocation.getParams());
            } catch (NoSuchMethodException e) {
                throw new SystemException("Failed to find the method: " + retryable.transactionCheckMethod());
            }
        }

        return true;
    }
}
