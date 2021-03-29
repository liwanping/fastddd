package org.fastddd.common.exception;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
public class ReflectionRuntimeException extends RuntimeException {

    public ReflectionRuntimeException(String message) {
        super(message);
    }

    public ReflectionRuntimeException(Throwable t) {
        super(t);
    }

    public ReflectionRuntimeException(String message, Throwable t) {
        super(message, t);
    }

}
