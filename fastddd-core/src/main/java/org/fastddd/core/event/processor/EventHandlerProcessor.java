package org.fastddd.core.event.processor;

import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.event.EventInvocation;
import org.fastddd.core.event.annotation.EventHandler;

public class EventHandlerProcessor {

    public static void process(EventInvocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler.asynchronous()) {

        } else {

        }
    }
}
