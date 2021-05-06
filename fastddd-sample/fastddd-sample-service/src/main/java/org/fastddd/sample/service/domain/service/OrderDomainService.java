package org.fastddd.sample.service.domain.service;

import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void createOrder(Order order) {
        order.save();
        orderRepository.save(order);
    }
}
