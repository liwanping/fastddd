package org.fastddd.sample.service.eventhandler;

import com.alibaba.fastjson.JSON;
import org.fastddd.api.event.EventHandler;
import org.fastddd.api.retry.Retryable;
import org.fastddd.sample.service.domain.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorEventHandler.class);

    private int retryCount;

    @EventHandler(retryable = @Retryable(maxAttempts = 5))
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        retryCount++;
        if (retryCount % 3 != 0) {
            LOGGER.info("throwing error for order created event: {}", JSON.toJSONString(event.getOrder()));
            throw new RuntimeException("order created error!");
        }
    }
}
