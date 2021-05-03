package org.fastddd.sample.service.impl;

import com.alibaba.fastjson.JSON;
import org.fastddd.sample.api.OrderService;
import org.fastddd.sample.api.dto.OrderDto;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.factory.OrderFactory;
import org.fastddd.sample.service.domain.repository.OrderRepository;
import org.fastddd.sample.service.domain.service.OrderDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Long placeOrder(OrderDto orderDto) {

        LOGGER.info("place order: {}", JSON.toJSONString(orderDto));
        Order order = OrderFactory.buildOrder(orderDto);
        orderDomainService.createOrder(order);
        return order.getId();
    }

    @Override
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);


    }

    @Override
    public void cancelOrder(Long orderId) {

    }
}
