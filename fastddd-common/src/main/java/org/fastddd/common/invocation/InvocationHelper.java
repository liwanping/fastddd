package org.fastddd.common.invocation;

import org.fastddd.common.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class InvocationHelper {

    private static List<InvocationHook> invocationHooks = new ArrayList<>();

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
