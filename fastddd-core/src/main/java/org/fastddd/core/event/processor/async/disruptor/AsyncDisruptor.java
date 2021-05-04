package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.core.event.processor.async.AsyncConfig;
import org.fastddd.core.event.processor.async.AsyncInvoker;
import org.fastddd.core.event.processor.async.AsyncThreadFactory;
import org.fastddd.core.event.processor.async.AsyncUtils;
import org.fastddd.retry.core.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AsyncDisruptor implements AsyncInvoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDisruptor.class);

    private final Map<String, Disruptor<AsyncEvent>> disruptorMap = new ConcurrentHashMap<>();

    private final ThreadLocal<AsyncEventTranslator> eventTranslatorThreadLocal = ThreadLocal.withInitial(AsyncEventTranslator::new);

    @Override
    public void invoke(Invocation invocation) {

        AsyncConfig asyncConfig = AsyncUtils.buildAsyncConfig(invocation.getTarget(), invocation.getMethod());

        // ensure disruptor started
        start(asyncConfig);

        AsyncEventTranslator eventTranslator = eventTranslatorThreadLocal.get();
        eventTranslator.reset(invocation);

        Disruptor<AsyncEvent> disruptor = disruptorMap.get(asyncConfig.getName());

        if (!disruptor.getRingBuffer().tryPublishEvent(eventTranslator)) {
            LOGGER.warn("Disruptor ring buffer is full, event handler will be executed in sync mode for {}.{}",
                    invocation.getTarget().getClass().getSimpleName(),
                    invocation.getMethod().getName());
            RetryUtils.doWithRetry(invocation);
        }
    }

    @Override
    public void start(AsyncConfig asyncConfig) {

        String disruptorName = asyncConfig.getName();
        if (disruptorMap.containsKey(disruptorName)) {
            return;
        }

        synchronized (AsyncDisruptor.class) {
            if (disruptorMap.containsKey(disruptorName)) {
                return;
            }

            LOGGER.info("starting disruptor: {}", asyncConfig);

            String threadPrefix = asyncConfig.getName() + "-disruptor";
            ThreadFactory threadFactory = new AsyncThreadFactory(threadPrefix, true, Thread.NORM_PRIORITY);

            WaitStrategy waitStrategy = new TimeoutBlockingWaitStrategy(10L, TimeUnit.MILLISECONDS);

            Disruptor<AsyncEvent> disruptor = new Disruptor<>(new AsyncEventFactory(),
                    asyncConfig.getDisruptorRingBufferSize(), threadFactory, ProducerType.MULTI, waitStrategy);

            AsyncEventHandler[] eventHandlers = {new AsyncEventHandler()};
            disruptor.handleEventsWith(eventHandlers);

            disruptor.setDefaultExceptionHandler(new AsyncEventExceptionHandler());

            disruptor.start();

            LOGGER.info("started disruptor: {}", disruptorName);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                stop(disruptorName, disruptor);
            }));

            disruptorMap.put(disruptorName, disruptor);
        }
    }

    @Override
    public void shutdown() {

        LOGGER.info("shut down disruptor...");
        Map<String, Disruptor<AsyncEvent>> tmpDisruptorMap = new ConcurrentHashMap<>(disruptorMap);
        if (tmpDisruptorMap.isEmpty()) {
            // disruptor already shut down by other threads
            return;
        }

        tmpDisruptorMap.forEach(this::stop);
    }

    private void stop(String name, Disruptor<AsyncEvent> disruptor) {

        LOGGER.info("shut down disruptor: {}", name);

        /*
         * Calling Disruptor.shutdown() will wait until all enqueued events are fully processed,
         * but this wait happens in a busy-spin. To avoid wasting CPU, we will sleep in shot chunks,
         * up to 10 seconds, waiting for the ring buffer to drain.
         */
        for (int i = 0; i < 100; i++) {
            if (hasBacklog(disruptor)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }

        try {
            // busy-spins until all events currently in the disruptor haven been processed or timeout
            disruptor.shutdown(120, TimeUnit.SECONDS);
        } catch (TimeoutException ex) {
            // give up on remaining events, if any
            disruptor.halt();
        }
    }

    private boolean hasBacklog(Disruptor<AsyncEvent> disruptor) {
        RingBuffer<AsyncEvent> ringBuffer = disruptor.getRingBuffer();
        return !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize());
    }

}
