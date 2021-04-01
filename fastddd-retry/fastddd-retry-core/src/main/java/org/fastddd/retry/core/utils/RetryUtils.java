package org.fastddd.retry.core.utils;

import org.fastddd.api.event.EventHandler;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.retry.core.strategy.RetryStrategyHelper;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryUtils {

    public static Object doWithRetry(Invocation invocation) {
        InvocationHelper.beforeInvoke(invocation);
        return RetryStrategyHelper.doWithRetry(invocation);
    }

    public static Retryable getRetryable(Invocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler != null) {
            return eventHandler.retryable();
        }
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
}
