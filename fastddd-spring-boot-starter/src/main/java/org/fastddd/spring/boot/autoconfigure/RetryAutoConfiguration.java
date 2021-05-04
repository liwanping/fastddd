package org.fastddd.spring.boot.autoconfigure;

import org.fastddd.retry.core.RetryAspect;
import org.fastddd.spring.event.AnnotationEventHandlerBeanPostProcessor;
import org.fastddd.spring.factory.SpringFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class RetryAutoConfiguration {

    @Bean
    @Order
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }

}
