package org.fastddd.api.event;

import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EventHandler annotation to indicate the method is to process the given event
 * It will be automatically triggered with session operations: commit or cleanupAfterCompletion
 *
 * @author: frank.li
 * @date: 2021/3/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

    // Indicate if the method will be asynchronously
    boolean asynchronous() default false;

    // Indicate if the method will be invoked after the transaction committed
    boolean fireAfterTransaction() default true;

    // the sequence of invocation, only make sense for synchronous
    int sequence() default 0;

    // async config data
    AsyncConfig asyncConfig() default @AsyncConfig();

    // retry config
    Retryable retryable() default @Retryable(mode= RetryMode.NEVER);
}
