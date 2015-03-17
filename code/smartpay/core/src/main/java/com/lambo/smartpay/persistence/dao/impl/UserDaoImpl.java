package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.UserDao;
import com.lambo.smartpay.persistence.entity.User;
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

        logger.debug("findByUsername query is " + typedQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root);

        Path<String> path = root.get("email");
        Predicate predicate = builder.equal(path, email);
        query.where(predicate);

        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        logger.debug("findByEmail query is " + typedQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Cannot find user with email " + email);
            e.printStackTrace();
            return null;
        }
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
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
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
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param user   contains criteria if the field is not null or empty.
     * @param start
     * @param length @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<User> findByAdvanceSearch(User user, Integer start, Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, user);

        query.where(predicate);

// default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
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
                                   String order, ResourceProperties.JpaOrderDir orderDir) {

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

    //TODO NEWLY METHODS

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param user   contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(User user, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(user)) {
            predicate = equalPredicate(builder, root, user);
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
     * @param user     contains all criteria for equals, like name equals xx and active equals
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
    public List<User> findByCriteria(User user, String search, Integer start, Integer length,
                                     String order, ResourceProperties.JpaOrderDir orderDir) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(user)) {
            predicate = equalPredicate(builder, root, user);
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
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
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
     * @param user null return false, all required fields are null return false.
     * @return
     */
    @Override
    public Boolean isBlank(User user) {
        if (user == null) {
            return true;
        }
        if (user.getId() == null && StringUtils.isBlank(user.getUsername())
                && StringUtils.isBlank(user.getFirstName())
                && StringUtils.isBlank(user.getLastName())
                && StringUtils.isBlank(user.getEmail())
                && user.getUserStatus() == null && user.getMerchant() == null
                && user.getActive() == null) {
            return true;
        }
        return false;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports username firstName, lastName, email.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<User> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> usernamePath = root.get("username");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");

        // create the predicate expression for all the path
        Predicate usernamePredicate = builder.like(usernamePath, likeSearch);
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);

        Predicate predicate = builder.or(usernamePredicate, firstNamePredicate,
                lastNamePredicate, emailPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, username, firstName, lastName, email, UserStatus id,
     * Merchant id, active.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param user    is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<User> root, User
            user) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (user.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(user.getId()));
            return predicate;
        }

        // check username
        if (StringUtils.isNotBlank(user.getUsername())) {
            predicate = builder.like(root.<String>get("username"),
                    builder.literal("%" + user.getUsername() + "%"));
        }
        // check firstName
        if (StringUtils.isNotBlank(user.getFirstName())) {
            predicate = builder.like(root.<String>get("firstName"),
                    builder.literal("%" + user.getFirstName() + "%"));
        }
        // check lastName
        if (StringUtils.isNotBlank(user.getLastName())) {
            predicate = builder.like(root.<String>get("lastName"),
                    builder.literal("%" + user.getLastName() + "%"));
        }
        // check email
        if (StringUtils.isNotBlank(user.getEmail())) {
            predicate = builder.like(root.<String>get("email"),
                    builder.literal("%" + user.getEmail() + "%"));
        }

        if (user.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(user.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
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
        // check User Login id
        if (user.getMerchant() != null && user.getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(
                    root.join("merchant").<Long>get("id"),
                    builder.literal(user.getMerchant().getId()));
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
     * Supports id, username, firstName, lastName, email, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<User> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> usernamePath = root.get("username");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
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
                break;
        }

        logger.debug("Formulated order by clause is " + orderBy.toString());
        return orderBy;
    }
}
