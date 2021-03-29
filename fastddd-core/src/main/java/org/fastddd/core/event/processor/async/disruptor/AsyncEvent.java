package org.fastddd.core.event.processor.async.disruptor;

import org.fastddd.common.invocation.Invocation;

public class AsyncEvent {

    private Invocation invocation;

    public void reset(Invocation invocation) {
        this.invocation = invocation;
    }

    public Invocation getInvocation() {
        return invocation;
    }

    @Override
    public String toString() {
        return invocation.toString();
    }

}
