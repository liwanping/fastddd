package org.fastddd.core.retry.strategy;

import org.fastddd.api.retry.Retryable;
import org.fastddd.common.invocation.Invocation;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public interface RetryStrategy {

    boolean isQualified(Retryable retryable);

    Object doWithRetry(Invocation invocation);
}
