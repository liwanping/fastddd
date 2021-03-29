package org.fastddd.core.event.bus;

import org.fastddd.core.event.processor.EventHandlerProcessor;
import org.fastddd.core.session.TransactionalSessionManager;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.PayloadEvent;
import org.fastddd.api.event.EventHandler;
import org.fastddd.core.event.listener.AnnotationEventListener;
import org.fastddd.core.event.listener.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultEventBus implements EventBus {

    private static final EventBus INSTANCE = new DefaultEventBus();

    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    protected static EventBus get() {
        return INSTANCE;
    }

    @Override
    public void subscribe(EventListener eventListener) {

        if (eventListener instanceof AnnotationEventListener) {
            for (EventListener listener : listeners) {
                if (listener.getTargetType().equals(eventListener.getTargetType())) {
                    // duplicated entry
                    return;
                }
            }
        }

        listeners.add(eventListener);
    }

    @Override
    public void publish(List<PayloadEvent> payloadEvents) {

        List<Invocation> invocations = new ArrayList<>();

        for (EventListener listener : listeners) {
            invocations.addAll(listener.generateInvocations(payloadEvents));
        }

        sort(invocations);

        for (Invocation invocation : invocations) {
            handle(invocation);
        }
    }

    @Override
    public List<EventListener> getAllEventListeners() {
        return this.listeners;
    }

    private void handle(Invocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler.fireAfterTransaction()) {
            TransactionalSessionManager.get().requireSession().addPostInvoker(invocation);
        } else {
            EventHandlerProcessor.process(invocation);
        }
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
