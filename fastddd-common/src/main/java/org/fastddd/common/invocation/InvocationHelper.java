package org.fastddd.common.invocation;

import org.fastddd.common.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

public final class InvocationHelper {

    private static Set<InvocationHook> invocationHooks = new HashSet<>();

    static {
        ServiceLoader<InvocationHook> invocationHookServiceLoader = ServiceLoader.load(InvocationHook.class);
        for (InvocationHook invocationHook : invocationHookServiceLoader) {
            invocationHooks.add(invocationHook);
        }
    }

    public static Object doInvoke(Invocation invocation) {
        Object result = ReflectionUtils.invokeMethod(invocation.getMethod(),
                invocation.getTarget(),
                invocation.getParams());
        afterInvoke(invocation);
        return result;
    }

    public static void beforeInvoke(Invocation invocation) {
        for (InvocationHook invocationHook : invocationHooks) {
            invocationHook.beforeInvoke(invocation);
        }
    }

    public static void afterInvoke(Invocation invocation) {
        for (InvocationHook invocationHook : invocationHooks) {
            invocationHook.afterInvoke(invocation);
        }
    }
}
