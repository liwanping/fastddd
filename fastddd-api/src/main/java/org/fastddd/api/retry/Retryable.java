package org.fastddd.api.retry;

import org.fastddd.api.retry.RetryMode;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public @interface Retryable {

    // retry mode
    RetryMode mode() default RetryMode.SIMPLE;

    // max attempt count to invoke, including the first invoking
    int maxAttempts() default 3;

    // retry interval time in milliseconds
    long retryIntervalInMillis() default 10;

    // indicate if fast retry enabled, if true, will invoke the method immediately after the first throwing
    boolean enableFastRetry() default true;

    // define the exceptions that will stop retrying if they are thrown
    Class<? extends Throwable>[] notRetryableForExceptions() default {};

    // transaction store service defined if store mode
    // in store mode, will handle this invocation as a transaction
    // invocation retrying will be triggered by the transaction recovery job
    // the  job will check the stored transaction context data and invoke the method periodically
    String transactionStoreService() default "";

    // transaction check method to check if retrying method should be invoked
    // it's often used for the business status check
    // this method should return boolean value
    String transactionCheckMethod() default "";

    // transaction check class that the above transaction check method defined
    // by default, it is same as the invoking target class
    Class<?> transactionCheckClass() default Object.class;
}
