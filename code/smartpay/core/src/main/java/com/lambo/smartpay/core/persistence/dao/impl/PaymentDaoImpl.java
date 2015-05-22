package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.PaymentDao;
import com.lambo.smartpay.core.persistence.entity.Payment;
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
 * Created by swang on 3/7/2015.
 */
@Repository("paymentDao")
public class PaymentDaoImpl extends GenericDaoImpl<Payment, Long>
        implements PaymentDao {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param payment    contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Payment payment, String search, Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Payment> root = query.from(Payment.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
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
     * @param payment    contains all criteria for equals, like name equals xx and active equals
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
    public List<Payment> findByCriteria(Payment payment, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir, Date rangeStart, Date
                                                rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
        Root<Payment> root = query.from(Payment.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
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

        TypedQuery<Payment> typedQuery = entityManager.createQuery(query);
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
    public Boolean isBlank(Payment payment) {
        return payment == null || payment.getId() == null && payment.getActive() == null &&
                payment.getPaymentStatus() == null && payment.getCurrency() == null
                && payment.getOrder() == null
                && StringUtils.isBlank(payment.getBankTransactionNumber());
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
    private Predicate likePredicate(CriteriaBuilder builder, Root<Payment> root, String
            search) {

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

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, PaymentStatus id, Currency id, active, site id, merchant id.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param payment is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Payment> root, Payment
            payment) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (payment.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(payment.getId()));
            return predicate;
        }

        if (payment.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(payment.getActive()));
        }

        // check bank transaction number
        if (!StringUtils.isBlank(payment.getBankTransactionNumber())) {
            Predicate bankTransactionNumberPredicate =
                    builder.like(root.<String>get("bankTransactionNumber"),
                            builder.literal(payment.getBankTransactionNumber()));
            if (predicate == null) {
                predicate = bankTransactionNumberPredicate;
            } else {
                predicate = builder.and(predicate, bankTransactionNumberPredicate);
            }
        }

        // check Payment Status id
        if (payment.getPaymentStatus() != null && payment.getPaymentStatus().getId() != null) {
            Predicate paymentStatusPredicate = builder.equal(
                    root.join("paymentStatus").<Long>get("id"),
                    builder.literal(payment.getPaymentStatus().getId()));
            if (predicate == null) {
                predicate = paymentStatusPredicate;
            } else {
                predicate = builder.and(predicate, paymentStatusPredicate);
            }
        }
        // check Payment Login id
        if (payment.getCurrency() != null && payment.getCurrency().getId() != null) {
            Predicate currencyPredicate = builder.equal(
                    root.join("currency").<Long>get("id"),
                    builder.literal(payment.getCurrency().getId()));
            if (predicate == null) {
                predicate = currencyPredicate;
            } else {
                predicate = builder.and(predicate, currencyPredicate);
            }
        }

        // based on site
        if (payment.getOrder() != null) {
            // if payment has site
            if (payment.getOrder().getSite() != null) {
                // site id
                if (payment.getOrder().getSite().getId() != null) {
                    Predicate sitePredicate = builder.equal(root.join("order")
                                    .join("site").<Long>get("id"),
                            builder.literal(payment.getOrder().getSite().getId()));
                    if (predicate == null) {
                        predicate = sitePredicate;
                    } else {
                        predicate = builder.and(predicate, sitePredicate);
                    }
                }

                // if has merchant id
                if (payment.getOrder().getSite().getMerchant() != null) {
                    if (payment.getOrder().getSite().getMerchant().getId() != null) {
                        Predicate merchantPredicate = builder.equal(root.join("order")
                                        .join("site").join("merchant").<Long>get("id"),
                                builder.literal(
                                        payment.getOrder().getSite().getMerchant().getId()));
                        if (predicate == null) {
                            predicate = merchantPredicate;
                        } else {
                            predicate = builder.and(predicate, merchantPredicate);
                        }
                    }
                }
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Payment> root,
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
    private Order orderBy(CriteriaBuilder builder, Root<Payment> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<Date> createdTimePath = root.get("createdTime");
        Path<Date> successTimePath = root.get("successTime");

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
                    case "successTime":
                        orderBy = builder.asc(successTimePath);
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
                    case "successTime":
                        orderBy = builder.desc(successTimePath);
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
