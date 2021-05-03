package org.fastddd.sample.api.dto;

import java.util.List;

public class OrderDto {

    private Long userId;

    private Integer orderType;

    private List<OrderLineDto> orderLines;

    public OrderDto() {
    }

    public OrderDto(Long userId, Integer orderType) {
        this.userId = userId;
        this.orderType = orderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }
}
