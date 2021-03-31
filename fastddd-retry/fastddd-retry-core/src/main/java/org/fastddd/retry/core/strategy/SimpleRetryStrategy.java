package org.fastddd.retry.core.strategy;

import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.ReflectionRuntimeException;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.retry.core.RetryAspect;
import org.fastddd.retry.core.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class SimpleRetryStrategy implements RetryStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRetryStrategy.class);

    @Override
    public boolean isQualified(Retryable retryable) {
        return RetryMode.SIMPLE == retryable.mode();
    }

    @Override
    public Object doWithRetry(Invocation invocation) {

        Retryable retryable = RetryUtils.getRetryable(invocation);

        for (int loop = 0; ; loop++) {
            try {
                return InvocationHelper.doInvoke(invocation);
            } catch (Throwable t) {
                if (RetryUtils.canRetry(retryable, t, loop)) {
                    if (retryable.enableFastRetry() && loop == 0) {
                        LOGGER.warn("Invocation execution failed but fast retry enabled, will retry immediately, invocation:{}", invocation);
                        continue;
                    }

                    LOGGER.warn("Invocation execution failed but will retry, invocation:{}, loop:{}, retryIntervalInMillis:{}",
                            invocation, loop, retryable.retryIntervalInMillis());
                    if (retryable.retryIntervalInMillis() > 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(retryable.retryIntervalInMillis());
                        } catch (InterruptedException e) {
                            //just ignore
                        }
                    }
                } else {
                    LOGGER.error("Invocation execution failed but cannot retry, invocation:{}", invocation, t);
                    throw new ReflectionRuntimeException(t);
                }
            }
        }
    }
}
