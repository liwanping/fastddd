package org.fastddd.core.event.processor.async;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncConfig {

    private String name;

    private Integer disruptorRingBufferSize;

    public static AsyncConfig create() {
        return new AsyncConfig();
    }

    public String getName() {
        return name;
    }

    public AsyncConfig setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getDisruptorRingBufferSize() {
        return disruptorRingBufferSize;
    }

    public AsyncConfig setDisruptorRingBufferSize(Integer disruptorRingBufferSize) {
        this.disruptorRingBufferSize = disruptorRingBufferSize;
        return this;
    }

    @Override
    public String toString() {
        return "AsyncConfig{" +
                "name='" + name + '\'' +
                ", disruptorRingBufferSize=" + disruptorRingBufferSize +
                '}';
    }
}
