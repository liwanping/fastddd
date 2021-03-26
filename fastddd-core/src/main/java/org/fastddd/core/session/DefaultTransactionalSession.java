package org.fastddd.core.session;

import org.apache.commons.collections.CollectionUtils;
import org.fastddd.api.event.EventRegistry;
import org.fastddd.core.event.bus.EventBusFactory;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.PayloadEvent;
import org.fastddd.core.event.processor.EventHandlerProcessor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DefaultTransactionalSession implements TransactionalSession {

    private final Queue<Invocation> invocationQueue = new ConcurrentLinkedDeque<>();

    @Override
    public void commit() {
        try {
            doCommit();
        } catch (Throwable t) {
            doRollback();
            throw new RuntimeException(t);
        }
    }

    @Override
    public void rollback() {
        doRollback();
    }

    @Override
    public void cleanupAfterCompletion() {
        try {
            while (!invocationQueue.isEmpty()) {
                EventHandlerProcessor.process(invocationQueue.poll());
            }
        } finally {
            invocationQueue.clear();
            EventRegistry.remove();
        }
    }

    @Override
    public void addPostInvoker(Invocation invocation) {
        invocationQueue.add(invocation);
    }

    protected void doCommit() {
        List<PayloadEvent> payloadEvents = EventRegistry.unregisterAll();
        if (CollectionUtils.isNotEmpty(payloadEvents)) {
            EventBusFactory.getEventBus().publish(payloadEvents);
            // in case new events registered after fired
            doCommit();
        }
    }

    protected void doRollback() {
        invocationQueue.clear();
    }
}
