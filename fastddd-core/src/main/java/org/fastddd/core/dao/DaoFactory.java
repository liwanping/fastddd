package org.fastddd.core.dao;

import org.fastddd.core.entity.DomainObject;

import java.io.Serializable;

public interface DaoFactory {

    <T extends DomainObject<ID>, ID extends Serializable> DomainObjectDao<T, ID> getDao(Class<T> entityClass);
}
