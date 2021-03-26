package org.fastddd.common.invocation;

import org.fastddd.common.invocation.Invocation;

public interface InvocationHook {

    void beforeInvoke(Invocation invocation);

    void afterInvoke(Invocation invocation);
}
