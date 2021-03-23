package org.fastddd.spring.event;

import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.event.bus.EventBusFactory;
import org.fastddd.core.event.annotation.EventHandler;
import org.fastddd.core.event.listener.AnnotationEventListener;
import org.fastddd.core.event.listener.EventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AnnotationEventHandlerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isCandidateBean(bean, beanName)) {
            EventListener eventListener = new AnnotationEventListener(bean);
            EventBusFactory.getEventBus().subscribe(eventListener);
        }
        return bean;
    }

    private boolean isCandidateBean(Object bean, String beanName) {
        final AtomicBoolean result = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            if (method.isAnnotationPresent(EventHandler.class)) {
                result.set(true);
            }
        });
        return result.get();
    }
}
