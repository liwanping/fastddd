package org.fastddd.core.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Injector factory to hold the google injector instance
 * @author: frank.li
 * @date: 2021/3/29
 */
public abstract class InjectorFactory {

    private static Injector injector;

    static {
        injector = Guice.createInjector(new CoreModule());
    }

    public static <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}
