package org.fastddd.sample.service.domain.entity;

import org.fastddd.api.entity.AbstractDomainObject;

import java.math.BigDecimal;

public class OrderLine extends AbstractDomainObject<Long> {

    private Long id;

    private Long itemId;

    private Long quantity;

    private BigDecimal unitPrice;

    private Order order;


    public OrderLine() {
    }

    public OrderLine(Long itemId, Long quantity, BigDecimal unitPrice) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    protected void setOrder(Order order) {
        this.order = order;
    }
}
