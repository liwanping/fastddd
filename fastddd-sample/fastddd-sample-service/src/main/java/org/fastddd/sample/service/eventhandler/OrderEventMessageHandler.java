package org.fastddd.sample.service.eventhandler;

import com.alibaba.fastjson.JSON;
import org.fastddd.api.event.EventHandler;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderEventMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventMessageHandler.class);

    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        LOGGER.info("sending order created message: {}", JSON.toJSONString(event.getOrder()));
    }
}
