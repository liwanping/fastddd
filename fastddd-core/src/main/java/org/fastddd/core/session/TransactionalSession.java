package org.fastddd.core.session;

import org.fastddd.core.event.PayloadEvent;

public interface TransactionalSession {

    void registerEvent(PayloadEvent payloadEvent);

    void commit();

    void rollback();

    void cleanupAfterCompletion();
}
