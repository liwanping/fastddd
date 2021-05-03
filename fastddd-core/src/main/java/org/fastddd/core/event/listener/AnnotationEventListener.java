package org.fastddd.core.event.listener;

import org.fastddd.common.exception.SystemException;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.DomainEvent;
import org.fastddd.api.event.EventHandler;
import org.fastddd.common.utils.ClassUtils;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.event.processor.async.AsyncConfig;
import org.fastddd.core.event.processor.async.AsyncInvoker;
import org.fastddd.core.event.processor.async.AsyncUtils;
import org.fastddd.core.injector.InjectorFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AnnotationEventListener that monitor the methods with @EventHandler annotated
 * @author: frank.li
 * @date: 2021/3/29
 */
public class AnnotationEventListener implements EventListener {

    private final Object target;
    private final List<Method> methods;

    public AnnotationEventListener(Object target) {
        this.target = target;
        this.methods = ClassUtils.getAnnotatedMethods(target.getClass(), EventHandler.class);
        init();
    }

    @Override
    public List<Invocation> onEvent(Collection<DomainEvent> events) {

        Map<Class<? extends DomainEvent>, List<DomainEvent>> eventMap = new HashMap<>();
        for (DomainEvent event : events) {
            if (!eventMap.containsKey(event.getClass())) {
                eventMap.put(event.getClass(), new ArrayList<>());
            }
            eventMap.get(event.getClass()).add(event);
        }

        List<Invocation> invocations = new ArrayList<>();

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
            for (Map.Entry<Class<? extends DomainEvent>, List<DomainEvent>> entry : eventMap.entrySet()) {
                if (isTypeEquals(eventType, entry.getKey())) {
                    for (DomainEvent domainEvent : entry.getValue()) {
                        invocations.add(new Invocation(method, this.target, domainEvent));
                    }
                } else if (isCollectionOfType(eventType, entry.getKey())) {
                    invocations.add(new Invocation(method, this.target, entry.getValue()));
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

    private void init() {
        // prepare for async invoke
        for (Method method : methods) {
            EventHandler eventHandler = ReflectionUtils.getAnnotation(method, EventHandler.class);
            if (eventHandler.asynchronous()) {
                AsyncConfig asyncConfig = AsyncUtils.buildAsyncConfig(target, method);
                InjectorFactory.getInstance(AsyncInvoker.class).start(asyncConfig);
            }
        }
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
