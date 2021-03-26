package org.fastddd.api.repository;

import org.apache.commons.collections.CollectionUtils;
import org.fastddd.api.entity.AggregateRoot;
import org.fastddd.api.entity.CompositeId;
import org.fastddd.common.exception.OptimisticLockException;
import org.fastddd.common.exception.SystemException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractRepository<T extends AggregateRoot<ID>, ID extends Serializable> implements Repository<T, ID> {

    protected final Class<T> aggregateType;

    protected AbstractRepository(Class<T> aggregateType) {
        this.aggregateType = aggregateType;
    }

    protected abstract int doInsert(Collection<T> entities);

    protected abstract int doUpdate(Collection<T> entities);

    protected abstract int doDelete(Collection<T> entities);

    protected abstract List<T> doFindByIds(Collection<ID> ids);

    protected abstract T doFindById(ID id);

    @Override
    public T save(T entity) {
        save(Collections.singleton(entity));
        return entity;
    }

    @Override
    public List<T> save(final Collection<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        doSave(entities);
        return new ArrayList<>(entities);
    }

    @Override
    public T findById(ID id) {
        return doFindById(id);
    }

    @Override
    public Collection<T> findByIds(Collection<ID> ids) {
        return doFindByIds(ids);
    }

    @Override
    public void delete(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    @Override
    public void delete(T entity) {
        delete(Collections.singletonList(entity));
    }

    @Override
    public void delete(Collection<T> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            doRemove(entities);
        }
    }

    private void doSave(Collection<T> entities) {

        Class<?> idClass = getIdClass(this.aggregateType);

        List<T> insertEntities = new ArrayList<>();
        List<T> updateEntities = new ArrayList<>();

        for (T entity : entities) {
            ensureCompositeIdInitialized(idClass, entity);
            if (entity.isNew()) {
                insertEntities.add(entity);
            } else {
                updateEntities.add(entity);
            }
        }

        if (CollectionUtils.isNotEmpty(insertEntities)) {
            int effectedCount = doInsert(insertEntities);
            if (effectedCount < insertEntities.size()) {
                throw new OptimisticLockException("INSERT expected count:" + entities.size()+
                        ", but actual count:" + effectedCount);
            }
        }

        if (CollectionUtils.isNotEmpty(updateEntities)) {
            int effectedCount = doUpdate(updateEntities);
            if (effectedCount < updateEntities.size()) {
                throw new OptimisticLockException("UPDATE expected count:" + entities.size()+
                        ", but actual count:" + effectedCount);
            }
        }
    }

    private void doRemove(Collection<T> entities) {
        int effectedCount = doDelete(entities);
        if (effectedCount < entities.size()) {
            throw new OptimisticLockException("DELETE expected count:" + entities.size()+
                    ", but actual count:" + effectedCount);
        }
    }

    private void ensureCompositeIdInitialized(Class idClass, T entity) {
        if (entity.getId() == null && CompositeId.class.isAssignableFrom(idClass)) {
            try {
                entity.setId((ID)idClass.newInstance());
            } catch (Exception e) {
                throw new SystemException("Failed to initialize id class: " + idClass.getCanonicalName());
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static Class getIdClass(Class entityClass) {

        Class currentClass = entityClass;

        while (currentClass != null && !currentClass.equals(Object.class)) {

            Type type = currentClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                if (parameterizedType.getActualTypeArguments().length > 0) {
                    Type idType = parameterizedType.getActualTypeArguments()[0];
                    if (idType instanceof Class) {
                        return (Class)idType;
                    }
                }
                break;
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }
}
