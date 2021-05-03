package org.fastddd.api.repository;

import org.apache.commons.collections.CollectionUtils;
import org.fastddd.api.dao.BatchDomainObjectDao;
import org.fastddd.api.dao.DaoFactory;
import org.fastddd.api.dao.DomainObjectDao;
import org.fastddd.api.entity.AggregateRoot;
import org.fastddd.common.factory.FactoryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The repository that will implement the basic operations with the defined mysql instance
 * @author: frank.li
 * @date: 2021/3/29
 */
public class DaoAwareRepository<T extends AggregateRoot<ID>, ID extends Serializable> extends AbstractRepository<T, ID> {

    public DaoAwareRepository(Class<T> aggregateType) {
        super(aggregateType);
    }

    @Override
    protected int doInsert(Collection<T> entities) {

        DomainObjectDao<T, ID> dao = getDao(entities.iterator().next().getClass());

        int effectedCount = 0;
        if (dao instanceof BatchDomainObjectDao) {
            // batch insert all entities
            BatchDomainObjectDao<T, ID> batchDomainObjectDao = (BatchDomainObjectDao)dao;
            effectedCount = batchDomainObjectDao.insertAll(entities);
        } else {
            for (T entity : entities) {
                effectedCount += dao.insert(entity);
            }
        }

        doInsertChildComponents(entities);

        return effectedCount;
    }

    @Override
    protected int doUpdate(Collection<T> entities) {

        DomainObjectDao<T, ID> dao = getDao(entities.iterator().next().getClass());

        int effectedCount = 0;
        if (dao instanceof BatchDomainObjectDao) {
            // batch update all entities
            BatchDomainObjectDao<T, ID> batchDomainObjectDao = (BatchDomainObjectDao)dao;
            effectedCount = batchDomainObjectDao.updateAll(entities);
        } else {
            for (T entity : entities) {
                effectedCount += dao.update(entity);
            }
        }

        doUpdateChildComponents(entities);

        return effectedCount;
    }

    @Override
    protected int doDelete(Collection<T> entities) {

        DomainObjectDao<T, ID> dao = getDao(entities.iterator().next().getClass());

        int effectedCount = 0;
        if (dao instanceof BatchDomainObjectDao) {
            // batch delete all entities
            BatchDomainObjectDao<T, ID> batchDomainObjectDao = (BatchDomainObjectDao)dao;
            effectedCount = batchDomainObjectDao.deleteAll(entities);
        } else {
            for (T entity : entities) {
                effectedCount += dao.delete(entity);
            }
        }

        doDeleteChildComponents(entities);

        return effectedCount;
    }

    @Override
    protected List<T> doFindByIds(Collection<ID> ids) {

        DomainObjectDao<T, ID> dao = getDao(aggregateType);

        List<T> entities;
        if (dao instanceof BatchDomainObjectDao) {
            // batch query all entities
            BatchDomainObjectDao<T, ID> batchDomainObjectDao = (BatchDomainObjectDao)dao;
            entities = batchDomainObjectDao.findByIds(ids);
        } else {
            entities = new ArrayList<>();
            for (ID id : ids) {
                T entity = dao.findById(id);
                if (entity != null) {
                    entities.add(entity);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(entities)) {
            doFindChildComponents(entities);
        }

        return entities;
    }

    @Override
    protected T doFindById(ID id) {
        DomainObjectDao<T, ID> dao = getDao(aggregateType);
        T entity = dao.findById(id);
        if (entity != null) {
            doFindChildComponents(entity);
        }
        return entity;
    }

    /**
     * Insert all the child components for entities
     * @param entities
     */
    protected void doInsertChildComponents(Collection<T> entities) {

    }

    /**
     * Update all the child components for entities
     * @param entities
     */
    protected void doUpdateChildComponents(Collection<T> entities) {

    }

    /**
     * Delete all the child components for entities
     * @param entities
     */
    protected void doDeleteChildComponents(Collection<T> entities) {

    }

    /**
     * Find all the child components for entities
     * @param entities
     */
    protected void doFindChildComponents(Collection<T> entities) {

    }

    /**
     * Find all the child components for the entity
     * @param entity
     */
    protected void doFindChildComponents(T entity) {

    }

    private DomainObjectDao<T, ID> getDao(Class clazz) {
        return FactoryBuilder.getFactory(DaoFactory.class).getDao(clazz);
    }
}
