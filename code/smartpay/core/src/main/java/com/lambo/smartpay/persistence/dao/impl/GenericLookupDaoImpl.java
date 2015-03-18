package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.GenericLookupDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Generic lookup dao for all lookup domains, including *Status and *Type objects.
 * Is the super interface for all Dao of the lookup objects.
 * <p/>
 * Created by swang on 2/24/2015.
 */
public abstract class GenericLookupDaoImpl<T, PK extends Serializable> extends GenericDaoImpl<T, PK>
        implements GenericLookupDao<T, PK> {

    private final static Logger LOG = LoggerFactory.getLogger(GenericLookupDaoImpl.class);

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericLookupDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
        LOG.debug("Getting generic type parameter " + type.getName());
    }

    /**
     * Find object by name.
     *
     * @param name is name of the object.
     * @return generic object specified by name.
     */
    @Override
    public T findByName(String name) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);

        // get path for name attribute
        Path<String> path = root.get("name");
        // formulate name predicate
        Predicate predicate = builder.equal(path, name);
        query.where(predicate);

        // create TypedQuery
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Find object by code.
     *
     * @param code is code of the object.
     * @return generic object specified by code.
     */
    @Override
    public T findByCode(String code) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);

        // get path for code attribute
        Path<String> path = root.get("code");
        // formulate code predicate
        Predicate predicate = builder.equal(path, code);
        query.where(predicate);

        // create TypedQuery
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
