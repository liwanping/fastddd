package org.fastddd.sample.service.domain.event;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.sample.service.domain.entity.Order;

public class OrderCanceledEvent implements DomainEvent {

    private Long orderId;

    public OrderCanceledEvent() {
    }

    public OrderCanceledEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
