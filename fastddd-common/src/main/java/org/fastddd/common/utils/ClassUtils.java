package org.fastddd.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public final class ClassUtils {

    private static final String CGLIB_SUB_CLASS_IDENTIFIER = "CGLIB";

    public static <T extends Annotation> List<Method> getAnnotatedMethods(Class<?> clazz, Class<T> annotatedCLazz) {

        List<Method> methods = new ArrayList<>();
        Class<?> currentClazz = clazz;

        while (currentClazz != null && !currentClazz.equals(Object.class)) {
            if (!currentClazz.getName().contains(CGLIB_SUB_CLASS_IDENTIFIER)) {
                for (Method declaredMethod : currentClazz.getDeclaredMethods()) {
                    if (declaredMethod.getAnnotation(annotatedCLazz) != null) {
                        methods.add(declaredMethod);
                    }
                }
            }

            currentClazz = clazz.getSuperclass();
        }

        return methods;
    }
}
