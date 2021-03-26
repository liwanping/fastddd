package org.fastddd.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotaionType) {

        T annotation = (T)method.getAnnotation(annotaionType);
        if (annotation != null) {
            return annotation;
        }

        Class<?> currentClass = method.getDeclaringClass().getSuperclass();
        while (annotation == null && !currentClass.equals(Object.class)) {
            try {
                Method superMethod = currentClass.getMethod(method.getName(), method.getParameterTypes());
                annotation = superMethod.getAnnotation(annotaionType);
            } catch (NoSuchMethodException e) {
                // ignore
            }
            currentClass = currentClass.getSuperclass();
        }
        return annotation;
    }

    public static void doWithMethods(Class<?> targetClass, MethodCallback methodCallback) {
        do {
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                try {
                    methodCallback.doWith(method);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException(
                            "Shouldn't be illegal to access method '" + method.getName() + "':" + ex);
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && !targetClass.equals(Object.class));
    }

    public interface MethodCallback {
        void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
    }
}
