package org.fastddd.sample.service.infra.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: frank.li
 * @date: 2021-06-06
 */
@Component
public class OrderMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMessageSender.class);

    public void sendOrderMessage(String message) {
        LOGGER.info("sending order message: {}", message);
    }
}
