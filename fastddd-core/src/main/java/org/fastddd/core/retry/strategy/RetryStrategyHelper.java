package org.fastddd.core.retry.strategy;

import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.SystemException;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.core.retry.RetryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author: frank.li
 * @date: 2021/3/31
 */
public class RetryStrategyHelper {

    private static List<RetryStrategy> retryStrategies = new ArrayList<>();

    static {
        ServiceLoader<RetryStrategy> retryStrategyServiceLoader = ServiceLoader.load(RetryStrategy.class);
        for (RetryStrategy retryStrategy : retryStrategyServiceLoader) {
            retryStrategies.add(retryStrategy);
        }
    }

    public static Object doWithRetry(Invocation invocation) {
        Retryable retryable = RetryUtils.getRetryable(invocation);
        return getRetryStrategy(retryable).doWithRetry(invocation);
    }

    private static RetryStrategy getRetryStrategy(Retryable retryable) {
        for (RetryStrategy retryStrategy : retryStrategies) {
            if (retryStrategy.isQualified(retryable)) {
                return retryStrategy;
            }
        }
        // should never arrive here
        throw new SystemException("No qualified retry strategy found!");
    }
}
