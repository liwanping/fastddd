package org.fastddd.spring.boot.autoconfigure;

import org.fastddd.core.event.aop.EventSponsorAspect;
import org.fastddd.spring.event.AnnotationEventHandlerBeanPostProcessor;
import org.fastddd.spring.factory.SpringFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AnnotationEventHandlerBeanPostProcessor.class)
public class FastdddAutoConfiguration {

    @Bean
    public SpringFactory springFactory() {
        return new SpringFactory();
    }

    @Bean
    public EventSponsorAspect eventSponsorAspect() {
        return new EventSponsorAspect();
    }

}
