package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;

public interface DomainObjectDao<T extends DomainObject<ID>, ID extends Serializable> {

    T findById(ID id);

    int insert(T entity);

    int update(T entity);

    int delete(T entity);
}
