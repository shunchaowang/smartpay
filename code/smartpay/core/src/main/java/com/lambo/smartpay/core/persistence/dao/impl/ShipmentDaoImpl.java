package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.ShipmentDao;
import com.lambo.smartpay.core.persistence.entity.Shipment;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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

    @Override
    public Shipment findByTrackingNumber(String trackingNumber) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> query = builder.createQuery(Shipment.class);

        Root<Shipment> root = query.from(Shipment.class);
        query.select(root);

        Path<String> path = root.get("trackingNumber");
        Predicate predicate = builder.equal(path, trackingNumber);
        query.where(predicate);

        TypedQuery<Shipment> typedQuery = entityManager.createQuery(query);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Cannot find shipment with tracking number " + trackingNumber);
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
     * @param shipment contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Shipment shipment, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Shipment> root = query.from(Shipment.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(shipment)) {
            predicate = equalPredicate(builder, root, shipment);
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
     * @param shipment contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field.
     * @param orderDir order direction on the order field.
     * @return
     */
    @Override
    public List<Shipment> findByCriteria(Shipment shipment, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> query = builder.createQuery(Shipment.class);
        Root<Shipment> root = query.from(Shipment.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(shipment)) {
            predicate = equalPredicate(builder, root, shipment);
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
        TypedQuery<Shipment> typedQuery = entityManager.createQuery(query);
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
    public Boolean isBlank(Shipment shipment) {
        return shipment == null || shipment.getId() == null && shipment.getActive() == null
                && StringUtils.isBlank(shipment.getTrackingNumber())
                && shipment.getShipmentStatus() == null && shipment.getOrder() == null;
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Root<Shipment> root,
                                     Shipment shipment) {
        Predicate predicate = null;

        // check id, if id != null, query by id and return
        if (shipment.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(shipment.getId()));
            return predicate;
        }
        // check tracking number
        if (StringUtils.isNotBlank(shipment.getTrackingNumber())) {
            predicate = builder.like(root.<String>get("trackingNumber"),
                    builder.literal("%" + shipment.getTrackingNumber() + "%"));
        }

        if (shipment.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(shipment.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check shipment status id
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
        // check shipment order id
        if (shipment.getOrder() != null) {
            if (shipment.getOrder().getId() != null) {
                Predicate orderPredicate = builder.equal(
                        root.join("order").<Long>get("id"),
                        builder.literal(shipment.getOrder().getId()));
                if (predicate == null) {
                    predicate = orderPredicate;
                } else {
                    predicate = builder.and(predicate, orderPredicate);
                }
            }

            // check for site
            if (shipment.getOrder().getSite() != null) {
                if (shipment.getOrder().getSite().getId() != null) {
                    Predicate sitePredicate = builder.equal(
                            root.join("order").join("site").<Long>get("id"),
                            builder.literal(shipment.getOrder().getSite().getId()));
                    if (predicate == null) {
                        predicate = sitePredicate;
                    } else {
                        predicate = builder.and(predicate, sitePredicate);
                    }
                }

                // check for merchant
                if (shipment.getOrder().getSite().getMerchant() != null) {
                    if (shipment.getOrder().getSite().getMerchant().getId() != null) {
                        Predicate merchantPredicate = builder.equal(
                                root.join("order").join("site").join("merchant").<Long>get("id"),
                                builder.literal(
                                        shipment.getOrder().getSite().getMerchant().getId()));
                        if (predicate == null) {
                            predicate = merchantPredicate;
                        } else {
                            predicate = builder.and(predicate, merchantPredicate);
                        }
                    }
                }
            }
        }

        return predicate;
    }

    private Predicate likePredicate(CriteriaBuilder builder, Root<Shipment> root, String search) {
        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> carrierPath = root.get("carrier");
        Path<String> trackingNumberPath = root.get("trackingNumber");

        // create the predicate expression for all the path
        Predicate carrierPredicate = builder.like(carrierPath, likeSearch);
        Predicate trackingNumberPredicate = builder.like(trackingNumberPath, likeSearch);

        Predicate predicate = builder.or(carrierPredicate, trackingNumberPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    private Order orderBy(CriteriaBuilder builder, Root<Shipment> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> carrierPath = root.get("carrier");
        Path<String> trackingNumberPath = root.get("trackingNumber");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "carrier":
                        orderBy = builder.asc(carrierPath);
                        break;
                    case "trackingNumber":
                        orderBy = builder.asc(trackingNumberPath);
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
                    case "carrier":
                        orderBy = builder.desc(carrierPath);
                        break;
                    case "trackingNumber":
                        orderBy = builder.desc(trackingNumberPath);
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
