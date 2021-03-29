package org.fastddd.core.event.processor.async.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncEventExceptionHandler implements ExceptionHandler<AsyncEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEventExceptionHandler.class);

    @Override
    public void handleEventException(Throwable throwable, long sequence, AsyncEvent asyncEvent) {
        LOGGER.error("Exception thrown while handling event, sequence:{}, event:{}",
                sequence, asyncEvent, throwable);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        LOGGER.error("Exception thrown while starting", throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        LOGGER.error("Exception thrown while shutting down", throwable);
    }
}
