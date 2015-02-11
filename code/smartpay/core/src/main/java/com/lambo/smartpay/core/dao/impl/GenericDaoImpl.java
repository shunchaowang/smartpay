package com.lambo.smartpay.core.dao.impl;

import com.lambo.smartpay.core.dao.GenericDao;
import com.lambo.smartpay.core.util.ResourceUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public T create(T persistentObject) {
        entityManager.persist(persistentObject);
        return persistentObject;
    }

    @Override
    public List<T> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T update(T persistentObject) {
        return entityManager.merge(persistentObject);
    }

    @Override
    public void delete(PK id) {
        entityManager.remove(entityManager.getReference(type, id));
    }

    @Override
    public T get(PK id) {
        return entityManager.find(type, id);
    }

    /**
     * Counts the record using positional parameters.
     *
     * @param criteria
     * @param args
     */
    @Override
    public Long count(String criteria, List<Object> args) {
        Query query = entityManager.createQuery(criteria);

        // set positional params
        for (int i = 0; i < args.size(); i++) {
            query.setParameter(i, args.get(i));
        }

        return (Long) query.getSingleResult();
    }

    /**
     * Counts the record using name-value-pair parameters.
     *
     * @param criteria
     * @param args
     */
    @Override
    public Long count(String criteria, Map<String, Object> args) {

        Query query = entityManager.createQuery(criteria);
        for (String key: args.keySet()) {
            query.setParameter(key, args.get(key));
        }

        return (Long) query.getSingleResult();
    }

    /**
     * Find all records using positional parameters.
     *
     * @param criteria
     * @param args
     */
    @Override
    public List<T> findAll(String criteria, List<Object> args) {
        return null;
    }

    /**
     * Find all records using name-value-pair.
     *
     * @param criteria
     * @param args
     */
    @Override
    public List<T> findAll(String criteria, Map<String, Object> args) {
        return null;
    }

    /**
     * Calculate the first result of the query based on page number and size per page.
     * */
    private int calculateFirstResult(Integer pageNumber, Integer pageSize) {

        if (pageNumber == null) {
            pageNumber = 1;
        }

        return (pageNumber - 1) * pageSize;
    }

    private Query createPaginatedQuery(String criteria, Integer pageNumber, Integer pageSize) {

        // if pageSize is null, set it to default value
        if (pageSize == null) {
            pageSize = ResourceUtil.PAGE_SIZE;
        }

        int firstResult = calculateFirstResult(pageNumber, pageSize);

        Query query = entityManager.createQuery(criteria).setFirstResult(firstResult).setMaxResults(pageSize);
        return query;
    }

    /**
     * Find all records using positional parameters with pagination.
     *
     * @param criteria
     * @param args
     */
    @Override
    public List<T> findAll(String criteria, List<Object> args, Integer pageNumber, Integer pageSize) {

        Query query = createPaginatedQuery(criteria, pageNumber, pageSize);
        for (int i = 0; i < args.size(); i++) {
            query.setParameter(i, args.get(i));
        }

        return query.getResultList();
    }

    /**
     * Find all records using name-value-pair with pagination.
     *
     * @param criteria
     * @param args
     */
    @Override
    public List<T> findAll(String criteria, Map<String, Object> args, Integer pageNumber, Integer pageSize) {

        Query query = createPaginatedQuery(criteria, pageNumber, pageSize);

        for (String key: args.keySet()) {
            query.setParameter(key, args.get(key));
        }
        return query.getResultList();
    }

    /**
     * Find all records with pagination without parameters.
     *
     * @param criteria
     * @param pageNumber
     * @param pageSize
     */
    @Override
    public List<T> findAll(String criteria, Integer pageNumber, Integer pageSize) {

        Query query = createPaginatedQuery(criteria, pageNumber, pageSize);

        return query.getResultList();
    }
}
