package org.fastddd.core.session;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface SessionLifecycleListener {

    void onBegin(Session session);

    void onCommit(Session session);

    void onRollback(Session session);

    void onComplete(Session session);
}
