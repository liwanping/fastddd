package org.fastddd.sample.service.impl;

import org.fastddd.sample.api.OrderService;
import org.fastddd.sample.api.dto.OrderDto;
import org.fastddd.sample.api.dto.OrderLineDto;
import org.fastddd.sample.api.enums.OrderStatusEnum;
import org.fastddd.sample.api.enums.OrderTypeEnum;
import org.fastddd.sample.service.AbstractTestCase;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.fastddd.sample.service.domain.repository.OrderRepository;
import org.fastddd.sample.service.eventhandler.ErrorEventHandler;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceTest extends AbstractTestCase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test_order() {

        OrderDto orderDto = new OrderDto(100L, OrderTypeEnum.NORMAL.getId());
        List<OrderLineDto> orderLines = new ArrayList<>();
        orderLines.add(new OrderLineDto(1000021L, 3L, BigDecimal.ONE));
        orderLines.add(new OrderLineDto(1000666L, 3L, BigDecimal.ONE));
        orderDto.setOrderLines(orderLines);

        // place order
        Long orderId = orderService.placeOrder(orderDto);

        Order order = orderRepository.findById(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(OrderStatusEnum.CREATED.getId(), order.getOrderStatus());
        Assert.assertEquals(orderLines.size(), order.getOrderLines().size());

        // pay order
        orderService.payOrder(orderId);

        order = orderRepository.findById(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(OrderStatusEnum.PAID.getId(), order.getOrderStatus());

        // cancel order
        orderService.cancelOrder(orderId);

        order = orderRepository.findById(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(OrderStatusEnum.CANCELED.getId(), order.getOrderStatus());
    }
}
