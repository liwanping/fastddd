package org.fastddd.retry.core.strategy;

import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.ReflectionRuntimeException;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.retry.core.constants.RetryConstants;
import org.fastddd.retry.core.constants.RetryLauncher;
import org.fastddd.retry.core.model.RetryContext;
import org.fastddd.retry.core.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class StoreRetryStrategy implements RetryStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreRetryStrategy.class);

    @Override
    public boolean isQualified(Retryable retryable) {
        return RetryMode.STORE == retryable.mode();
    }

    @Override
    public Object doWithRetry(Invocation invocation) {
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
}
