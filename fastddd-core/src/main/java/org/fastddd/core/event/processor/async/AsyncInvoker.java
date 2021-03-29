package org.fastddd.core.event.processor.async;

import org.fastddd.common.invocation.Invocation;

public interface AsyncInvoker {

    void invoke(Invocation invocation);

    void start(Invocation invocation);

    void shutdown();
}
