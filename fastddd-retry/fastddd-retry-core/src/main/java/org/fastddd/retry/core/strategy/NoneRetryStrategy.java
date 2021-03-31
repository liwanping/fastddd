package org.fastddd.retry.core.strategy;

import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class NoneRetryStrategy implements RetryStrategy {

    @Override
    public boolean isQualified(Retryable retryable) {
        return RetryMode.NONE == retryable.mode();
    }

    @Override
    public Object doWithRetry(Invocation invocation) {
        // no retry required, just go ahead
        return InvocationHelper.doInvoke(invocation);
    }
}
