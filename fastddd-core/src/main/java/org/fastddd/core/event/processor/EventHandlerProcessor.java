package org.fastddd.core.event.processor;

import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.EventHandler;

public class EventHandlerProcessor {

    public static void process(Invocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler.asynchronous()) {

        } else {
            InvocationHelper.doInvoke(invocation);
        }
    }
}
