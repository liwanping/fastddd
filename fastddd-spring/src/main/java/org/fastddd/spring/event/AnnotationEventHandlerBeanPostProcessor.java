package org.fastddd.spring.event;

import org.fastddd.api.event.EventHandler;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.event.bus.EventBus;
import org.fastddd.core.event.listener.AnnotationEventListener;
import org.fastddd.core.event.listener.EventListener;
import org.fastddd.core.event.processor.async.AsyncInvoker;
import org.fastddd.core.injector.InjectorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Bean processor to scan and handle @EventHandler
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AnnotationEventHandlerBeanPostProcessor implements BeanPostProcessor, DisposableBean {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isCandidateBean(bean, beanName)) {
            EventListener eventListener = new AnnotationEventListener(bean);
            InjectorFactory.getInstance(EventBus.class).subscribe(eventListener);
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

    @Override
    public void destroy() throws Exception {
        InjectorFactory.getInstance(AsyncInvoker.class).shutdown();
    }
}
