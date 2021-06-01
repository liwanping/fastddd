package org.fastddd.api.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage the domain events to register
 *
 * @author: frank.li
 * @date: 2021/3/29
 */
public final class EventRegistry {

    private static final ThreadLocal<List<DomainEvent>> TL = ThreadLocal.withInitial(ArrayList::new);

    /**
     * Register the domain event into the event container forthe  current thead
     * @param domainEvent
     */
    public static void register(DomainEvent domainEvent) {
        TL.get().add(domainEvent);
    }

    /**
     * Clear all the domain  events for the current thread
     * @return
     */
    public static List<DomainEvent> clearAll() {
        List<DomainEvent> domainEvents = new ArrayList<>(TL.get());
        TL.get().clear();
        return domainEvents;
    }

    /**
     * Returns all the domain events for the current thread
     * @return
     */
    public static List<DomainEvent> get() {
        return TL.get();
    }

    /**
     * Remove the domain event container for the current thread
     */
    public static void remove() {
        TL.remove();
    }
}
