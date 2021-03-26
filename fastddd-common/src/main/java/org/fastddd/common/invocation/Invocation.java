package org.fastddd.common.invocation;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class Invocation {

    private final Method method;
    private final Object target;
    private final Object[] params;

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

    @Override
    public String toString() {
        return "Invocation{" +
                "method=" + method +
                ", target=" + target +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
