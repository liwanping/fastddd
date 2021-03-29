package org.fastddd.common.utils;

import org.fastddd.common.exception.ReflectionRuntimeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
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

    public static Object invokeMethod(Method method, Object target, Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never arrive at this point!");
    }

    private static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            throw new ReflectionRuntimeException("Invoke method exception thrown", ex);
        }
        throw new ReflectionRuntimeException("Unexpected exception thrown", ex);
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
