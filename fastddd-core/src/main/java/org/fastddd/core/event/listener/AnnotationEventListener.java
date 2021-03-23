package org.fastddd.core.event.listener;

import org.fastddd.common.exception.SystemException;
import org.fastddd.core.event.EventInvocation;
import org.fastddd.core.event.PayloadEvent;
import org.fastddd.core.event.annotation.EventHandler;
import org.fastddd.common.utils.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AnnotationEventListener implements EventListener {

    private final Object target;
    private final List<Method> methods;

    public AnnotationEventListener(Object target) {
        this.target = target;
        this.methods = ClassUtils.getAnnotatedMethods(target.getClass(), EventHandler.class);
    }

    @Override
    public Class<?> getTargetType() {
        return target.getClass();
    }

    @Override
    public List<EventInvocation> generateInvocations(Collection<PayloadEvent> events) {

        Map<Class<? extends PayloadEvent>, List<PayloadEvent>> eventMap = new HashMap<>();
        for (PayloadEvent event : events) {
            if (!eventMap.containsKey(event.getClass())) {
                eventMap.put(event.getClass(), new ArrayList<>());
            }
            eventMap.get(events.getClass()).add(event);
        }

        List<EventInvocation> invocations = new ArrayList<>();

        for (Method method : methods) {
            Type[] types = method.getGenericParameterTypes();
            if (types.length <= 0) {
                continue;
            }
            if (types.length > 1) {
                throw new SystemException(String.format("Invalid event method parameters, class:%s, method:%s",
                        method.getClass().getName(), method.getName()));
            }

            Type eventType = types[0];
            for (Map.Entry<Class<? extends PayloadEvent>, List<PayloadEvent>> entry : eventMap.entrySet()) {
                if (isTypeEquals(eventType, entry.getKey())) {
                    for (PayloadEvent payloadEvent : entry.getValue()) {
                        invocations.add(new EventInvocation(method, this.target, payloadEvent));
                    }
                } else if (isCollectionOfType(eventType, entry.getKey())) {
                    invocations.add(new EventInvocation(method, this.target, entry.getValue()));
                }
            }
        }

        return invocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnotationEventListener that = (AnnotationEventListener) o;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return target != null ? target.hashCode() : 0;
    }

    private boolean isTypeEquals(Type type, Class<?> targetClass) {
        return type.equals(targetClass);
    }

    private boolean isCollectionOfType(Type type, Class<?> targetClass) {

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() instanceof Class &&
                    Collection.class.isAssignableFrom((Class) parameterizedType.getRawType())) {
                for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                    if (actualTypeArgument.equals(targetClass)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
