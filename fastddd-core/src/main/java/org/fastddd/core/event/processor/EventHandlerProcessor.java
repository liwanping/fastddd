package org.fastddd.core.event.processor;

import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.EventHandler;
import org.fastddd.core.event.processor.async.AsyncInvoker;
import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.retry.utils.RetryUtils;

/**
 * Process invocation that the method is annotated with @EventHandler
 * @author: frank.li
 * @date: 2021/3/29
 */
public class EventHandlerProcessor {

    public static void process(Invocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler.asynchronous()) {
            InjectorFactory.getInstance(AsyncInvoker.class).invoke(invocation);
        } else {
            RetryUtils.doWithRetry(invocation);
        }
    }
}
