package org.fastddd.core.retry.transaction.factory;

import org.fastddd.api.retry.Retryable;
import org.fastddd.common.id.IdUtils;
import org.fastddd.common.id.XidUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.core.retry.constants.RetryConstants;
import org.fastddd.core.retry.transaction.model.RetryContext;
import org.fastddd.core.retry.transaction.model.RetryTransaction;
import org.fastddd.core.retry.RetryUtils;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransactionFactory {


    public static RetryTransaction buildRetryTransaction(Invocation invocation) {

        Retryable retryable = RetryUtils.getRetryable(invocation);
        RetryTransaction retryTransaction = new RetryTransaction();
        retryTransaction.setXid(XidUtils.generateXID());
        retryTransaction.setBranchId(IdUtils.generateId());
        retryTransaction.setMaxAttempts(retryable.maxAttempts());

        RetryContext retryContext = invocation.getContextValue(RetryConstants.RETRY_CONTEXT_KEY, RetryContext.class);
        retryTransaction.setRetriedCount(retryContext.getRetriedCount());
        retryTransaction.setRemark(retryContext.getRemark());

        retryTransaction.setApplicationData(serialize(invocation));

        return retryTransaction;
    }

    private static byte[] serialize(Invocation invocation) {
        return new byte[0];
    }

}
