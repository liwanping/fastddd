package org.fastddd.common.factory;

import java.util.Map;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public interface BeanFactory {

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName, Class<T> beanType);

    <T> T getBean(String beanName);

    <T> Map<String, T> getBeans(Class<T> beanType);
}
