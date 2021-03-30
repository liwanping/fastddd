package org.fastddd.core.event.bus;

import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.core.event.processor.EventHandlerProcessor;
import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.session.SessionManager;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.DomainEvent;
import org.fastddd.api.event.EventHandler;
import org.fastddd.core.event.listener.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default event bus implementation
 * @author: frank.li
 * @date: 2021/3/29
 */
public class DefaultEventBus implements EventBus {

    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(EventListener eventListener) {

        for (EventListener listener : listeners) {
            if (listener.equals(eventListener)) {
                // duplicated entry
                return;
            }
        }



        listeners.add(eventListener);
    }

    @Override
    public void publish(List<DomainEvent> domainEvents) {

        List<Invocation> invocations = new ArrayList<>();

        for (EventListener listener : listeners) {
            invocations.addAll(listener.onEvent(domainEvents));
        }

        sort(invocations);

        // before invoke
        for (Invocation invocation : invocations) {
            InvocationHelper.beforeInvoke(invocation);
        }

        // do invoke as config
        for (Invocation invocation : invocations) {
            EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
            if (eventHandler.fireAfterTransaction()) {
                InjectorFactory.getInstance(SessionManager.class).requireSession().addPostInvoker(invocation);
            } else {
                EventHandlerProcessor.process(invocation);
            }
        }
    }

    @Override
    public List<EventListener> getAllEventListeners() {
        return this.listeners;
    }

    private void sort(List<Invocation> invocations) {
        invocations.sort((i1, i2) -> {
            EventHandler e1 = ReflectionUtils.getAnnotation(i1.getMethod(), EventHandler.class);
            EventHandler e2 = ReflectionUtils.getAnnotation(i2.getMethod(), EventHandler.class);
            if (e1.asynchronous() != e2.asynchronous()) {
                return e1.asynchronous() ? 1 : -1;
            }
            return e1.sequence() - e2.sequence();
        });
    }
}
