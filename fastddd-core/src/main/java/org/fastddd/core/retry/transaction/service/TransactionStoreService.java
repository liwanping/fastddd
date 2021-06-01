package org.fastddd.core.retry.transaction.service;

import org.fastddd.core.retry.transaction.model.RetryTransaction;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public interface TransactionStoreService {

    void save(RetryTransaction transaction);

    void update(RetryTransaction transaction);

    void remove(RetryTransaction transaction);
}
