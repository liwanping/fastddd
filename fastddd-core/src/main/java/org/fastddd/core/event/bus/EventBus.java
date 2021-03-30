package org.fastddd.core.event.bus;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.core.event.listener.EventListener;

import java.util.List;

/**
 * Event bus
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface EventBus {

    /**
     * Subscribe the given event listener
     * @param eventListener
     */
    void subscribe(EventListener eventListener);

    /**
     * Publish the domain events
     * All the subscribed event listeners will be triggered to process
     * @param domainEvents
     */
    void publish(List<DomainEvent> domainEvents);

    /**
     * Returns all the subscribed event listeners
     * @return
     */
    List<EventListener> getAllEventListeners();
}
