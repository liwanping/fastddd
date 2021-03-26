package org.fastddd.api.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractSimpleAggregateRoot<ID extends Serializable> extends AbstractAggregateRoot<ID> {

    private Date createdTime;

    private Date updatedTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }
}
