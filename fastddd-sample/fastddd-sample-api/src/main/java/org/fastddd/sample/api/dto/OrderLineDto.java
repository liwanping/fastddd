package org.fastddd.sample.api.dto;

import java.math.BigDecimal;

public class OrderLineDto {

    private Long itemId;

    private Long quantity;

    private BigDecimal unitPrice;

    public OrderLineDto() {
    }

    public OrderLineDto(Long itemId, Long quantity, BigDecimal unitPrice) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
