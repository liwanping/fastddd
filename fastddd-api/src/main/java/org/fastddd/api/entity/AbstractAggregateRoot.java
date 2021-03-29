package org.fastddd.api.entity;

import java.io.Serializable;

/**
 * The abstract aggregate root
 * @author: frank.li
 * @date: 2021/3/29
 */
public abstract class AbstractAggregateRoot<ID extends Serializable> extends AbstractDomainObject<ID> implements AggregateRoot<ID> {

}
