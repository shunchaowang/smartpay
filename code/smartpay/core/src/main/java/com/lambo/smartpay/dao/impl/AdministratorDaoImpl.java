package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.AdministratorDao;
import com.lambo.smartpay.model.Administrator;
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
 * Dao Impl for Administrator.
 * Created by swang on 2/27/2015.
 */
@Repository("administratorDao")
public class AdministratorDaoImpl extends GenericDaoImpl<Administrator, Long>
        implements AdministratorDao {

    private static final Logger LOG = LoggerFactory.getLogger(AdministratorDaoImpl.class);

    /**
     * Find Administrator by username.
     *
     * @param username username trying to find
     * @return administrator with the username or null if not existing
     */
    @Override
    public Administrator findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrator> query = builder.createQuery(Administrator.class);

        Root<Administrator> root = query.from(Administrator.class);
        query.select(root);

        Path<String> path = root.get("username");
        Predicate predicate = builder.equal(path, username);
        query.where(predicate);

        TypedQuery<Administrator> typedQuery = entityManager.createQuery(query);

        LOG.debug("findByUsername query is " + typedQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Find Administrator by email.
     *
     * @param email email trying to find
     * @return administrator with the email or null if not existing
     */
    @Override
    public Administrator findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrator> query = builder.createQuery(Administrator.class);

        Root<Administrator> root = query.from(Administrator.class);
        query.select(root);

        Path<String> path = root.get("email");
        Predicate predicate = builder.equal(path, email);
        query.where(predicate);

        TypedQuery<Administrator> typedQuery = entityManager.createQuery(query);

        LOG.debug("findByEmail query is " + typedQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Count number of Administrator matching the search.
     * Support ad hoc search on username,firstName, lastName, email.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Administrator> root = query.from(Administrator.class);
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
        LOG.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find all Administrator matching the search.
     * Support ad hoc search on username, firstName, lastName, email.
     * Support order by id, username, firstName, lastName, email, active, createdTime.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Administrator.
     */
    @Override
    public List<Administrator> findByAdHocSearch(String search, Integer start, Integer length,
                                                 String order, ResourceUtil.JpaOrderDir orderDir,
                                                 Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrator> query = builder.createQuery(Administrator.class);
        Root<Administrator> root = query.from(Administrator.class);
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

        TypedQuery<Administrator> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        LOG.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Administrator by criteria.
     *
     * @param administrator contains criteria if the field is not null or empty.
     * @return number of the Administrator matching search.
     */
    @Override
    public Long countByAdvanceSearch(Administrator administrator) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Administrator> root = query.from(Administrator.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, administrator);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        LOG.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Administrator by criteria.
     * Support query on id, username, firstName, lastName, email, active.
     * Support order by id.
     *
     * @param administrator contains criteria if the field is not null or empty.
     * @return List of the Administrator matching search ordered by id without pagination.
     */
    @Override
    public List<Administrator> findByAdvanceSearch(Administrator administrator) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrator> query = builder.createQuery(Administrator.class);
        Root<Administrator> root = query.from(Administrator.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, administrator);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Administrator> typedQuery = entityManager.createQuery(query);
        LOG.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Supports username, firstName, lastName, email.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Administrator> root,
                                         String search) {
        String likeSearch = "%" + search + "%";

        // get all paths
        Path<String> usernamePath = root.get("username");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");

        // create all predicates
        Predicate usernamePredicate = builder.like(usernamePath, likeSearch);
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);

        Predicate predicate = builder.or(usernamePredicate, firstNamePredicate, lastNamePredicate,
                emailPredicate);

        LOG.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Support id, username, firstName, lastName, email, active.
     *
     * @param builder       is the JPA CriteriaBuilder.
     * @param root          is the root of the CriteriaQuery.
     * @param administrator is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Administrator> root,
                                         Administrator administrator) {
        // if not criteria return
        if (administrator.getId() == null && StringUtils.isBlank(administrator.getUsername()) &&
                StringUtils.isBlank(administrator.getFirstName()) &&
                StringUtils.isBlank(administrator.getLastName()) &&
                StringUtils.isBlank(administrator.getEmail()) &&
                administrator.getActive() == null) {
            return null;
        }

        Predicate predicate = null;
        // id query, if id != null, return query by id
        if (administrator.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(administrator.getId()));
            return predicate;
        }

        // username query
        if (StringUtils.isNotBlank(administrator.getUsername())) {
            predicate = builder.like(root.<String>get("username"), builder.literal("%" +
                    administrator.getUsername() + "%"));
        }

        // firstName query
        if (StringUtils.isNotBlank(administrator.getFirstName())) {
            Predicate firstNamePredicate = builder.like(root.<String>get("firstName"),
                    builder.literal("%" + administrator.getFirstName() + "%"));
            if (predicate == null) {
                predicate = firstNamePredicate;
            } else {
                predicate = builder.and(predicate, firstNamePredicate);
            }
        }
        // lastName query
        if (StringUtils.isNotBlank(administrator.getLastName())) {
            Predicate lastNamePredicate = builder.like(root.<String>get("lastName"),
                    builder.literal("%" + administrator.getLastName() + "%"));
            if (predicate == null) {
                predicate = lastNamePredicate;
            } else {
                predicate = builder.and(predicate, lastNamePredicate);
            }
        }
        // email query
        if (StringUtils.isNotBlank(administrator.getEmail())) {
            Predicate emailPredicate = builder.like(root.<String>get("email"),
                    builder.literal("%" + administrator.getEmail() + "%"));
            if (predicate == null) {
                predicate = emailPredicate;
            } else {
                predicate = builder.and(predicate, emailPredicate);
            }
        }
        // active query
        if (administrator.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(administrator.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        LOG.debug("Formulate predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order for CriteriaQuery.
     * Supports id, username, firstName, lastName, email, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<Administrator> root,
                                   String order, ResourceUtil.JpaOrderDir orderDir) {
        // get all paths
        Path<Long> idPath = root.get("id");
        Path<String> usernamePath = root.get("username");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");
        Path<Date> createdTimePath = root.get("createdTime");

        Order orderBy = null;
        switch (orderDir) {
            // create Order for asc
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "username":
                        orderBy = builder.asc(usernamePath);
                        break;
                    case "firstName":
                        orderBy = builder.asc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.asc(lastNamePath);
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
            // create Order for desc
            case DESC:
                switch (order) {
                    case "id":
                        orderBy = builder.desc(idPath);
                        break;
                    case "username":
                        orderBy = builder.desc(usernamePath);
                        break;
                    case "firstName":
                        orderBy = builder.desc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.desc(lastNamePath);
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
        }

        LOG.debug("Formulated order clause is " + orderBy);
        return orderBy;
    }
}
