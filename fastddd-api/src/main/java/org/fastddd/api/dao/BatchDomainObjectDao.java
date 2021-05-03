package org.fastddd.api.dao;

import org.fastddd.api.entity.DomainObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Batch mysql operations for domain object
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface BatchDomainObjectDao<T extends DomainObject<ID>, ID extends Serializable> extends DomainObjectDao<T, ID> {

    List<T> findByIds(Collection<ID> ids);

    int insertAll(Collection<T> entities);

    default int updateAll(Collection<T> entities) {
        return 0;
    }

    default int deleteAll(Collection<T> entities) {
        return 0;
    }
}
