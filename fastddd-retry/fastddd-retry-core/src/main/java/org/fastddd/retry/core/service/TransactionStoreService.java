package org.fastddd.retry.core.service;

import org.fastddd.common.invocation.Invocation;
import org.fastddd.retry.core.model.RetryTransaction;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public interface TransactionStoreService {

    void save(RetryTransaction transaction);

    void update(RetryTransaction transaction);

    void remove(RetryTransaction transaction);
}
