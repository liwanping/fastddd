package org.fastddd.api.event;

public @interface AsyncConfig {

    int disruptorRingBufferSize() default 2048;
}
