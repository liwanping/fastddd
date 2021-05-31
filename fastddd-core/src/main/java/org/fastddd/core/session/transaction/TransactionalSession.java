package org.fastddd.core.session.transaction;

import org.apache.commons.collections.CollectionUtils;
import org.fastddd.api.event.EventRegistry;
import org.fastddd.api.event.DomainEvent;
import org.fastddd.common.id.XidUtils;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.id.IdUtils;
import org.fastddd.core.event.bus.EventBus;
import org.fastddd.core.event.processor.EventHandlerProcessor;
import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.session.Session;
import org.fastddd.core.session.SessionHelper;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class TransactionalSession implements Session {

    private final Queue<Invocation> invocationQueue = new ConcurrentLinkedDeque<>();

    private TransactionalXid transactionalXid;


    private TransactionalSession() {}

    static TransactionalSession create() {
        return new TransactionalSession();
    }

    @Override
    public void begin() {
        this.transactionalXid = TransactionalXidBuilder.generate();
        SessionHelper.onBegin(this);
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
        List<DomainEvent> domainEvents = EventRegistry.clearAll();
        if (CollectionUtils.isNotEmpty(domainEvents)) {
            InjectorFactory.getInstance(EventBus.class).publish(domainEvents);
            // in case new events registered after fired
            doCommit();
        }
    }

    protected void doRollback() {
        invocationQueue.clear();
    }

    public TransactionalXid getTransactionalXid() {
        return transactionalXid;
    }

}
