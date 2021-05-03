package org.fastddd.sample.service.domain.event;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.sample.service.domain.entity.Order;

public class OrderCreatedEvent implements DomainEvent {

    private Order order;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
