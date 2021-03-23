package org.fastddd.core.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractSimpleDomainObject<ID extends Serializable> extends AbstractDomainObject<ID> {

    private Date createdTime;

    private Date updatedTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }
}
