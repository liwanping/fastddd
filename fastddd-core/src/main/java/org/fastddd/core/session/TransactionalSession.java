package org.fastddd.core.session;

import org.apache.commons.collections.CollectionUtils;
import org.fastddd.api.event.EventRegistry;
import org.fastddd.core.event.bus.EventBusManager;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.PayloadEvent;
import org.fastddd.core.event.processor.EventHandlerProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TransactionalSession implements Session {

    private final Queue<Invocation> invocationQueue = new ConcurrentLinkedDeque<>();

    private TransactionalSession() {}

    public static TransactionalSession create() {
        TransactionalSession session = new TransactionalSession();
        SessionHelper.onBegin(session);
        return session;
    }

    @Override
    public void commit() {
        try {
            doCommit();
            SessionHelper.onCommit(this);
        } catch (Throwable t) {
            doRollback();
            throw new RuntimeException(t);
        }
    }

    @Override
    public void rollback() {
        doRollback();
        SessionHelper.onRollback(this);
    }

    @Override
    public void cleanupAfterCompletion() {
        try {
            while (!invocationQueue.isEmpty()) {
                EventHandlerProcessor.process(invocationQueue.poll());
            }
        } finally {
            SessionHelper.onComplete(this);
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
            EventBusManager.getEventBus().publish(payloadEvents);
            // in case new events registered after fired
            doCommit();
        }
    }

    protected void doRollback() {
        invocationQueue.clear();
    }
}
