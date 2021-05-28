package org.fastddd.retry.core.service;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.retry.core.model.RetryTransaction;
import org.fastddd.retry.core.utils.RetryUtils;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransactionHelper {

    public static void save(Invocation invocation) {

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
