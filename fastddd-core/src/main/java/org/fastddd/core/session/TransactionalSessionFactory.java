package org.fastddd.core.session;

public interface TransactionalSessionFactory {

    boolean registerSession(boolean requireNew);

    TransactionalSession requireSession();

    void closeSession();
}
