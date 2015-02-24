package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.MerchantDao;
import com.lambo.smartpay.model.Merchant;
import com.lambo.smartpay.util.ResourceUtil;
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
 * Created by swang on 2/19/2015.
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends GenericDaoImpl<Merchant, Long> implements MerchantDao {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantDaoImpl.class);

    /**
     * Count number of Merchant matching the search. Support ad hoc search on name, contact, tel, email and name
     * of MerchantStatus.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Merchant> root = query.from(Merchant.class);
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
        LOG.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find all Merchant matching the search. Support ad hoc search on name, contact, tel, email and name
     * of MerchantStatus.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Merchant.
     */
    @Override
    public List<Merchant> findByAdHocSearch(String search, Integer start, Integer length,
                                            String order, ResourceUtil.JpaOrderDir orderDir,
                                            Boolean activeFlag) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Merchant> query = builder.createQuery(Merchant.class);
        Root<Merchant> root = query.from(Merchant.class);
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

        TypedQuery<Merchant> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        LOG.debug("findByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Merchant by criteria, created time range.
     * Support id, name, MerchantStatus code and createdTime range.
     *
     * @param merchant         contains criteria if the field is not null or empty.
     * @param createdTimeBegin is the beginning of the created time range if not null.
     * @param createdTimeEnd   is the ending of the created time range if not null.
     * @return number of the Merchant matching search.
     */
    @Override
    public Long countByAdvanceSearch(Merchant merchant, Date createdTimeBegin, Date createdTimeEnd) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Merchant> root = query.from(Merchant.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, merchant, createdTimeBegin, createdTimeEnd);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        LOG.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Merchant by criteria, created time range.
     * Support id, name, active, MerchantStatus code and createdTime range.
     *
     * @param merchant         contains criteria if the field is not null or empty.
     * @param createdTimeBegin is the beginning of the created time range if not null.
     * @param createdTimeEnd   is the ending of the created time range if not null.
     * @return List of the Merchant matching search ordered by id without pagination.
     */
    @Override
    public List<Merchant> findByAdvanceSearch(Merchant merchant, Date createdTimeBegin, Date createdTimeEnd) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Merchant> query = builder.createQuery(Merchant.class);
        Root<Merchant> root = query.from(Merchant.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, merchant, createdTimeBegin, createdTimeEnd);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Merchant> typedQuery = entityManager.createQuery(query);
        LOG.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Supports name, contact, tel, email and name of MerchantStatus.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Merchant> root, String search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> namePath = root.get("name");
        Path<String> contactPath = root.get("contact");
        Path<String> telPath = root.get("tel");
        Path<String> emailPath = root.get("email");
        // join on MerchantStatus to search on MerchantStatus name
        Path<String> merchantStatusPath = root.join("merchantStatus").get("name");

        // create the predicate expression for all the path
        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate contactPredicate = builder.like(contactPath, likeSearch);
        Predicate telPredicate = builder.like(telPath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);
        Predicate merchantStatusPredicate = builder.like(merchantStatusPath, likeSearch);

        Predicate predicate = builder.or(namePredicate, contactPredicate, telPredicate,
                emailPredicate, merchantStatusPredicate);

        // we don't want to have wildcard search on id actually
        // create id predicate expression of search is numeric
//        if (StringUtils.isNumeric(search)) {
//            Path<Long> idPath = root.get("id");
//            Predicate idPredicate = builder.equal(idPath, builder.literal(Long.valueOf(search)));
//            predicate = builder.or(predicate, idPredicate);
//        }

        // create the final Predicate and return
        LOG.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate for CriteriaQuery.
     * Support id, name, active, MerchantStatus code and createdTime range.
     *
     * @param builder          is the JPA CriteriaBuilder.
     * @param root             is the root of the CriteriaQuery.
     * @param merchant         is the search keyword.
     * @param createdTimeBegin not null, beginning of time range.
     * @param createdTimeEnd   not null, ending of time range.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Merchant> root, Merchant merchant,
                                         Date createdTimeBegin, Date createdTimeEnd) {

        // neither of createdTime cannot be null
        if (merchant.getId() == null && StringUtils.isBlank(merchant.getName()) &&
                merchant.getMerchantStatus() == null || createdTimeBegin == null || createdTimeEnd == null) {
            return null;
        }

        Predicate predicate = builder.between(root.<Date>get("createdTime"),
                builder.literal(createdTimeBegin), builder.literal(createdTimeEnd));

        // check id
        if (merchant.getId() != null) {
            predicate = builder.and(predicate, builder.equal(root.<Long>get("id"), builder.literal(merchant.getId())));
        }

        // check name
        if (StringUtils.isNotBlank(merchant.getName())) {
            predicate = builder.and(predicate, builder.like(root.<String>get("name"),
                    builder.literal("%" + merchant.getName() + "%")));
        }

        if (merchant.getActive() != null) {
            predicate = builder.and(predicate,
                    builder.equal(root.get("active"), builder.literal(merchant.getActive())));
        }

        // check Merchant Status code
        if (merchant.getMerchantStatus() != null &&
                StringUtils.isNotBlank(merchant.getMerchantStatus().getCode())) {
            predicate = builder.and(predicate, builder.like(root.join("merchantStatus").<String>get("code"),
                    builder.literal("%" + merchant.getMerchantStatus().getCode() + "%")));
        }

        // check createdTime range

        LOG.debug("Formulated predicate is " + predicate.toString());
        return null;
    }

    /**
     * Formulate JPA Order for CriteriaQuery.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<Merchant> root,
                                   String order, ResourceUtil.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> contactPath = root.get("contact");
        Path<String> telPath = root.get("tel");
        Path<String> emailPath = root.get("email");
        Path<String> merchantStatusPath = root.join("merchantStatus").get("name");
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
                    case "contact":
                        orderBy = builder.asc(contactPath);
                        break;
                    case "tel":
                        orderBy = builder.asc(telPath);
                        break;
                    case "email":
                        orderBy = builder.asc(emailPath);
                        break;
                    case "merchantStatus":
                        orderBy = builder.asc(merchantStatusPath);
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
                    case "contact":
                        orderBy = builder.desc(contactPath);
                        break;
                    case "tel":
                        orderBy = builder.desc(telPath);
                        break;
                    case "email":
                        orderBy = builder.desc(emailPath);
                        break;
                    case "merchantStatus":
                        orderBy = builder.desc(merchantStatusPath);
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

        LOG.debug("Formulated order by clause is " + orderBy.toString());
        return orderBy;
    }
}
