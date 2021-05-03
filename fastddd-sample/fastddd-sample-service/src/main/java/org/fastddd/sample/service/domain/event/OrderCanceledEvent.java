package org.fastddd.sample.service.domain.event;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.sample.service.domain.entity.Order;

public class OrderCanceledEvent implements DomainEvent {

    private Order order;

    public OrderCanceledEvent() {
    }

    public OrderCanceledEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
