package org.fastddd.sample.service.factory;

import org.fastddd.sample.api.dto.OrderDto;
import org.fastddd.sample.api.dto.OrderLineDto;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.entity.OrderLine;

import java.math.BigDecimal;

public class OrderFactory {

    public static Order buildOrder(OrderDto orderDto) {

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderLineDto orderLine : orderDto.getOrderLines()) {
            totalAmount = totalAmount.add(orderLine.getUnitPrice().multiply(new BigDecimal(orderLine.getQuantity())));
        }

        Order order = new Order(orderDto.getOrderType(), totalAmount);

        for (OrderLineDto orderLine : orderDto.getOrderLines()) {
            order.addOrderLine(new OrderLine(orderLine.getItemId(),
                    orderLine.getQuantity(),
                    orderLine.getUnitPrice()));
        }

        return order;
    }
}
