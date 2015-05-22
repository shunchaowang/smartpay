package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.UserDao;
import com.lambo.smartpay.core.persistence.entity.Role;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        logger.debug("Search of dao: " + search);
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
        if (StringUtils.isBlank(order)) {
            order = "id";
        }
        if (orderDir == null) {
            orderDir = ResourceProperties.JpaOrderDir.DESC;
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

        logger.debug("findByCriteria query is " + typedQuery.unwrap(Query.class).getQueryString());
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
                && user.getRoles() == null
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
     * Merchant id, Role ids, active.
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
        // check user merchant id
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

        // check user role id
        /*
        if (user.getRole() != null && user.getRole().getId() != null) {
            Predicate rolePredicate = builder.equal(
                    root.join("role").<Long>get("id"),
                    builder.literal(user.getRole().getId()));
            if (predicate == null) {
                predicate = rolePredicate;
            } else {
                predicate = builder.and(predicate, rolePredicate);
            }
        }
        */

        // check role ids
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            // create set for role id
            Set<Long> rolesIds = new HashSet<>();
            for (Role role : user.getRoles()) {
                rolesIds.add(role.getId());
            }
            // join roles from user and obtain id path
            // create in clause for role id in role ids
            Predicate rolePredicate = root.join("roles").get("id").in(rolesIds);
            if (predicate == null) {
                predicate = rolePredicate;
            } else {
                predicate = builder.and(predicate, rolePredicate);
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
