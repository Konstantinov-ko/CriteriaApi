package ru.javaschool.dao.impl;


import org.springframework.transaction.annotation.Transactional;
import ru.javaschool.dao.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDaoImpl<Entity, Id> implements GenericDao<Entity, Id> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<Entity> daoClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        daoClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public void create(Entity entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(Entity entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void remove(Entity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public Entity get(Id key) {
        return entityManager.find(daoClass, key);
    }

    @Override
    public List<Entity> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(daoClass);
        Root<Entity> root = criteriaQuery.from(daoClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
