package org.fastddd.core.dao;

import org.fastddd.core.entity.DomainObject;

import java.io.Serializable;
import java.util.List;

public interface BatchDomainObjectDao<T extends DomainObject<ID>, ID extends Serializable> extends DomainObjectDao<T, ID> {

    List<T> findByIds(List<ID> ids);

    int insertAll(List<T> entities);
}
