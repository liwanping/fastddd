package org.fastddd.core.session;

public interface SessionLifecycleListener {

    void onBegin(Session session);

    void onCommit(Session session);

    void onRollback(Session session);

    void onComplete(Session session);
}
