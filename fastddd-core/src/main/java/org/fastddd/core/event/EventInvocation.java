package org.fastddd.core.event;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class EventInvocation {

    private final Method method;
    private final Object target;
    private final Object[] params;

    public EventInvocation(Method method, Object target, Object... params) {
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

    @Override
    public String toString() {
        return "EventInvocation{" +
                "method=" + method +
                ", target=" + target +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
