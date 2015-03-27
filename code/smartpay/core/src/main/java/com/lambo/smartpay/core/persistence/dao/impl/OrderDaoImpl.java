package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.OrderDao;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.util.ResourceProperties;
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
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
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
     * @param order      contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Order order, String search, Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(order)) {
            predicate = equalPredicate(builder, root, order);
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
     * @param order      order by field.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param start      first position of the result.
     * @param length     max record of the result.
     * @param orderField order by field.
     * @param orderDir   order direction on the order field.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return
     */
    @Override
    public List<Order> findByCriteria(Order order, String search, Integer start, Integer length,
                                      String orderField, ResourceProperties.JpaOrderDir orderDir,
                                      Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(order)) {
            predicate = equalPredicate(builder, root, order);
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
        if (StringUtils.isBlank(orderField)) {
            orderField = "id";
        }
        if (orderDir == null) {
            orderDir = ResourceProperties.JpaOrderDir.DESC;
        }
        query.orderBy(orderBy(builder, root, orderField, orderDir));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
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
    public Boolean isBlank(Order order) {
        if (order == null) {
            return true;
        }
        if (order.getId() == null && StringUtils.isBlank(order.getMerchantNumber())
                && order.getOrderStatus() == null && order.getActive() == null
                && order.getCurrency() == null && order.getCustomer() == null) {
            return true;
        }
        return false;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports merchantNumber.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Order> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> merchantNumberPath = root.get("merchantNumber");

        // create the predicate expression for all the path
        Predicate merchantNumberPredicate = builder.like(merchantNumberPath, likeSearch);


        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + merchantNumberPredicate.toString());
        return merchantNumberPredicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, merchantNumber, OrderStatus id, Site id, Currency id, Customer id, active.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param order   is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Order> root, Order
            order) {

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
            Predicate orderLoginPredicate = builder.equal(
                    root.join("site").<Long>get("id"),
                    builder.literal(order.getSite().getId()));
            if (predicate == null) {
                predicate = orderLoginPredicate;
            } else {
                predicate = builder.and(predicate, orderLoginPredicate);
            }
        }
        // check Currency id
        if (order.getCurrency() != null && order.getCurrency().getId() != null) {
            Predicate currencyPredicate = builder.equal(
                    root.join("currency").<Long>get("id"),
                    builder.literal(order.getSite().getId()));
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
                    builder.literal(order.getSite().getId()));
            if (predicate == null) {
                predicate = customerPredicate;
            } else {
                predicate = builder.and(predicate, customerPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Order> root,
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
    private javax.persistence.criteria.Order orderBy(CriteriaBuilder builder, Root<Order> root,
                                                     String order, ResourceProperties.JpaOrderDir
            orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> merchantNumberPath = root.get("merchantNumber");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        javax.persistence.criteria.Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "merchantNumber":
                        orderBy = builder.asc(merchantNumberPath);
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
                    case "merchantNumber":
                        orderBy = builder.desc(merchantNumberPath);
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
