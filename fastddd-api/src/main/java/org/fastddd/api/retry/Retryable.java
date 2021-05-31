package org.fastddd.api.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {

    /**
     * retry mode
     */
    RetryMode mode() default RetryMode.SIMPLE;
    /**
     * max attempt count to invoke, including the first invoking
     */
    int maxAttempts() default 3;
    /**
     * retry interval time in milliseconds
     */
    long retryIntervalInMillis() default 10;
    /**
     * indicate if fast retry enabled, if true, will invoke the method immediately after the first throwing
     */
    boolean enableFastRetry() default true;
    /**
     * define the exceptions that will stop retrying if they are thrown
     */
    Class<? extends Throwable>[] notRetryableForExceptions() default {};

    /**
     * transaction store service defined if store mode
     * in store mode, will handle this invocation as a transaction
     * invocation retrying will be triggered by the transaction recovery job
     * the  job will check the stored transaction context data and invoke the method periodically
     */
    String transactionStoreService() default "";

    /**
     * transaction check method to check if retrying method should be invoked
     * it's often used for the business status check
     * NOTE: this method should return boolean value
     */
    String transactionCheckMethod() default "";
}
