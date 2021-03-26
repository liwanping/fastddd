package org.fastddd.common.exception;

public class OptimisticLockException extends RuntimeException {

    public OptimisticLockException(String message) {
        super(message);
    }

    public OptimisticLockException(Throwable t) {
        super(t);
    }

    public OptimisticLockException(String message, Throwable t) {
        super(message, t);
    }
}
