package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;

/**
 * The dao factory
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface DaoFactory {

    /**
     * Retrieve the dao implementation to operate on the given entity class
     *
     * @param entityClass
     * @param <T>
     * @param <ID>
     * @return
     */
    <T extends DomainObject<ID>, ID extends Serializable> DomainObjectDao<T, ID> getDao(Class<T> entityClass);
}
