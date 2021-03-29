package org.fastddd.api.event;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public @interface AsyncConfig {

    // disruptor ring buffer size
    int disruptorRingBufferSize() default 2048;
}
