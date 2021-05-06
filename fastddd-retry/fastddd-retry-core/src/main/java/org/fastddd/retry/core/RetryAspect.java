package org.fastddd.retry.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHelper;
import org.fastddd.retry.core.utils.RetryUtils;

import java.lang.reflect.Method;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
@Aspect
public class RetryAspect {

    @Pointcut("@annotation(org.fastddd.api.retry.Retryable)")
    public void retryPointcut() {
    }

    @Around("retryPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {

        Method targetMethod = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Invocation invocation = new Invocation(targetMethod,
                proceedingJoinPoint.getTarget(), proceedingJoinPoint.getArgs());
        InvocationHelper.beforeInvoke(invocation);
        return RetryUtils.doWithRetry(invocation);
    }
}
