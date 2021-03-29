package org.fastddd.core.session;

import org.fastddd.common.invocation.Invocation;

public interface Session {

    void begin();

    void commit();

    void rollback();

    void cleanupAfterCompletion();

    void addPostInvoker(Invocation invocation);
}
