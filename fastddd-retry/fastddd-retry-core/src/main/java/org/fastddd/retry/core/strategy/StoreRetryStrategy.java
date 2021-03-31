package org.fastddd.retry.core.strategy;

import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class StoreRetryStrategy implements RetryStrategy {

    @Override
    public boolean isQualified(Retryable retryable) {
        return RetryMode.STORE == retryable.mode();
    }

    @Override
    public Object doWithRetry(Invocation invocation) {
        // call directly since the next retry will be triggered by the job
        return InvocationHelper.doInvoke(invocation);
    }
}
