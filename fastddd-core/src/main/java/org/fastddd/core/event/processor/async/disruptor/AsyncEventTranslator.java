package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.EventTranslator;
import org.fastddd.common.invocation.Invocation;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncEventTranslator implements EventTranslator<AsyncEvent> {

    private Invocation invocation;

    @Override
    public void translateTo(AsyncEvent asyncEvent, long sequence) {
        asyncEvent.reset(invocation);
    }

    public void reset(Invocation invocation) {
        this.invocation = invocation;
    }

    public Invocation getInvocation() {
        return invocation;
    }
}
