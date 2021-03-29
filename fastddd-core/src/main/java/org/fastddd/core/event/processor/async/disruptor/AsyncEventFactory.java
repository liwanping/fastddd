package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.EventFactory;

public class AsyncEventFactory implements EventFactory<AsyncEvent> {

    @Override
    public AsyncEvent newInstance() {
        return new AsyncEvent();
    }
}
