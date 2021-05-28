package org.fastddd.common.invocation;

import org.fastddd.common.exception.ReflectionRuntimeException;
import org.fastddd.common.utils.ReflectionUtils;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public final class InvocationHelper {

    private static Set<InvocationHook> invocationHooks = new HashSet<>();

    static {
        ServiceLoader<InvocationHook> invocationHookServiceLoader = ServiceLoader.load(InvocationHook.class);
        for (InvocationHook invocationHook : invocationHookServiceLoader) {
            invocationHooks.add(invocationHook);
        }
    }

    public static Object doInvoke(Invocation invocation) {

        try {
            Object result = ReflectionUtils.invokeMethod(invocation.getMethod(),
                    invocation.getTarget(),
                    invocation.getParams());
            afterInvoke(invocation, result);
            return result;
        } catch (Throwable t) {
            afterThrow(invocation, t);
            throw new ReflectionRuntimeException(t);
        }
    }

    public static void beforeInvoke(Invocation invocation) {
        for (InvocationHook invocationHook : invocationHooks) {
            if (invocationHook.isQualified(invocation) &&
                    !invocationHook.beforeInvoke(invocation)) {
                //invocation hook chain stop
                break;
            }
        }
    }

    public static void afterInvoke(Invocation invocation, Object result) {
        for (InvocationHook invocationHook : invocationHooks) {
            if (invocationHook.isQualified(invocation)) {
                invocationHook.afterInvoke(invocation, result);
            }
        }
    }

    public static void afterThrow(Invocation invocation, Throwable t) {
        for (InvocationHook invocationHook : invocationHooks) {
            if (invocationHook.isQualified(invocation)) {
                invocationHook.afterThrow(invocation, t);
            }
        }
    }
}
