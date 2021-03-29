package org.fastddd.core.session;

import org.fastddd.common.invocation.Invocation;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface Session {

    void begin();

    void commit();

    void rollback();

    void cleanupAfterCompletion();

    void addPostInvoker(Invocation invocation);
}
