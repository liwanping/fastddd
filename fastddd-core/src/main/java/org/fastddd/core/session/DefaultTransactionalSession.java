package org.fastddd.core.session;

import org.fastddd.core.event.bus.EventBusFactory;
import org.fastddd.core.event.EventInvocation;
import org.fastddd.core.event.PayloadEvent;
import org.fastddd.core.event.processor.EventHandlerProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DefaultTransactionalSession implements TransactionalSession {

    private final List<PayloadEvent> payloadEventList = new ArrayList<>();

    private final Queue<EventInvocation> invocationQueue = new ConcurrentLinkedDeque<>();

    @Override
    public void registerEvent(PayloadEvent payloadEvent) {
        payloadEventList.add(payloadEvent);
    }

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
            payloadEventList.clear();
            invocationQueue.clear();
        }
    }

    protected void doCommit() {
        if (!payloadEventList.isEmpty()) {
            EventBusFactory.getEventBus().publish(payloadEventList, payloadEventList::clear);
            // in case new event registered after execution
            doCommit();
        }
    }

    protected void doRollback() {
        payloadEventList.clear();
        invocationQueue.clear();
    }
}
