package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BatchDomainObjectDao<T extends DomainObject<ID>, ID extends Serializable> extends DomainObjectDao<T, ID> {

    List<T> findByIds(Collection<ID> ids);

    int insertAll(Collection<T> entities);

    int updateAll(Collection<T> entities);

    int deleteAll(Collection<T> entities);
}
