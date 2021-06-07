package org.fastddd.sample.api;

import org.fastddd.sample.api.dto.OrderDto;

public interface OrderService {

    Long placeOrder(OrderDto orderDto);

    void payOrder(Long orderId);

    void cancelOrder(Long orderId);
}
