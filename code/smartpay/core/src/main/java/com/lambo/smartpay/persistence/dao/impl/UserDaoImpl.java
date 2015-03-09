package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.UserDao;
import com.lambo.smartpay.persistence.entity.User;
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
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root);

        Path<String> path = root.get("username");
        Predicate predicate = builder.equal(path, username);
        query.where(predicate);

        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        logger.debug("findByName query is " + typedQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Count number of User matching the search. Support ad hoc search on attributes of User.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
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
     * Find all User matching the search. Support ad hoc search on attributes of User.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the User.
     */
    @Override
    public List<User> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
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

        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count User by criteria.
     * Support attributes of User.
     *
     * @param user contains criteria if the field is not null or empty.
     * @return number of the User matching search.
     */
    @Override
    public Long countByAdvanceSearch(User user) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, user);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find User by criteria.
     * Support attributes of User.
     *
     * @param user contains criteria if the field is not null or empty.
     * @return List of the User matching search ordered by id without pagination.
     */
    @Override
    public List<User> findByAdvanceSearch(User user) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, user);

        query.where(predicate);

// default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
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
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<User> root, String search) {
        //TODO Add more search criteria
        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> usernamePath = root.get("username");

        // create the predicate expression for all the path
        Predicate usernamePredicate = builder.like(usernamePath, likeSearch);

        Predicate predicate = builder.or(usernamePredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Supports id, UserStatus id, active.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param user    is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<User> root, User user) {

        // neither of createdTime cannot be null
        if (user.getId() == null && user.getActive() == null && user.getUserStatus() == null) {
            return null;
        }

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (user.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(user.getId()));
            return predicate;
        }

        if (user.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(user.getActive()));
        }

        // check User Status id
        if (user.getUserStatus() != null && user.getUserStatus().getId() != null) {
            Predicate userStatusPredicate = builder.equal(
                    root.join("userStatus").<Long>get("id"),
                    builder.literal(user.getUserStatus().getId()));
            if (predicate == null) {
                predicate = userStatusPredicate;
            } else {
                predicate = builder.and(predicate, userStatusPredicate);
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
    private Order formulateOrderBy(CriteriaBuilder builder, Root<User> root,
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
