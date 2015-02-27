package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.LookupGenericDao;
import com.lambo.smartpay.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Generic lookup dao for all lookup domains, including *Status and *Type objects.
 * Is the super interface for all Dao of the lookup objects.
 * <p/>
 * Created by swang on 2/24/2015.
 */
public abstract class LookupGenericDaoImpl<T, PK extends Serializable> extends GenericDaoImpl<T, PK>
        implements LookupGenericDao<T, PK> {

    private final static Logger LOG = LoggerFactory.getLogger(LookupGenericDaoImpl.class);

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public LookupGenericDaoImpl() {
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
        return typedQuery.getSingleResult();
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
        return typedQuery.getSingleResult();
    }

    /**
     * Count the number of all object.
     *
     * @param search     the keyword to search, eg. m.name LIKE *lambo*
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return count of the result
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(type);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, search);

        if (activeFlag != null) {
            // set active Predicate
            // literal true expression
            Path<Boolean> activePath = root.get("active");
            Predicate activePredicate = builder.equal(activePath, activeFlag);

            predicate = builder.and(predicate, activePredicate);
        }

        query.where(predicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        LOG.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find all objects.
     *
     * @param search     keyword to search eg. m.name LIKE *lambo*
     * @param start      the offset of the result list
     * @param length     total count of the result list
     * @param order      which column to order the result, including the direct relationship
     * @param orderDir   direction of the order, ASC or DESC
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return List of MerchantStatus matching search, starting from start offest and max of length
     */
    @Override
    public List<T> findByAdHocSearch(String search, Integer start, Integer length,
                                     String order, ResourceUtil.JpaOrderDir orderDir,
                                     Boolean activeFlag) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, search);

        if (activeFlag != null) {
            // set active Predicate
            // literal true expression
            Path<Boolean> activePath = root.get("active");
            Predicate activePredicate = builder.equal(activePath, activeFlag);

            predicate = builder.and(predicate, activePredicate);
        }

        query.where(predicate);

        // formulate order by
        Order orderBy = formulateOrderBy(builder, root, order, orderDir);
        query.orderBy(orderBy);

        // create TypedQuery
        TypedQuery<T> typedQuery = entityManager.createQuery(query);

        // set pagination on TypedQuery
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        LOG.debug("findByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Iterate all attributes to set predicates of the query.
     * For lookup entity, the wildcard search supports name, code and description.
     *
     * @param search ad hoc query string
     * @return jpa predicate with all criteria set up
     */
    protected Predicate formulatePredicate(CriteriaBuilder builder, Root<T> root, String search) {

        // get all paths for all attributes except active
        Path<String> namePath = root.get("name");
        Path<String> codePath = root.get("code");
        Path<String> descriptionPath = root.get("description");

        // formulate all predicates
        // String pattern matching with wildcards
        String likeSearch = "%" + search + "%";


        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate codePredicate = builder.like(codePath, likeSearch);
        Predicate descriptionPredicate = builder.like(descriptionPath, likeSearch);

        Predicate predicate = builder.or(namePredicate, codePredicate, descriptionPredicate);

        // we don't want to have wildcard search on id actually
        // if search is numeric, search against id
//        if (StringUtils.isNumeric(search)) {
//            Path<Long> idPath = root.get("id");
//            // use jpa literal to create Expression
//            Predicate idPredicate = builder.equal(idPath, builder.literal(Long.valueOf(search)));
//            predicate = builder.or(idPredicate, namePredicate, codePredicate,
// descriptionPredicate);
//        }

        LOG.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate ORDER BY clause of the jpa query.
     * For lookup entity order supports id, name, code and description asc/desc.
     *
     * @param order    order attribute.
     * @param orderDir order direction, ASC or DESC.
     * @return jpa Order clause
     */
    protected Order formulateOrderBy(CriteriaBuilder builder, Root<T> root,
                                     String order, ResourceUtil.JpaOrderDir orderDir) {

        // get all paths for all attributes
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> codePath = root.get("code");
        Path<String> descriptionPath = root.get("description");

        // default is ORDER BY id DESC
        Order orderBy = null;

        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "name":
                        orderBy = builder.asc(namePath);
                        break;
                    case "code":
                        orderBy = builder.asc(codePath);
                        break;
                    case "description":
                        orderBy = builder.asc(descriptionPath);
                        break;
                    default:
                        orderBy = builder.asc(idPath);
                        break;
                }
                break;

            case DESC:
                switch (order) {
                    case "id":
                        orderBy = builder.desc(idPath);
                        break;
                    case "name":
                        orderBy = builder.desc(namePath);
                        break;
                    case "code":
                        orderBy = builder.desc(codePath);
                        break;
                    case "description":
                        orderBy = builder.desc(descriptionPath);
                        break;
                    default:
                        orderBy = builder.desc(idPath);
                        break;
                }
                break;

            default:
                orderBy = builder.desc(idPath);
                break;
        }

        LOG.debug("Formulated order by clause is " + orderBy.toString());
        return orderBy;
    }
}
