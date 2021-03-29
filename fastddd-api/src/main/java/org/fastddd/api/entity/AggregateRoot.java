package org.fastddd.api.entity;

import org.fastddd.api.event.PayloadEvent;

import java.io.Serializable;
import java.util.Collection;

/**
 * Basic aggregate root interface
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface AggregateRoot<ID extends Serializable> extends DomainObject<ID> {

}
