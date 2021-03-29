package org.fastddd.core.event.processor.async;

import org.fastddd.common.invocation.Invocation;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface AsyncInvoker {

    void invoke(Invocation invocation);

    void start(AsyncConfig asyncConfig);

    void shutdown();
}
