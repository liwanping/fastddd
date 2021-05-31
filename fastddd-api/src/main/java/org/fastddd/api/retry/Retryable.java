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
     * Retry mode
     */
    RetryMode mode() default RetryMode.SIMPLE;

    /**
     * Max attempt count to invoke, including the first invoking
     */
    int maxAttempts() default 3;

    /**
     * retry interval time in milliseconds
     */
    long retryIntervalInMillis() default 10;

    /**
     * Indicate if fast retry enabled, if true, will invoke the method immediately after the first throwing
     */
    boolean enableFastRetry() default true;

    /**
     * Exception types that are retryable. Defaults to empty (and if excludes is also
     * empty all exceptions are retried).
     */
    Class<? extends Throwable>[] include() default {};

    /**
     * Exception types that are not retryable. Defaults to empty (and if includes is also
     * empty all exceptions are retried).
     * If includes is empty but excludes is not, all not excluded exceptions are retried
     */
    Class<? extends Throwable>[] exclude() default {};

    /**
     * Transaction store service defined if store mode
     * in store mode, will handle this invocation as a transaction
     * invocation retrying will be triggered by the transaction recovery job
     * the  job will check the stored transaction context data and invoke the method periodically
     */
    String transactionStoreService() default "";

    /**
     * Transaction check method to check if retrying method should be invoked
     * it's often used for the business status check
     * NOTE: this method should return boolean value
     */
    String transactionCheckMethod() default "";
}
