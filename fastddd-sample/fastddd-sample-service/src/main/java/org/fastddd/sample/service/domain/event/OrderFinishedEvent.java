package org.fastddd.sample.service.domain.event;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.sample.service.domain.entity.Order;

public class OrderFinishedEvent implements DomainEvent {

    private Order order;

    public OrderFinishedEvent() {
    }

    public OrderFinishedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
