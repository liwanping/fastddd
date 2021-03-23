package org.fastddd.core.session;

public class SessionUtils {

    private static TransactionalSessionFactory sessionFactory = DefaultTransactionalSessionFactory.get();

    public static TransactionalSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void setSessionFactory(TransactionalSessionFactory sessionFactory) {
        SessionUtils.sessionFactory = sessionFactory;
    }

    public static void execute(Callback callback) {

        boolean success = sessionFactory.registerSession(false);

        try {
            callback.execute();
            if (success) {
                sessionFactory.requireSession().commit();
                sessionFactory.requireSession().cleanupAfterCompletion();
            }
        } finally {
            if (success) {
                sessionFactory.closeSession();
            }
        }
    }

    public interface Callback {
        void execute();
    }
}
