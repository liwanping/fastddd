package org.fastddd.api.entity;

import java.io.Serializable;

/**
 * Basic domain object interface
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface DomainObject<ID extends Serializable> extends Serializable  {

    ID getId();

    void setId(ID id);

    boolean isNew();
}
