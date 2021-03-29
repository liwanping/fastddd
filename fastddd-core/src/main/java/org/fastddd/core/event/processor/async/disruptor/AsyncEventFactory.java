package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncEventFactory implements EventFactory<AsyncEvent> {

    @Override
    public AsyncEvent newInstance() {
        return new AsyncEvent();
    }
}
