package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.WithdrawalDao;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
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
@Repository("withdrawalDao")
public class WithdrawalDaoImpl extends GenericDaoImpl<Withdrawal, Long>
        implements WithdrawalDao {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalDaoImpl.class);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param withdrawal contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Withdrawal withdrawal, String search, Date rangeStart,
                                Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Withdrawal> root = query.from(Withdrawal.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(withdrawal)) {
            predicate = equalPredicate(builder, root, withdrawal);
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
     * @param withdrawal contains all criteria for equals, like name equals xx and active equals
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
    public List<Withdrawal> findByCriteria(Withdrawal withdrawal, String search, Integer start,
                                           Integer
                                                   length, String order,
                                           ResourceProperties.JpaOrderDir orderDir, Date rangeStart,
                                           Date
                                                   rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Withdrawal> query = builder.createQuery(Withdrawal.class);
        Root<Withdrawal> root = query.from(Withdrawal.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(withdrawal)) {
            predicate = equalPredicate(builder, root, withdrawal);
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

        TypedQuery<Withdrawal> typedQuery = entityManager.createQuery(query);
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
    public Boolean isBlank(Withdrawal withdrawal) {
        return withdrawal == null || withdrawal.getId() == null && withdrawal.getActive() == null &&
                withdrawal.getWithdrawalStatus() == null && withdrawal.getAmount() == null
                && withdrawal.getBalance() == null && withdrawal.getDateStart() == null
                && withdrawal.getDateEnd() == null && withdrawal.getMerchant() == null;
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
    private Predicate likePredicate(CriteriaBuilder builder, Root<Withdrawal> root, String
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
     * Supports id, WithdrawalStatus id, Currency id, active, site id, merchant id.
     *
     * @param builder    is the JPA CriteriaBuilder.
     * @param root       is the root of the CriteriaQuery.
     * @param withdrawal is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Withdrawal> root, Withdrawal
            withdrawal) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (withdrawal.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(withdrawal.getId()));
            return predicate;
        }

        if (withdrawal.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(withdrawal.getActive()));
        }

        // check Withdrawal Status id
        if (withdrawal.getWithdrawalStatus() != null &&
                withdrawal.getWithdrawalStatus().getId() != null) {
            Predicate withdrawalStatusPredicate = builder.equal(
                    root.join("withdrawalStatus").<Long>get("id"),
                    builder.literal(withdrawal.getWithdrawalStatus().getId()));
            if (predicate == null) {
                predicate = withdrawalStatusPredicate;
            } else {
                predicate = builder.and(predicate, withdrawalStatusPredicate);
            }
        }
        // check merchant id
        if (withdrawal.getMerchant() != null && withdrawal.getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(
                    root.join("merchant").<Long>get("id"),
                    builder.literal(withdrawal.getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Withdrawal> root,
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
    private Order orderBy(CriteriaBuilder builder, Root<Withdrawal> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<Date> createdTimePath = root.get("createdTime");
        Path<Date> updatedTimePath = root.get("updatedTime");
        Path<Date> dateStartPath = root.get("dateStart");
        Path<Date> dateEndPath = root.get("dateEnd");

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
                    case "updatedTime":
                        orderBy = builder.asc(updatedTimePath);
                        break;
                    case "dateStart":
                        orderBy = builder.asc(dateStartPath);
                        break;
                    case "dateEnd":
                        orderBy = builder.asc(dateEndPath);
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
                    case "updatedTime":
                        orderBy = builder.desc(updatedTimePath);
                        break;
                    case "dateStart":
                        orderBy = builder.asc(dateStartPath);
                        break;
                    case "dateEnd":
                        orderBy = builder.asc(dateEndPath);
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
