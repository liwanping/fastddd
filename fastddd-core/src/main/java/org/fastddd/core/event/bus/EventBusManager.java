package org.fastddd.core.event.bus;

public class EventBusManager {

    private static EventBus eventBus = DefaultEventBus.get();

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static void setEventBus(EventBus eventBus) {
        EventBusManager.eventBus = eventBus;
    }
}
