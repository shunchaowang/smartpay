package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.ClaimDao;
import com.lambo.smartpay.core.persistence.entity.Claim;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
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
 * Created by swang on 4/17/2015.
 */
@Repository("claimDao")
public class ClaimDaoImpl extends GenericDaoImpl<Claim, Long>
        implements ClaimDao {

    private static final Logger logger = LoggerFactory.getLogger(ClaimDaoImpl.class);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param claim      contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Claim claim, String search, Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Claim> root = query.from(Claim.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(claim)) {
            predicate = equalPredicate(builder, root, claim);
        }

        // if search is not blank like predicate needs to be generated
        if (!StringUtils.isBlank(search)) {
            Predicate likePredicate = likePredicate(builder, root, search);
            if (predicate == null) {
                predicate = likePredicate;
            } else {
                predicate = builder.and(predicate, likePredicate);
            }
        }

        if (rangeStart != null && rangeEnd != null) {
            Predicate rangePredicate = rangePredicate(builder, root,
                    rangeStart, rangeEnd);
            if (predicate == null) {
                predicate = rangePredicate;
            } else {
                predicate = builder.and(predicate, rangePredicate);
            }
        }

        if (predicate != null) {
            query.where(predicate);
        }
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByCriteria query is " + typedQuery);
        try {
            return super.countAllByCriteria(typedQuery);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param claim      contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param start      first position of the result.
     * @param length     max record of the result.
     * @param order      order by field.
     * @param orderDir   order direction on the order field.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return
     */
    @Override
    public List<Claim> findByCriteria(Claim claim, String search, Integer start, Integer length,
                                      String order, ResourceProperties.JpaOrderDir orderDir,
                                      Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Claim> query = builder.createQuery(Claim.class);
        Root<Claim> root = query.from(Claim.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(claim)) {
            predicate = equalPredicate(builder, root, claim);
        }

        // if search is not blank like predicate needs to be generated
        if (!StringUtils.isBlank(search)) {
            Predicate likePredicate = likePredicate(builder, root, search);
            if (predicate == null) {
                predicate = likePredicate;
            } else {
                predicate = builder.and(predicate, likePredicate);
            }
        }

        if (rangeStart != null && rangeEnd != null) {
            Predicate rangePredicate = rangePredicate(builder, root,
                    rangeStart, rangeEnd);
            if (predicate == null) {
                predicate = rangePredicate;
            } else {
                predicate = builder.and(predicate, rangePredicate);
            }
        }

        if (predicate != null) {
            query.where(predicate);
        }
        if (StringUtils.isBlank(order)) {
            order = "id";
        }
        if (orderDir == null) {
            orderDir = ResourceProperties.JpaOrderDir.DESC;
        }
        query.orderBy(orderBy(builder, root, order, orderDir));

        TypedQuery<Claim> typedQuery = entityManager.createQuery(query);
        // pagination
        if (start != null) {
            typedQuery.setFirstResult(start);
        }
        if (length != null) {
            typedQuery.setMaxResults(length);
        }

        logger.debug("findByCriteria query is " + typedQuery);
        try {
            return super.findAllByCriteria(typedQuery);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean isBlank(Claim claim) {
        return claim == null || claim.getId() == null && claim.getActive() == null
                && StringUtils.isBlank(claim.getRemark())
                && claim.getPayment() == null;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports bankName.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Claim> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> remarkPath = root.get("remark");

        // create the predicate expression for all the path
        Predicate remarkPredicate =
                builder.like(remarkPath, likeSearch);


        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + remarkPredicate.toString());
        return remarkPredicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, ClaimStatus id, Currency id, active, site id, merchant id.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param claim   is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Claim> root, Claim
            claim) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (claim.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(claim.getId()));
            return predicate;
        }

        if (claim.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(claim.getActive()));
        }

        // check Claim remark id
        if (!StringUtils.isBlank(claim.getRemark())) {
            Predicate remarkPredicate = builder.like(
                    root.<String>get("remark"),
                    "%" + builder.literal(claim.getRemark()) + "%");
            if (predicate == null) {
                predicate = remarkPredicate;
            } else {
                predicate = builder.and(predicate, remarkPredicate);
            }
        }

        // check payment
        if (claim.getPayment() != null && claim.getPayment().getId() != null) {
            Predicate paymentPredicate = builder.equal(root.join("payment").<Long>get("id"),
                    builder.literal(claim.getPayment().getId()));
            if (predicate == null) {
                predicate = paymentPredicate;
            } else {
                predicate = builder.and(predicate, paymentPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Claim> root,
                                     Date rangeStart, Date rangeEnd) {
        return builder.between(root.<Date>get("createdTime"),
                builder.literal(rangeStart), builder.literal(rangeEnd));
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<Claim> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

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
