package org.fastddd.core.retry.transaction.service;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.core.retry.transaction.factory.RetryTransactionFactory;
import org.fastddd.core.retry.transaction.model.RetryTransaction;
import org.fastddd.core.retry.RetryUtils;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransactionHelper {

    public static void save(Invocation invocation) {
        RetryTransaction retryTransaction = RetryTransactionFactory.buildRetryTransaction(invocation);
        getTransactionStoreService(invocation).save(retryTransaction);
    }

    public static void update(Invocation invocation) {

    }

    public static void remove(Invocation invocation) {

    }

    private static TransactionStoreService getTransactionStoreService(Invocation invocation) {
        Retryable retryable = RetryUtils.getRetryable(invocation);
        BeanFactory beanFactory = FactoryBuilder.getFactory(BeanFactory.class);
        if (StringUtils.isNotBlank(retryable.transactionStoreService())) {
            return beanFactory.getBean(retryable.transactionStoreService(), TransactionStoreService.class);
        }
        return beanFactory.getBean(TransactionStoreService.class);
    }
}
