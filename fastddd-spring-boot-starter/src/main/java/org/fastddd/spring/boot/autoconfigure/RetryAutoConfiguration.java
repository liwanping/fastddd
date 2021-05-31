package org.fastddd.spring.boot.autoconfigure;

import org.fastddd.core.retry.RetryAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RetryAutoConfiguration {

    @Bean
    @Order
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }

}
