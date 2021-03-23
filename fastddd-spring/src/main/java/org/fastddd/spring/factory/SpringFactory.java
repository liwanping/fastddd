package org.fastddd.spring.factory;

import org.fastddd.common.exception.SystemException;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.core.dao.DaoFactory;
import org.fastddd.core.dao.DomainObjectDao;
import org.fastddd.core.entity.DomainObject;
import org.fastddd.core.event.PayloadEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringFactory implements DaoFactory, ApplicationContextAware {

    private ApplicationContext context;

    private Map<Class<? extends DomainObject>, DomainObjectDao> daoMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        FactoryBuilder.registerFactory(this);
    }

    @Override
    public <T extends DomainObject<ID>, ID extends Serializable> DomainObjectDao<T, ID> getDao(Class<T> entityClass) {

        DomainObjectDao<T, ID> foundDao = daoMap.get(entityClass);
        if (foundDao == null) {
            synchronized (entityClass) {
                foundDao = daoMap.get(entityClass);
                if (foundDao == null) {
                    foundDao = doFindDao(entityClass);
                }
            }
        }

        return foundDao;
    }

    private <ID extends Serializable, T extends DomainObject<ID>> DomainObjectDao<T, ID> doFindDao(Class<T> entityClass) {

        DomainObjectDao<T, ID> foundDao;

        Map<String, DomainObjectDao> daoBeans = context.getBeansOfType(DomainObjectDao.class);
        for (DomainObjectDao<T, ID> dao : daoBeans.values()) {
            Class[] classes = dao.getClass().getInterfaces();
            for (Class clazz : classes) {
                Type[] types = clazz.getGenericInterfaces();
                for (Type type : types) {
                    if (type instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) type;
                        for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                            if (isEntityMatch(actualTypeArgument, entityClass)) {
                                foundDao = dao;
                                daoMap.put(entityClass, dao);
                                return foundDao;
                            }
                        }
                    }
                }
            }
        }

        throw new SystemException("No DAO defined for class: " + entityClass.getCanonicalName());
    }

    private boolean isEntityMatch(Type providedType, Type requiredType) {

        if (providedType == requiredType) {
            return true;
        }
        if (providedType instanceof Class &&
                requiredType instanceof Class &&
                DomainObject.class.isAssignableFrom((Class)providedType) &&
                DomainObject.class.isAssignableFrom((Class)requiredType)) {
            Class providedClass = (Class)providedType;
            Class requiredClass = (Class)requiredType;
            while (requiredClass != null && !requiredClass.equals(DomainObject.class)) {
                if (providedClass == requiredClass) {
                    return true;
                }
                // no exactly matched dao defined, use the dao of its super class instead
                requiredClass = requiredClass.getSuperclass();
            }
        }
        return false;
    }


}
