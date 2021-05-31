package org.fastddd.core.retry.factory;

import org.fastddd.api.retry.Retryable;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.core.retry.model.RetryTransaction;
import org.fastddd.core.retry.utils.RetryUtils;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransactionFactory {


    public static RetryTransaction buildRetryTransaction(Invocation invocation) {
        Retryable retryable = RetryUtils.getRetryable(invocation);
        RetryTransaction retryTransaction = new RetryTransaction();
        retryTransaction.setMaxAttempts(retryable.maxAttempts());
        return retryTransaction;
    }
}
