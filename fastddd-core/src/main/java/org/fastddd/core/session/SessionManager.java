package org.fastddd.core.session;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface SessionManager {

    boolean registerSession(boolean requireNew);

    Session requireSession();

    void closeSession();
}
