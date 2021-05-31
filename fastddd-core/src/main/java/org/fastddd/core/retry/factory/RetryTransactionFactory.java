package org.fastddd.core.retry.factory;

import org.fastddd.api.retry.Retryable;
import org.fastddd.common.id.IdUtils;
import org.fastddd.common.id.XidUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.retry.model.RetryTransaction;
import org.fastddd.core.retry.utils.RetryUtils;
import org.fastddd.core.session.SessionManager;

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
        return retryTransaction;
    }


}
