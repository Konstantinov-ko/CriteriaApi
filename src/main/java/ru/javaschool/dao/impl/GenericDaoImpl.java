package ru.javaschool.dao.impl;


import org.springframework.transaction.annotation.Transactional;
import ru.javaschool.dao.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public abstract class GenericDaoImpl<Entity, Id> implements GenericDao<Entity, Id> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<Entity> daoClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        daoClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Transactional
    public void create(Entity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(Entity entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void remove(Entity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public Entity get(Id key) {
        return entityManager.find(daoClass, key);
    }
}
