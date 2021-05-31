package org.fastddd.common.invocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Invocation class to hold the required info for invoking
 * @author: frank.li
 * @date: 2021/3/29
 */
public final class Invocation {

    private final Method method;
    private final Object target;
    private final Object[] params;


    private transient final Map<String, Object> context = new HashMap<>();

    public Invocation(Method method, Object target, Object... params) {
        this.method = method;
        this.target = target;
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public Object getTarget() {
        return target;
    }

    public Object[] getParams() {
        return params;
    }

    public void putContextValue(String key, Object value) {
        context.put(key, value);
    }

    public <T> T getContextValue(String key, Class<T> valueType) {
        return (T)context.get(key);
    }

    public Object getContextValue(String key) {
        return context.get(key);
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "method=" + method +
                ", target=" + target +
                ", params=" + Arrays.toString(params) +
                ", context=" + context +
                '}';
    }
}
