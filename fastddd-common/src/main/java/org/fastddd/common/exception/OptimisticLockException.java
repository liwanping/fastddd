package org.fastddd.common.exception;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
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
