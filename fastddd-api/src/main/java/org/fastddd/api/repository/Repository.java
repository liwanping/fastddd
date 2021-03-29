package org.fastddd.api.repository;

import java.io.Serializable;
import java.util.Collection;

/**
 * The repository interface to define the basic operations
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface Repository<T, ID extends Serializable> {

    T save(T entity);

    Collection<T> save(Collection<T> entities);

    T findById(ID id);

    Collection<T> findByIds(Collection<ID> ids);

    void delete(ID id);

    void delete(T entity);

    void delete(Collection<T> entities);

}
