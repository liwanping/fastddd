package org.fastddd.api.retry;

import org.fastddd.api.retry.RetryMode;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public @interface Retryable {

    RetryMode mode() default RetryMode.SIMPLE;

    int maxAttempts() default 3;

    long retryIntervalInMillis() default 100;

    boolean enableFastRetry() default true;

    Class<? extends Throwable>[] notRetryableForExceptions() default {};

    String transactionStoreService() default "";

    String checkMethod() default "";

    Class<?> checkClass() default Object.class;
}
