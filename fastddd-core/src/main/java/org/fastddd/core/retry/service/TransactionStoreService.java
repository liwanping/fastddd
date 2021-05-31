package org.fastddd.core.retry.service;

import org.fastddd.core.retry.model.RetryTransaction;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public interface TransactionStoreService {

    void save(RetryTransaction transaction);

    void update(RetryTransaction transaction);

    void remove(RetryTransaction transaction);
}
