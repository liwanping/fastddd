package org.fastddd.common.exception;

public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable t) {
        super(t);
    }

    public SystemException(String message, Throwable t) {
        super(message, t);
    }
}
