package org.fastddd.api.entity;

import org.fastddd.api.event.DomainEvent;
import org.fastddd.api.event.EventRegistry;

import java.io.Serializable;

/**
 * The abstract aggregate root
 * @author: frank.li
 * @date: 2021/3/29
 */
public abstract class AbstractAggregateRoot<ID extends Serializable> extends AbstractDomainObject<ID> implements AggregateRoot<ID> {

    public void register(DomainEvent event) {
        EventRegistry.register(event);
    }
}
