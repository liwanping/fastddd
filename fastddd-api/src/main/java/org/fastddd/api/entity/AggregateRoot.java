package org.fastddd.api.entity;

import org.fastddd.api.event.DomainEvent;

import java.io.Serializable;

/**
 * Basic aggregate root interface
 *
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface AggregateRoot<ID extends Serializable> extends DomainObject<ID> {

    /**
     * register domain event
     * @param event
     */
    void register(DomainEvent event);
}
