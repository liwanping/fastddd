package org.fastddd.core.session;

import org.fastddd.core.transaction.TransactionExecutor;

import java.util.ArrayDeque;
import java.util.Currency;
import java.util.Deque;
import java.util.Objects;

public class DefaultTransactionalSessionFactory implements TransactionalSessionFactory {

    private static final TransactionalSessionFactory INSTANCE = new DefaultTransactionalSessionFactory();

    private static final ThreadLocal<Deque<SessionEntry>> CURRENT = new ThreadLocal<>();

    public static TransactionalSessionFactory get() {
        return INSTANCE;
    }

    @Override
    public boolean registerSession(boolean requireNew) {
        if (requireNew) {
            if (CURRENT.get() == null) {
                CURRENT.set(new ArrayDeque<>());
            }
            CURRENT.get().push(new SessionEntry(new DefaultTransactionalSession()));
            return true;
        }

        if (CURRENT.get() == null) {
            CURRENT.set(new ArrayDeque<>());
            CURRENT.get().push(new SessionEntry(new DefaultTransactionalSession()));
            return true;
        } else if (CURRENT.get().peek() == null) {
            CURRENT.get().push(new SessionEntry(new DefaultTransactionalSession()));
            return true;
        }
        return false;
    }

    @Override
    public TransactionalSession requireSession() {
        return Objects.requireNonNull(CURRENT.get().peek()).getSession();
    }

    @Override
    public void closeSession() {
        CURRENT.get().pop();
    }

    private static class SessionEntry {
        private final TransactionalSession transactionalSession;

        public SessionEntry(TransactionalSession transactionalSession) {
            this.transactionalSession = transactionalSession;
        }

        TransactionalSession getSession() {
            return transactionalSession;
        }
    }
}
