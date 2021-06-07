package org.fastddd.sample.service.domain.entity;

import org.fastddd.api.entity.AbstractAggregateRoot;
import org.fastddd.sample.api.enums.OrderStatusEnum;
import org.fastddd.sample.service.domain.event.OrderCanceledEvent;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.fastddd.sample.service.domain.event.OrderFinishedEvent;
import org.fastddd.sample.service.domain.event.OrderPaidEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractAggregateRoot<Long> {

    private Long id;

    private Integer orderType;

    private Integer orderStatus;

    private BigDecimal totalAmount;

    private List<OrderLine> orderLines = new ArrayList<>();

    public Order() {
    }

    public Order(Integer orderType, BigDecimal totalAmount) {
        this.orderType = orderType;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatusEnum.CREATED.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        this.orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

    public void save() {
        this.register(new OrderCreatedEvent(this));
    }

    public void pay() {
        this.orderStatus = OrderStatusEnum.PAID.getId();
        this.register(new OrderPaidEvent(getId()));
    }

    public void cancel() {
        this.orderStatus = OrderStatusEnum.CANCELED.getId();
        this.register(new OrderCanceledEvent(getId()));
    }

    public void finish() {
        this.orderStatus = OrderStatusEnum.FINISHED.getId();
        this.register(new OrderFinishedEvent(getId()));
    }
}
