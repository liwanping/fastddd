package org.fastddd.core.injector;

import com.google.inject.AbstractModule;
import org.fastddd.core.event.bus.DefaultEventBus;
import org.fastddd.core.event.bus.EventBus;
import org.fastddd.core.event.processor.async.AsyncInvoker;
import org.fastddd.core.event.processor.async.disruptor.AsyncDisruptor;
import org.fastddd.core.session.SessionManager;
import org.fastddd.core.session.TransactionalSessionManager;

/**
 * The module to bind the required classes and instances
 * @author: frank.li
 * @date: 2021/3/29
 */
public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(DefaultEventBus.class).asEagerSingleton();
        bind(SessionManager.class).to(TransactionalSessionManager.class).asEagerSingleton();
        bind(AsyncInvoker.class).to(AsyncDisruptor.class).asEagerSingleton();
    }
}
