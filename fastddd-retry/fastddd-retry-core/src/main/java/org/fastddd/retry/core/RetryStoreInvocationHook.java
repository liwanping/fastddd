package org.fastddd.retry.core;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHook;
import org.fastddd.retry.core.constants.RetryConstants;
import org.fastddd.retry.core.constants.RetryLauncher;
import org.fastddd.retry.core.factory.RetryTransactionFactory;
import org.fastddd.retry.core.model.RetryTransaction;
import org.fastddd.retry.core.service.TransactionStoreService;
import org.fastddd.retry.core.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryStoreInvocationHook implements InvocationHook {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryStoreInvocationHook.class);

    @Override
    public boolean beforeInvoke(Invocation invocation) {

        Retryable retryable = RetryUtils.getRetryable(invocation);
        if (retryable == null || RetryMode.STORE != retryable.mode()) {
            return true;
        }

        RetryTransaction retryTransaction = getRetryTransaction(invocation);
        if (retryTransaction == null) {
            //for the first invoking, save new transaction
            retryTransaction = RetryTransactionFactory.buildRetryTransaction(invocation);
            invocation.putContextValue(RetryConstants.RETRY_TRANSACTION, retryTransaction);
            invocation.putContextValue(RetryConstants.RETRY_LAUNCHER, RetryLauncher.WORKFLOW);
            LOGGER.info("save retry transaction: {}", retryTransaction);
            getTransactionStoreService(retryable).save(retryTransaction);
        }
        return true;
    }

    @Override
    public void afterInvoke(Invocation invocation, Object result) {

        Retryable retryable = RetryUtils.getRetryable(invocation);
        if (retryable == null || RetryMode.STORE != retryable.mode()) {
            return;
        }

        RetryTransaction retryTransaction = getRetryTransaction(invocation);

        //remove transaction after success
        LOGGER.info("remove retry transaction: {}", retryTransaction);
        getTransactionStoreService(retryable).remove(retryTransaction);
    }

    @Override
    public void afterThrow(Invocation invocation, Throwable t) {

        Retryable retryable = RetryUtils.getRetryable(invocation);
        if (retryable == null || RetryMode.STORE != retryable.mode()) {
            return;
        }

        RetryTransaction retryTransaction = getRetryTransaction(invocation);
        //increase retried count
        int retriedCount = retryTransaction.getRetriedCount() + 1;
        if (RetryUtils.canRetry(retryable, t, retriedCount)) {
            //update retry transaction
            retryTransaction.setRetriedCount(retriedCount);
            retryTransaction.setRemark(t.getMessage());
            LOGGER.info("update retry transaction: {}", retryTransaction);
            getTransactionStoreService(retryable).update(retryTransaction);
        }
    }

    private static RetryTransaction getRetryTransaction(Invocation invocation) {
        return invocation.getContextValue(RetryConstants.RETRY_TRANSACTION, RetryTransaction.class);
    }

    private static TransactionStoreService getTransactionStoreService(Retryable retryable) {
        BeanFactory beanFactory = FactoryBuilder.getFactory(BeanFactory.class);
        if (StringUtils.isNotBlank(retryable.transactionStoreService())) {
            return beanFactory.getBean(retryable.transactionStoreService(), TransactionStoreService.class);
        }
        return beanFactory.getBean(TransactionStoreService.class);
    }
}
