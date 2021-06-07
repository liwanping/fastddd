package org.fastddd.sample.service.eventhandler;

import com.alibaba.fastjson.JSON;
import org.aspectj.weaver.ast.Or;
import org.fastddd.api.event.EventHandler;
import org.fastddd.api.event.EventSponsor;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.fastddd.sample.service.domain.event.OrderPaidEvent;
import org.fastddd.sample.service.domain.repository.OrderRepository;
import org.fastddd.sample.service.infra.mq.OrderMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderEventMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventMessageHandler.class);

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        LOGGER.info("handle order created event to send message, order: {}", JSON.toJSONString(event.getOrder()));
        OrderMessage orderMessage = buildOrderMessage(event.getOrder());
        orderMessageSender.sendOrderMessage(JSON.toJSONString(orderMessage));
    }

    @EventHandler
    public void handleOrderPaidEvent(OrderPaidEvent event) {
        LOGGER.info("handle order paid event to send message, orderId: {}", event.getOrderId());
        Order order = orderRepository.findById(event.getOrderId());
        if (order != null) {
            OrderMessage orderMessage = buildOrderMessage(order);
            orderMessageSender.sendOrderMessage(JSON.toJSONString(orderMessage));
        }

    }

    private OrderMessage buildOrderMessage(Order order) {
        return new OrderMessage()
                .setOrderId(order.getId())
                .setOrderType(order.getOrderType())
                .setOrderStatus(order.getOrderStatus())
                .setTotalAmount(order.getTotalAmount());
    }

    private class OrderMessage {

        private Long orderId;

        private Integer orderType;

        private Integer orderStatus;

        private BigDecimal totalAmount;

        public Long getOrderId() {
            return orderId;
        }

        public OrderMessage setOrderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public OrderMessage setOrderType(Integer orderType) {
            this.orderType = orderType;
            return this;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public OrderMessage setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public OrderMessage setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
    }
}
