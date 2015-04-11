package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.RefundDao;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    @Override
    public Refund findByBankTransactionNumber(String bankTransactionNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);

        Root<Refund> root = query.from(Refund.class);
        query.select(root);

        Path<String> path = root.get("bankTransactionNumber");
        Predicate predicate = builder.equal(path, bankTransactionNumber);
        query.where(predicate);

        TypedQuery<Refund> typedQuery = entityManager.createQuery(query);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Cannot find shipment with bank transaction number " +
                    bankTransactionNumber);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param refund     contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Refund refund, String search, Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
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
        logger.debug("countByCriteria query is "
                + typedQuery.unwrap(org.hibernate.Query.class).getQueryString());
        try {
            return super.countAllByCriteria(typedQuery);
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param refund     contains all criteria for equals, like name equals xx and active equals
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
    public List<Refund> findByCriteria(Refund refund, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir, Date rangeStart, Date
                                               rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
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

        TypedQuery<Refund> typedQuery = entityManager.createQuery(query);
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean isBlank(Refund refund) {
        return refund == null || refund.getId() == null && refund.getActive() == null
                && StringUtils.isBlank(refund.getBankAccountNumber())
                && refund.getCurrency() == null
                && refund.getRefundStatus() == null && refund.getOrder() == null;
    }

    private Predicate likePredicate(CriteriaBuilder builder, Root<Refund> root, String search) {
        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> bankTransactionNumberPath = root.get("bankTransactionNumber");

        // create the predicate expression for all the path
        Predicate bankTransactionNumberPredicate =
                builder.like(bankTransactionNumberPath, likeSearch);


        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + bankTransactionNumberPredicate.toString());
        return bankTransactionNumberPredicate;
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Root<Refund> root, Refund
            refund) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (refund.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(refund.getId()));
            return predicate;
        }

        // check firstName
        if (StringUtils.isNotBlank(refund.getBankTransactionNumber())) {
            predicate = builder.like(root.<String>get("bankTransactionNumber"),
                    builder.literal("%" + refund.getBankTransactionNumber() + "%"));
        }

        if (refund.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(refund.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check Order Status id
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
        // check order
        if (refund.getOrder() != null) {
            Order order = refund.getOrder();
            if (order.getId() != null) {
                Predicate orderIdPredicate = builder.equal(root.join("order").<Long>get("id"),
                        builder.literal(order.getId()));
                if (predicate == null) {
                    predicate = orderIdPredicate;
                } else {
                    predicate = builder.and(predicate, orderIdPredicate);
                }
            }

            // if order has site
            if (order.getSite() != null) {
                if (order.getSite().getId() != null) {
                    Predicate sitePredicate = builder.equal(
                            root.join("order").join("site").<Long>get("id"),
                            builder.literal(order.getSite().getId()));
                    if (predicate == null) {
                        predicate = sitePredicate;
                    } else {
                        predicate = builder.and(predicate, sitePredicate);
                    }
                }
                // if site has merchant
                if (order.getSite().getMerchant() != null) {
                    if (order.getSite().getMerchant().getId() != null) {
                        Predicate merchantIdPredicate = builder.equal(
                                root.join("order").join("site").join("merchant").<Long>get("id"),
                                builder.literal(order.getSite().getMerchant().getId()));
                        if (predicate == null) {
                            predicate = merchantIdPredicate;
                        } else {
                            predicate = builder.and(predicate, merchantIdPredicate);
                        }
                    }
                }
            }
        }
        // check Currency id
        if (refund.getCurrency() != null && refund.getCurrency().getId() != null) {
            Predicate currencyPredicate = builder.equal(
                    root.join("currency").<Long>get("id"),
                    builder.literal(refund.getCurrency().getId()));
            if (predicate == null) {
                predicate = currencyPredicate;
            } else {
                predicate = builder.and(predicate, currencyPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Refund> root,
                                     Date rangeStart, Date rangeEnd) {
        return builder.between(root.<Date>get("createdTime"),
                builder.literal(rangeStart), builder.literal(rangeEnd));
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, merchantNumber, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private javax.persistence.criteria.Order orderBy(CriteriaBuilder builder, Root<Refund> root,
                                                     String order, ResourceProperties.JpaOrderDir
                                                             orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> bankTransactionNumberPath = root.get("bankTransactionNumber");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        javax.persistence.criteria.Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "bankTransactionNumber":
                        orderBy = builder.asc(bankTransactionNumberPath);
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
                    case "bankTransactionNumber":
                        orderBy = builder.desc(bankTransactionNumberPath);
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
