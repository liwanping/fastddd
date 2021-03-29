package org.fastddd.core.event.processor.async;

import org.fastddd.api.event.EventHandler;
import org.fastddd.common.utils.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncUtils {

    public static AsyncConfig buildAsyncConfig(Object target, Method method) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(method, EventHandler.class);
        return AsyncConfig.create()
                .setName(target.getClass().getSimpleName())
                .setDisruptorRingBufferSize(eventHandler.asyncConfig().disruptorRingBufferSize());
    }
}
