package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.SiteDao;
import com.lambo.smartpay.persistence.entity.Site;
import com.lambo.smartpay.util.ResourceProperties;
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
 * Created by swang on 3/6/2015.
 */
@Repository("siteDao")
public class SiteDaoImpl extends GenericDaoImpl<Site, Long> implements SiteDao {

    private static final Logger logger = LoggerFactory.getLogger(SiteDaoImpl.class);

    /**
     * Find site by name.
     *
     * @param name name of the site to find.
     * @return site with the name specific of null if not found.
     */
    @Override
    public Site findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);

        Root<Site> root = query.from(Site.class);
        query.select(root);

        Path<String> path = root.get("name");
        Predicate predicate = builder.equal(path, name);
        query.where(predicate);

        TypedQuery<Site> typedQuery = entityManager.createQuery(query);

        logger.debug("findByName query is " + typedQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Cannot find site with name " + name);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Count number of Site matching the search. Support ad hoc search on attributes of Site.
     * Supports name, url, name of SiteStatus, name of Merchant.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Site> root = query.from(Site.class);
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
     * Find all Site matching the search. Support ad hoc search on attributes of Site.
     * Supports name, url, name of SiteStatus, name of Merchant.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Site.
     */
    @Override
    public List<Site> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
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

        TypedQuery<Site> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Site by criteria. Support attributes of Site.
     * Supports id, name, url, id of SiteStatus, id of Merchant.
     *
     * @param site contains criteria if the field is not null or empty.
     * @return number of the Site matching search.
     */
    @Override
    public Long countByAdvanceSearch(Site site) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Site> root = query.from(Site.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, site);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Site by criteria.Support attributes of Site.
     * Supports id, name, url, id of SiteStatus, id of Merchant.
     *
     * @param site contains criteria if the field is not null or empty.
     * @return List of the Site matching search ordered by id without pagination.
     */
    @Override
    public List<Site> findByAdvanceSearch(Site site) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, site);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Site> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param site   contains criteria if the field is not null or empty.
     * @param start
     * @param length @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Site> findByAdvanceSearch(Site site, Integer start, Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, site);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Site> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Supports name, url, name of SiteStatus, name of Merchant.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Site> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> namePath = root.get("name");
        Path<String> urlPath = root.get("url");
        // join on SiteStatus to search on SiteStatus name
        Path<String> siteStatusPath = root.join("siteStatus").get("name");
        Path<String> merchantNamePath = root.join("merchant").get("name");

        // create the predicate expression for all the path
        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate urlPredicate = builder.like(urlPath, likeSearch);
        Predicate siteStatusPredicate = builder.like(siteStatusPath, likeSearch);
        Predicate merchantNamePredicate = builder.like(merchantNamePath, likeSearch);

        Predicate predicate = builder.or(namePredicate, urlPredicate, siteStatusPredicate,
                merchantNamePredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Support id, name, url, active, SiteStatus id, Merchant id.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param site    is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Site> root, Site
            site) {

        // neither of createdTime cannot be null
        if (site.getId() == null && StringUtils.isBlank(site.getName()) &&
                site.getActive() == null && StringUtils.isBlank(site.getUrl()) &&
                site.getSiteStatus() == null && site.getMerchant() == null) {
            return null;
        }

//        Predicate predicate = builder.between(root.<Date>get("createdTime"),
//                builder.literal(createdTimeBegin), builder.literal(createdTimeEnd));

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (site.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(site.getId()));
            return predicate;
        }

        // check name
        if (StringUtils.isNotBlank(site.getName())) {
            predicate = builder.like(root.<String>get("name"),
                    builder.literal("%" + site.getName() + "%"));
        }
        // check url
        if (StringUtils.isNotBlank(site.getUrl())) {
            predicate = builder.like(root.<String>get("url"),
                    builder.literal("%" + site.getUrl() + "%"));
        }

        if (site.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(site.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check Site Status id
        if (site.getSiteStatus() != null && site.getSiteStatus().getId() != null) {
            Predicate siteStatusPredicate = builder.equal(
                    root.join("siteStatus").<Long>get("id"),
                    builder.literal(site.getSiteStatus().getId()));
            if (predicate == null) {
                predicate = siteStatusPredicate;
            } else {
                predicate = builder.and(predicate, siteStatusPredicate);
            }
        }
        // check Merchant id
        if (site.getMerchant() != null && site.getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(
                    root.join("merchant").<Long>get("id"),
                    builder.literal(site.getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order for CriteriaQuery.
     * Supports id, name, url, SiteStatus name, Merchant name, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */

    private Order formulateOrderBy(CriteriaBuilder builder, Root<Site> root,
                                   String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> urlPath = root.get("url");
        Path<String> siteStatusPath = root.join("siteStatus").get("name");
        Path<String> merchantPath = root.join("merchant").get("name");
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
                    case "url":
                        orderBy = builder.asc(urlPath);
                        break;
                    case "siteStatus":
                        orderBy = builder.asc(siteStatusPath);
                        break;
                    case "merchant":
                        orderBy = builder.asc(merchantPath);
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
                    case "url":
                        orderBy = builder.desc(urlPath);
                        break;
                    case "siteStatus":
                        orderBy = builder.desc(siteStatusPath);
                        break;
                    case "merchant":
                        orderBy = builder.desc(merchantPath);
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

    //TODO NEWLY ADDED HERE


    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param site   contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Site site, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Site> root = query.from(Site.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(site)) {
            predicate = equalPredicate(builder, root, site);
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
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param site     contains all criteria for equals, like name equals xx and active equals
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
    public List<Site> findByCriteria(Site site, String search, Integer start, Integer length,
                                     String order, ResourceProperties.JpaOrderDir orderDir) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(site)) {
            predicate = equalPredicate(builder, root, site);
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
        query.orderBy(orderBy(builder, root, order, orderDir));
        TypedQuery<Site> typedQuery = entityManager.createQuery(query);
        // pagination
        if (start != null) {
            typedQuery.setFirstResult(start);
        }
        if (length != null) {
            typedQuery.setMaxResults(length);
        }

        logger.debug("findByCriteria query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Test if T is blank for the query.
     *
     * @param site null return false, all required fields are null return false.
     * @return
     */
    @Override
    public Boolean isBlank(Site site) {
        if (site == null) {
            return true;
        }
        if (site.getId() == null && StringUtils.isBlank(site.getName())
                && StringUtils.isBlank(site.getUrl()) && site.getSiteStatus() == null
                && site.getMerchant() == null) {
            return true;
        }
        return false;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports name, url.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Site> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> namePath = root.get("name");
        Path<String> urlPath = root.get("url");

        // create the predicate expression for all the path
        Predicate namePredicate = builder.like(namePath, likeSearch);
        Predicate urlPredicate = builder.like(urlPath, likeSearch);

        Predicate predicate = builder.or(namePredicate, urlPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, name, url, SiteStatus id, Merchant id, active.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param site is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Site> root, Site
            site) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (site.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(site.getId()));
            return predicate;
        }

        // check name
        if (StringUtils.isNotBlank(site.getName())) {
            predicate = builder.like(root.<String>get("name"),
                    builder.literal("%" + site.getName() + "%"));
        }
        // check url
        if (StringUtils.isNotBlank(site.getUrl())) {
            predicate = builder.like(root.<String>get("url"),
                    builder.literal("%" + site.getUrl() + "%"));
        }

        if (site.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(site.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check Site Status id
        if (site.getSiteStatus() != null && site.getSiteStatus().getId() != null) {
            Predicate siteStatusPredicate = builder.equal(
                    root.join("siteStatus").<Long>get("id"),
                    builder.literal(site.getSiteStatus().getId()));
            if (predicate == null) {
                predicate = siteStatusPredicate;
            } else {
                predicate = builder.and(predicate, siteStatusPredicate);
            }
        }
        // check Site Login id
        if (site.getMerchant() != null && site.getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(
                    root.join("merchant").<Long>get("id"),
                    builder.literal(site.getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, name, url, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<Site> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> namePath = root.get("name");
        Path<String> urlPath = root.get("url");
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
                    case "url":
                        orderBy = builder.asc(urlPath);
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
                    case "url":
                        orderBy = builder.desc(urlPath);
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
