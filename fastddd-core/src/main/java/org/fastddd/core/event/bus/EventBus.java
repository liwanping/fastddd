package org.fastddd.core.event.bus;

import org.fastddd.api.event.PayloadEvent;
import org.fastddd.core.event.listener.EventListener;

import java.util.List;

public interface EventBus {

    void subscribe(EventListener eventListener);

    void publish(List<PayloadEvent> payloadEvents);

    List<EventListener> getAllEventListeners();
}
