package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;

public interface DaoFactory {

    <T extends DomainObject<ID>, ID extends Serializable> DomainObjectDao<T, ID> getDao(Class<T> entityClass);
}
