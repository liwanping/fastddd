package org.fastddd.sample.service.exception;

/**
 * @author: frank.li
 * @date: 2021-06-06
 */
public class OrderDomainException extends RuntimeException {

    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(Throwable t) {
        super(t);
    }

    public OrderDomainException(String message, Throwable t) {
        super(message, t);
    }

}
