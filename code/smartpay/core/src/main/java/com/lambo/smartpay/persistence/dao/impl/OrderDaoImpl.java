package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.OrderDao;
import com.lambo.smartpay.persistence.entity.Order;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/7/2015.
 */
@Repository("orderDao")
public class OrderDaoImpl extends GenericDaoImpl<Order, Long>
        implements OrderDao {

    private Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public Order findByMerchantNumber(String merchantNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);

        Root<Order> root = query.from(Order.class);
        query.select(root);

        Path<String> path = root.get("merchantNumber");
        Predicate predicate = builder.equal(path, merchantNumber);
        query.where(predicate);

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);

        logger.debug("findByName query is " + typedQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Count number of Order matching the search. Support ad hoc search on attributes of Order.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
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
     * Find all Order matching the search. Support ad hoc search on attributes of Order.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Order.
     */
    @Override
    public List<Order> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
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
        javax.persistence.criteria.Order orderBy = formulateOrderBy(builder, root, order, orderDir);
        query.orderBy(orderBy);

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Order by criteria.
     * Support attributes of Order.
     *
     * @param order            contains criteria if the field is not null or empty.
     * @param createdTimeStart when Order was created
     * @param createdTimeEnd   till when Order was created
     * @return number of the Order matching search
     */
    @Override
    public Long countByAdvanceSearch(Order order, Date createdTimeStart, Date createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, order, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param order            contains criteria if the field is not null or empty.
     * @param createdTimeStart when T was created
     * @param createdTimeEnd   till when T was created
     * @return List of the T matching search ordered by id without pagination.
     */
    @Override
    public List<Order> findByAdvanceSearch(Order order, Date createdTimeStart, Date
            createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, order, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param order            contains criteria if the field is not null or empty.
     * @param createdTimeStart
     * @param createdTimeEnd
     * @param start
     * @param length           @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Order> findByAdvanceSearch(Order order, Date createdTimeStart, Date createdTimeEnd,
                                           Integer start, Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, order, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Supports merchantNumber, goodsName, Site name, OrderStatus name, Currency name,
     * Customer email.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Order> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> merchantNumberPath = root.get("merchantNumber");
        Path<String> goodsNamePath = root.get("goodsName");
        // join on OrderStatus to search on OrderStatus name
        Path<String> sitePath = root.join("site").get("name");
        Path<String> orderStatusPath = root.join("orderStatus").get("name");
        Path<String> currencyPath = root.join("currency").get("name");
        Path<String> customerPath = root.join("customer").get("email");

        // create the predicate expression for all the path
        Predicate merchantNumberPredicate = builder.like(merchantNumberPath, likeSearch);
        Predicate goodsNamePredicate = builder.like(goodsNamePath, likeSearch);
        Predicate sitePredicate = builder.like(sitePath, likeSearch);
        Predicate orderStatusPredicate = builder.like(orderStatusPath, likeSearch);
        Predicate currencyPredicate = builder.like(currencyPath, likeSearch);
        Predicate customerPredicate = builder.like(customerPath, likeSearch);

        Predicate predicate = builder.or(merchantNumberPredicate, goodsNamePredicate,
                sitePredicate, orderStatusPredicate, currencyPredicate, customerPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Supports id, merchantNumber, goodsName, active, Site id, OrderStatus id, Currency id,
     * Customer id, createdTime between.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param order   is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Order> root, Order
            order, Date createdTimeStart, Date createdTimeEnd) {

        // neither of createdTime cannot be null
        if (order.getId() == null && StringUtils.isBlank(order.getMerchantNumber()) &&
                StringUtils.isBlank(order.getGoodsName()) && order.getActive() == null &&
                order.getSite() == null && order.getOrderStatus() == null &&
                order.getCurrency() == null && order.getCustomer() == null &&
                createdTimeStart == null && createdTimeEnd == null) {
            return null;
        }

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (order.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(order.getId()));
            return predicate;
        }

        // check firstName
        if (StringUtils.isNotBlank(order.getMerchantNumber())) {
            predicate = builder.like(root.<String>get("merchantNumber"),
                    builder.literal("%" + order.getMerchantNumber() + "%"));
        }
        // check lastName
        if (StringUtils.isNotBlank(order.getGoodsName())) {
            predicate = builder.like(root.<String>get("goodsName"),
                    builder.literal("%" + order.getGoodsName() + "%"));
        }

        if (order.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(order.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check Order Status id
        if (order.getOrderStatus() != null && order.getOrderStatus().getId() != null) {
            Predicate orderStatusPredicate = builder.equal(
                    root.join("orderStatus").<Long>get("id"),
                    builder.literal(order.getOrderStatus().getId()));
            if (predicate == null) {
                predicate = orderStatusPredicate;
            } else {
                predicate = builder.and(predicate, orderStatusPredicate);
            }
        }
        // check Site id
        if (order.getSite() != null && order.getSite().getId() != null) {
            Predicate sitePredicate = builder.equal(
                    root.join("site").<Long>get("id"),
                    builder.literal(order.getSite().getId()));
            if (predicate == null) {
                predicate = sitePredicate;
            } else {
                predicate = builder.and(predicate, sitePredicate);
            }
        }
        // check Currency id
        if (order.getCurrency() != null && order.getCurrency().getId() != null) {
            Predicate currencyPredicate = builder.equal(
                    root.join("currency").<Long>get("id"),
                    builder.literal(order.getCurrency().getId()));
            if (predicate == null) {
                predicate = currencyPredicate;
            } else {
                predicate = builder.and(predicate, currencyPredicate);
            }
        }
        // check Customer id
        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Predicate customerPredicate = builder.equal(
                    root.join("customer").<Long>get("id"),
                    builder.literal(order.getCustomer().getId()));
            if (predicate == null) {
                predicate = customerPredicate;
            } else {
                predicate = builder.and(predicate, customerPredicate);
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
    private javax.persistence.criteria.Order formulateOrderBy(CriteriaBuilder builder,
                                                              Root<Order> root, String order,
                                                              ResourceProperties.JpaOrderDir
                                                                      orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        javax.persistence.criteria.Order orderBy = null;
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
