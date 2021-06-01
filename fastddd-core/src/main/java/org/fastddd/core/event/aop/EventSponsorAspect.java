package org.fastddd.core.event.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.session.SessionManager;


/**
 * @author: frank.li
 * @date: 2021-06-01
 */
@Aspect
public class EventSponsorAspect {

    private SessionManager sessionManager = InjectorFactory.getInstance(SessionManager.class);

    @Pointcut("@annotation(org.fastddd.api.event.EventSponsor)")
    public void eventPointcut() {
    }

    @Around("eventPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        boolean success = sessionManager.registerSession(false);

        try {
            Object result = proceedingJoinPoint.proceed();

            if (success) {
                sessionManager.requireSession().commit();
                sessionManager.requireSession().cleanupAfterCompletion();
            }

            return result;

        } finally {
            if (success) {
                sessionManager.closeSession();
            }
        }
    }
}
