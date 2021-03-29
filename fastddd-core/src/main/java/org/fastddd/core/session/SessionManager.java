package org.fastddd.core.session;

public interface SessionManager {

    boolean registerSession(boolean requireNew);

    Session requireSession();

    void closeSession();
}
