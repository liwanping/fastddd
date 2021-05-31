package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import org.fastddd.core.retry.utils.RetryUtils;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncEventHandler implements SequenceReportingEventHandler<AsyncEvent>, LifecycleAware {

    private static final int NOTIFY_PROGRESS_THRESHOLD = 50;

    private Sequence sequenceCallback;
    private int counter;

    @Override
    public void onEvent(AsyncEvent asyncEvent, long sequence, boolean endOfBatch) throws Exception {

        RetryUtils.doWithRetry(asyncEvent.getInvocation());

        /*
         * Notify the BatchEventProcessor that the sequence has progressed.
         * without this callback, the sequence will not be progressed
         * until the batch has completely finished.
          */
        if (++counter > NOTIFY_PROGRESS_THRESHOLD) {
            sequenceCallback.set(sequence);
            counter = 0;
        }
    }

    @Override
    public void setSequenceCallback(Sequence sequence) {
        this.sequenceCallback = sequence;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onShutdown() {

    }

}
