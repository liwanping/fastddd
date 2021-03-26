package org.fastddd.api.factory;

import org.fastddd.api.exception.SystemException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBuilder {

    private static final ConcurrentHashMap<Class, Object> FACTORY_MAP = new ConcurrentHashMap<>();

    public static <T> T getFactory(Class<T> clazz) {

        if (FACTORY_MAP.containsKey(clazz)) {
            return (T)FACTORY_MAP.get(clazz);
        }

        for (Map.Entry<Class, Object> entry : FACTORY_MAP.entrySet()) {
            if (clazz.isAssignableFrom(entry.getKey())) {
                FACTORY_MAP.put(clazz, entry.getValue());
                return (T)entry.getValue();
            }
        }
        throw new SystemException("No qualified factory defined for class: " + clazz.getSimpleName());
    }

    public static <T> void registerFactory(T factory) {
        FACTORY_MAP.putIfAbsent(factory.getClass(), factory);
    }
}
