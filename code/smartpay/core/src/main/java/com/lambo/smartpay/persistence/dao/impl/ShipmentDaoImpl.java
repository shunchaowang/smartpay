package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.ShipmentDao;
import com.lambo.smartpay.persistence.entity.Shipment;
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
 * Created by swang on 3/9/2015.
 */
@Repository("shipmentDao")
public class ShipmentDaoImpl extends GenericDaoImpl<Shipment, Long>
        implements ShipmentDao {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentDaoImpl.class);

    /**
     * Count number of Shipment matching the search. Support ad hoc search on attributes of
     * Shipment.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Shipment> root = query.from(Shipment.class);
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
     * Find all Shipment matching the search. Support ad hoc search on attributes of Shipment.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    @Override
    public List<Shipment> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> query = builder.createQuery(Shipment.class);
        Root<Shipment> root = query.from(Shipment.class);
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

        TypedQuery<Shipment> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Shipment by criteria.
     * Support attributes of Shipment.
     *
     * @param shipment         contains criteria if the field is not null or empty.
     * @param createdTimeStart when Shipment was created
     * @param createdTimeEnd   till when Shipment was created
     * @return number of the Shipment matching search
     */
    @Override
    public Long countByAdvanceSearch(Shipment shipment, Date createdTimeStart, Date
            createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Shipment> root = query.from(Shipment.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, shipment, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Shipment  by criteria.
     * Support attributes of Shipment .
     *
     * @param shipment         contains criteria if the field is not null or empty.
     * @param createdTimeStart when Shipment was created
     * @param createdTimeEnd   till when Shipment was created
     * @return List of the Shipment matching search ordered by id without pagination.
     */
    @Override
    public List<Shipment> findByAdvanceSearch(Shipment shipment, Date createdTimeStart, Date
            createdTimeEnd) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> query = builder.createQuery(Shipment.class);
        Root<Shipment> root = query.from(Shipment.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, shipment, createdTimeStart,
                createdTimeEnd);

        query.where(predicate);

// default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Shipment> typedQuery = entityManager.createQuery(query);
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
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Shipment> root, String
            search) {
        //TODO Add more search criteria
        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> trackingNumberPath = root.get("trackingNumber");

        // create the predicate expression for all the path
        Predicate trackingNumberPredicate = builder.like(trackingNumberPath, likeSearch);

        Predicate predicate = builder.or(trackingNumberPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Supports id, ShipmentStatus id, active, createdTime between.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param shipment is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Shipment> root, Shipment
            shipment, Date createdTimeStart, Date createdTimeEnd) {

        // neither of createdTime cannot be null
        if (shipment.getId() == null && shipment.getActive() == null &&
                shipment.getShipmentStatus() == null && createdTimeStart == null &&
                createdTimeEnd == null) {
            return null;
        }

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (shipment.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(shipment.getId()));
            return predicate;
        }

        if (shipment.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(shipment.getActive()));
        }

        // check Shipment Status id
        if (shipment.getShipmentStatus() != null && shipment.getShipmentStatus().getId() != null) {
            Predicate shipmentStatusPredicate = builder.equal(
                    root.join("shipmentStatus").<Long>get("id"),
                    builder.literal(shipment.getShipmentStatus().getId()));
            if (predicate == null) {
                predicate = shipmentStatusPredicate;
            } else {
                predicate = builder.and(predicate, shipmentStatusPredicate);
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
    private Order formulateOrderBy(CriteriaBuilder builder, Root<Shipment> root,
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