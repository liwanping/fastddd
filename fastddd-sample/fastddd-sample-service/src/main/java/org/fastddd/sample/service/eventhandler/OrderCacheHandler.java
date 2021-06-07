package org.fastddd.sample.service.eventhandler;

import com.alibaba.fastjson.JSON;
import org.fastddd.api.event.EventHandler;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.fastddd.sample.service.domain.event.OrderPaidEvent;
import org.fastddd.sample.service.domain.repository.OrderRepository;
import org.fastddd.sample.service.infra.cache.RedisCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCacheHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCacheHandler.class);

    private static final String ORDER_CACHE_KEY = "order:%s";

    private static final long ORDER_CACHE_EXPIRED_SECONDS = 30000L;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        LOGGER.info("handle order created event for cache, order: {}", JSON.toJSONString(event.getOrder()));
        redisCacheManager.put(String.format(ORDER_CACHE_KEY, event.getOrder().getId()),
                JSON.toJSONString(event.getOrder()),
                ORDER_CACHE_EXPIRED_SECONDS);
    }

    @EventHandler
    public void handleOrderPaidEvent(OrderPaidEvent event) {
        LOGGER.info("handle order paid event for cache, orderId: {}", event.getOrderId());
        Order order = orderRepository.findById(event.getOrderId());
        if (order != null) {
            redisCacheManager.put(String.format(ORDER_CACHE_KEY, event.getOrderId()),
                    JSON.toJSONString(order),
                    ORDER_CACHE_EXPIRED_SECONDS);
        }

    }
}
