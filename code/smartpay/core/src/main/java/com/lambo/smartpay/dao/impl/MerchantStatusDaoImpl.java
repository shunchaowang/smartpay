package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.model.MerchantStatus;
import com.lambo.smartpay.util.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by swang on 2/17/2015.
 */
@Repository("merchantStatusDao")
public class MerchantStatusDaoImpl extends GenericDaoImpl<MerchantStatus, Long>
        implements MerchantStatusDao {

    @Override
    public MerchantStatus findByName(String name) {

        Query query = this.entityManager.createQuery("SELECT m FROM MerchantStatus m WHERE m.name = :name");
        query.setParameter("name", name);

        List<MerchantStatus> merchantStatuses = query.getResultList();
        if (merchantStatuses != null && merchantStatuses.size() == 1) {
            return merchantStatuses.get(0);
        }

        return null;
    }

    @Override
    public List<MerchantStatus> getAll() {

        Query query = this.entityManager.createQuery("SELECT m FROM MerchantStatus m");
        List<MerchantStatus> merchantStatuses = query.getResultList();

        if (merchantStatuses != null) {
            return merchantStatuses;
        }

        return null;
    }

    /**
     * Count the number of the record using search keyword against all attributes of MerchantStatus,
     * including direct many to one relationships..
     *
     * @param search the keyword to search, eg. m.name LIKE *lambo*, the space needs to be trimmed before passing
     * @return count of the result
     */
    @Override
    public Long countByAdHocSearch(String search) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<MerchantStatus> root = query.from(MerchantStatus.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, search);

        query.where(predicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find all records using search keyword against all attributes of MerchantStatus,
     * including direct many to one relationships.
     *
     * @param search   keyword to search eg. m.name LIKE *lambo*
     * @param start    the offset of the result list
     * @param length   total count of the result list
     * @param order    which column to order the result, including the direct relationship
     * @param orderDir direction of the order, ASC or DESC
     * @return List of MerchantStatus matching search, starting from start offest and max of length
     */
    @Override
    public List<MerchantStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceUtil.JpaOrderDir orderDir) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MerchantStatus> query = builder.createQuery(MerchantStatus.class);
        Root<MerchantStatus> root = query.from(MerchantStatus.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, search);
        query.where(predicate);

        // formulate order by
        Order orderBy = formulateOrderBy(builder, root, order, orderDir);
        query.orderBy(orderBy);

        // create TypedQuery
        TypedQuery<MerchantStatus> typedQuery = entityManager.createQuery(query);

        // set pagination on TypedQuery
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Iterate all attributes to set predicates of the query.
     * @param search ad hoc query string
     * @return jpa predicate with all criteria set up
     * */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<MerchantStatus> root, String search) {

        // get all paths for all attributes
        Path<String> namePath = root.get("name");
        Path<String> codePath = root.get("code");
        Path<String> descriptionPath = root.get("description");
        Path<Boolean> activePath = root.get("active");

        // formulate all predicates
        // String pattern matching with wildcards
        String likeSearch = "%" + search + "%";

        // literal true expression
        Expression<Boolean> trueExpression = builder.literal(true);
        Predicate activePredicate = builder.equal(activePath, trueExpression);
        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate codePredicate = builder.like(codePath, likeSearch);
        Predicate descriptionPredicate = builder.like(descriptionPath, likeSearch);

        Predicate orPredicate = builder.or(namePredicate, codePredicate, descriptionPredicate);

        // if search is numeric, search against id
        if (StringUtils.isNumeric(search)) {
            Path<Long> idPath = root.get("id");
            Predicate idPredicate = builder.equal(idPath, Long.valueOf(search));
            orPredicate = builder.or(idPredicate, namePredicate, codePredicate, descriptionPredicate);
        }

        Predicate andPredicate = builder.and(activePredicate, orPredicate);

        return andPredicate;
    }

    /**
     * Formulate ORDER BY clause of the jpa query.
     * @param order order attribute
     * @param orderDir order direction, ASC or DESC
     * @return jpa Order clause
     * */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<MerchantStatus> root,
                                   String order, ResourceUtil.JpaOrderDir orderDir) {

        // get all paths for all attributes
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> codePath = root.get("code");
        Path<String> descriptionPath = root.get("description");
        Path<Boolean> activePath = root.get("active");

        // default is ORDER BY id DESC
        Order orderBy = builder.desc(idPath);

        switch (orderDir) {
            case ASC:
                orderBy = builder.asc(idPath);
                if (order.equals("name")) {
                    orderBy = builder.asc(namePath);
                } else if (order.equals("description")) {
                    orderBy = builder.asc(descriptionPath);
                } else if (order.equals("code")) {
                    orderBy = builder.asc(codePath);
                } else if (order.equals("active")) {
                    orderBy = builder.asc(activePath);
                }
                break;

            case DESC:
                orderBy = builder.desc(idPath);
                if (order.equals("name")) {
                    orderBy = builder.desc(namePath);
                } else if (order.equals("description")) {
                    orderBy = builder.desc(descriptionPath);
                } else if (order.equals("code")) {
                    orderBy = builder.desc(codePath);
                } else if (order.equals("active")) {
                    orderBy = builder.desc(activePath);
                }
                break;

            default:
                orderBy = builder.desc(idPath);
                if (order.equals("name")) {
                    orderBy = builder.desc(namePath);
                } else if (order.equals("description")) {
                    orderBy = builder.desc(descriptionPath);
                } else if (order.equals("code")) {
                    orderBy = builder.desc(codePath);
                } else if (order.equals("active")) {
                    orderBy = builder.desc(activePath);
                }
                break;
        }

        return orderBy;
    }
}
