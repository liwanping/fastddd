package org.fastddd.core.event.processor.async;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class AsyncThreadFactory implements ThreadFactory {

    private static final AtomicLong FACTORY_NUMBER = new AtomicLong(1L);
    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1L);

    private final boolean daemon;
    private final ThreadGroup group;
    private final int priority;
    private final String threadNamePrefix;

    public AsyncThreadFactory(String threadNamePrefix, boolean daemon, int priority) {
        this.threadNamePrefix = threadNamePrefix + "-" + FACTORY_NUMBER.getAndIncrement() + "-";
        this.daemon = daemon;
        this.priority = priority;
        final SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new AsyncThread(group, runnable, threadNamePrefix + THREAD_NUMBER.getAndIncrement(), 0L);
        if (thread.isDaemon() != daemon) {
            thread.setDaemon(daemon);
        }
        if (thread.getPriority() != priority) {
            thread.setPriority(priority);
        }
        return thread;
    }
}
