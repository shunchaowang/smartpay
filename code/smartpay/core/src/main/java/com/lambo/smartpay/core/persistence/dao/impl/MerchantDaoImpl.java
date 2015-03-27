package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.MerchantDao;
import com.lambo.smartpay.core.persistence.entity.Merchant;
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
 * Dao Impl for Merchant.
 * Created by swang on 2/19/2015.
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends GenericDaoImpl<Merchant, Long> implements MerchantDao {

    private static final Logger logger = LoggerFactory.getLogger(MerchantDaoImpl.class);

    /**
     * Find merchant by name.
     *
     * @param name name of the merchant.
     * @return return the object if found, null if not found.
     */
    @Override
    public Merchant findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Merchant> query = builder.createQuery(Merchant.class);

        Root<Merchant> root = query.from(Merchant.class);
        query.select(root);

        Path<String> path = root.get("name");
        Predicate predicate = builder.equal(path, name);
        query.where(predicate);

        TypedQuery<Merchant> typedQuery = entityManager.createQuery(query);

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
     * @param merchant contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Merchant merchant, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Merchant> root = query.from(Merchant.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(merchant)) {
            predicate = equalPredicate(builder, root, merchant);
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
     * @param merchant contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Merchant> findByCriteria(Merchant merchant, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Merchant> query = builder.createQuery(Merchant.class);
        Root<Merchant> root = query.from(Merchant.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(merchant)) {
            predicate = equalPredicate(builder, root, merchant);
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
        TypedQuery<Merchant> typedQuery = entityManager.createQuery(query);
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

    /**
     * Test if T is blank for the query.
     *
     * @param merchant null return false, all required fields are null return false.
     * @return
     */
    @Override
    public Boolean isBlank(Merchant merchant) {
        if (merchant == null) {
            return true;
        }
        if (merchant.getId() == null && merchant.getName() == null
                && merchant.getEmail() == null && merchant.getMerchantStatus() == null) {
            return true;
        }
        return false;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports name, email.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Merchant> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> namePath = root.get("name");
        Path<String> emailPath = root.get("email");

        // create the predicate expression for all the path
        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);

        Predicate predicate = builder.or(namePredicate, emailPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, name, email, MerchantStatus id, active.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param merchant is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Merchant> root, Merchant
            merchant) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (merchant.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(merchant.getId()));
            return predicate;
        }

        // check firstName
        if (StringUtils.isNotBlank(merchant.getName())) {
            predicate = builder.like(root.<String>get("name"),
                    builder.literal("%" + merchant.getName() + "%"));
        }
        // check email
        if (StringUtils.isNotBlank(merchant.getEmail())) {
            predicate = builder.like(root.<String>get("email"),
                    builder.literal("%" + merchant.getEmail() + "%"));
        }

        if (merchant.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(merchant.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check Merchant Status id
        if (merchant.getMerchantStatus() != null && merchant.getMerchantStatus().getId() != null) {
            Predicate merchantStatusPredicate = builder.equal(
                    root.join("merchantStatus").<Long>get("id"),
                    builder.literal(merchant.getMerchantStatus().getId()));
            if (predicate == null) {
                predicate = merchantStatusPredicate;
            } else {
                predicate = builder.and(predicate, merchantStatusPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, name, email, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<Merchant> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> emailPath = root.get("email");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "name":
                        orderBy = builder.asc(namePath);
                        break;
                    case "email":
                        orderBy = builder.asc(emailPath);
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
                    case "name":
                        orderBy = builder.desc(namePath);
                        break;
                    case "email":
                        orderBy = builder.desc(emailPath);
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
