package org.fastddd.core.entity;

import java.io.Serializable;

public interface DomainObject<ID extends Serializable> extends Serializable  {

    ID getId();

    void setId(ID id);

    boolean isNew();
}
