package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;
import java.util.List;

public interface BatchDomainObjectDao<T extends DomainObject<ID>, ID extends Serializable> extends DomainObjectDao<T, ID> {

    List<T> findByIds(List<ID> ids);

    int insertAll(List<T> entities);

    int updateAll(List<T> entities);
}
