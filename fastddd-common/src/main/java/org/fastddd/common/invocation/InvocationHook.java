package org.fastddd.common.invocation;

/**
 * The hook interface that will intercept the invocation before/after invoking.
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface InvocationHook {

    boolean beforeInvoke(Invocation invocation);

    void afterInvoke(Invocation invocation, Object result);

    void afterThrow(Invocation invocation, Throwable t);
}
