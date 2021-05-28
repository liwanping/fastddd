package org.fastddd.core.session;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
final class SessionHelper {

    private static Set<SessionLifecycleListener> sessionLifecycleListeners = new HashSet<>();

    static {
        ServiceLoader<SessionLifecycleListener> sessionLifecycleListenerServiceLoader = ServiceLoader.load(SessionLifecycleListener.class);
        for (SessionLifecycleListener sessionLifecycleListener : sessionLifecycleListenerServiceLoader) {
            sessionLifecycleListeners.add(sessionLifecycleListener);
        }
    }

    static void onBegin(Session session) {
        for (SessionLifecycleListener sessionLifecycleListener : sessionLifecycleListeners) {
            sessionLifecycleListener.onBegin(session);
        }
    }

    static void onCommit(Session session) {
        for (SessionLifecycleListener sessionLifecycleListener : sessionLifecycleListeners) {
            sessionLifecycleListener.onCommit(session);
        }
    }

    static void onRollback(Session session) {
        for (SessionLifecycleListener sessionLifecycleListener : sessionLifecycleListeners) {
            sessionLifecycleListener.onRollback(session);
        }
    }

    static void onComplete(Session session) {
        for (SessionLifecycleListener sessionLifecycleListener : sessionLifecycleListeners) {
            sessionLifecycleListener.onComplete(session);
        }
    }
}
