package org.fastddd.core.event.processor.async;

import java.util.concurrent.atomic.AtomicLong;

public class AsyncThread extends Thread {

    private static final String PREFIX = "async-event-";

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1L);

    public AsyncThread(ThreadGroup threadGroup, Runnable target, String name, Long stackSize) {
       super(threadGroup, target, toThreadName(name), stackSize);
    }

    private static String toThreadName(String name) {
        return PREFIX + name;
    }
}
