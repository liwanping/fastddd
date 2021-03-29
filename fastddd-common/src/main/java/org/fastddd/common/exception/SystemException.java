package org.fastddd.common.exception;

/**
 * @author: frank.li
 * @date: 2021/3/29
 */
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
