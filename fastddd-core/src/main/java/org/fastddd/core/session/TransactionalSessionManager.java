package org.fastddd.core.session;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class TransactionalSessionManager implements SessionManager {

    private static final ThreadLocal<Deque<SessionEntry>> CURRENT = new ThreadLocal<>();

    @Override
    public boolean registerSession(boolean requireNew) {
        if (requireNew) {
            if (CURRENT.get() == null) {
                CURRENT.set(new ArrayDeque<>());
            }
            CURRENT.get().push(new SessionEntry(TransactionalSession.create()));
            return true;
        }

        if (CURRENT.get() == null) {
            CURRENT.set(new ArrayDeque<>());
            CURRENT.get().push(new SessionEntry(TransactionalSession.create()));
            return true;
        } else if (CURRENT.get().peek() == null) {
            CURRENT.get().push(new SessionEntry(TransactionalSession.create()));
            return true;
        }
        return false;
    }

    @Override
    public Session requireSession() {
        return Objects.requireNonNull(CURRENT.get().peek()).getSession();
    }

    @Override
    public void closeSession() {
        CURRENT.get().pop();
    }

    private static class SessionEntry {

        private final Session session;

        public SessionEntry(Session session) {
            this.session = session;
            this.session.begin();
        }

        Session getSession() {
            return session;
        }
    }
}
