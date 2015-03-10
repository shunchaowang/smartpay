package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.RefundDao;
import com.lambo.smartpay.persistence.entity.Refund;
import com.lambo.smartpay.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/8/2015.
 */
@Repository("refundDao")
public class RefundDaoImpl extends GenericDaoImpl<Refund, Long> implements RefundDao {

    private static final Logger logger = LoggerFactory.getLogger(RefundDaoImpl.class);

    /**
     * Count number of Refund matching the search. Support ad hoc search on attributes of Refund.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Refund> root = query.from(Refund.class);
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
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find all Refund matching the search. Support ad hoc search on attributes of Refund.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Refund.
     */
    @Override
    public List<Refund> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);
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

        TypedQuery<Refund> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Refund by criteria.
     * Support attributes of Refund.
     *
     * @param refund           contains criteria if the field is not null or empty.
     * @param createdTimeStart when Refund was created
     * @param createdTimeEnd   till when Refund was created
     * @return number of the Refund matching search
     */
    @Override
    public Long countByAdvanceSearch(Refund refund, Date createdTimeStart, Date createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, refund, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Refund by criteria.
     * Support attributes of Refund.
     *
     * @param refund           contains criteria if the field is not null or empty.
     * @param createdTimeStart when T was created
     * @param createdTimeEnd   till when T was created
     * @return List of the Refund matching search ordered by id without pagination.
     */
    @Override
    public List<Refund> findByAdvanceSearch(Refund refund, Date createdTimeStart, Date
            createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, refund, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);

// default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Refund> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param refund           contains criteria if the field is not null or empty.
     * @param createdTimeStart
     * @param createdTimeEnd
     * @param start
     * @param length           @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Refund> findByAdvanceSearch(Refund refund,
                                            Date createdTimeStart, Date createdTimeEnd,
                                            Integer start, Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, refund, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);

// default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Refund> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Refund> root, String
            search) {
        //TODO Add more search criteria
        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> bankNamePath = root.get("bankName");

        // create the predicate expression for all the path
        Predicate bankNamePredicate = builder.like(bankNamePath, likeSearch);

        Predicate predicate = builder.or(bankNamePredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Supports id, RefundStatus id, active, createdTime between.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param refund  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Refund> root, Refund
            refund, Date createdTimeStart, Date createdTimeEnd) {

        // neither of createdTime cannot be null
        if (refund.getId() == null && refund.getActive() == null &&
                refund.getRefundStatus() == null && createdTimeStart == null &&
                createdTimeEnd == null) {
            return null;
        }

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (refund.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(refund.getId()));
            return predicate;
        }

        if (refund.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(refund.getActive()));
        }

        // check Refund Status id
        if (refund.getRefundStatus() != null && refund.getRefundStatus().getId() != null) {
            Predicate refundStatusPredicate = builder.equal(
                    root.join("refundStatus").<Long>get("id"),
                    builder.literal(refund.getRefundStatus().getId()));
            if (predicate == null) {
                predicate = refundStatusPredicate;
            } else {
                predicate = builder.and(predicate, refundStatusPredicate);
            }
        }

        // check createdTime
        if (createdTimeStart != null && createdTimeEnd != null) {
            Predicate createdTimePredicate = builder.between(root.<Date>get("createdTime"),
                    builder.literal(createdTimeStart), builder.literal(createdTimeEnd));

            if (predicate == null) {
                predicate = createdTimePredicate;
            } else {
                predicate = builder.and(predicate, createdTimePredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order for CriteriaQuery.
     * Supports id, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<Refund> root,
                                   String order, ResourceUtil.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "createdTime":
                        orderBy = builder.asc(createdTimePath);
                        break;
                    default:
                        orderBy = builder.asc(idPath);
                }
                break;
            case DESC:
                switch (order) {
                    case "id":
                        orderBy = builder.desc(idPath);
                        break;
                    case "createdTime":
                        orderBy = builder.desc(createdTimePath);
                        break;
                    default:
                        orderBy = builder.desc(idPath);
                }
                break;
            default:
                orderBy = builder.desc(idPath);
                break;
        }

        logger.debug("Formulated order by clause is " + orderBy.toString());
        return orderBy;
    }
}
