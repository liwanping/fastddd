package org.fastddd.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

    boolean asynchronous() default false;

    boolean fireAfterTransaction() default false;

    int sequence() default 0;

    AsyncConfig asyncConfig() default @AsyncConfig();
}
