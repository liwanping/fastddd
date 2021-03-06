package org.fastddd.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * The abstract domain object
 * @author: frank.li
 * @date: 2021/3/29
 */
public abstract class AbstractDomainObject<ID extends Serializable> implements DomainObject<ID> {

    private boolean isNew;

    private Date createTime;

    private Date updateTime;

    @Override
    public boolean isNew() {
        return isNewId() || isNew;
    }

    protected void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        AbstractDomainObject<?> that = (AbstractDomainObject<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    private boolean isNewId() {

        ID id = getId();

        if (id == null) {
            return true;
        }

        if (id instanceof CompositeId) {
            return ((CompositeId) id).isNewId();
        }

        return false;
    }
}
