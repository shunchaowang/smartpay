package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CustomerLoginDao;
import com.lambo.smartpay.core.persistence.entity.CustomerLogin;
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
 * Created by swang on 3/6/2015.
 */
@Repository("customerLoginDao")
public class CustomerLoginDaoImpl extends GenericDaoImpl<CustomerLogin, Long>
        implements CustomerLoginDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoginDaoImpl.class);

    /**
     * Find CustomerLogin by loginEmail.
     *
     * @param loginEmail of the CustomerLogin
     * @return CustomerLogin with loginEmail specified or null is not existing
     */
    @Override
    public CustomerLogin findByLoginEmail(String loginEmail) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerLogin> query = builder.createQuery(CustomerLogin.class);

        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(root);

        Path<String> path = root.get("email");
        Predicate predicate = builder.equal(path, loginEmail);
        query.where(predicate);

        TypedQuery<CustomerLogin> typedQuery = entityManager.createQuery(query);

        logger.debug("findByName query is " + typedQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param customerLogin contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param search        instance wildcard search keyword, like name likes %xx%, etc.
     *                      it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CustomerLogin customerLogin, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(customerLogin)) {
            predicate = equalPredicate(builder, root, customerLogin);
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
            logger.debug(e.getMessage());
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
     * @param customerLogin contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param search        instance wildcard search keyword, like name likes %xx%, etc.
     *                      it means no criteria with wildcard search if search is null.
     * @param start         first position of the result.
     * @param length        max record of the result.
     * @param order         order by field, default is id.
     * @param orderDir      order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerLogin> findByCriteria(CustomerLogin customerLogin, String search, Integer
            start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerLogin> query = builder.createQuery(CustomerLogin.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(customerLogin)) {
            predicate = equalPredicate(builder, root, customerLogin);
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
        TypedQuery<CustomerLogin> typedQuery = entityManager.createQuery(query);
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
            logger.debug(e.getMessage());
            return null;
        }
    }

    /**
     * Test if T is blank for the query.
     *
     * @param customerLogin null return false, all required fields are null return false.
     * @return
     */
    @Override
    public Boolean isBlank(CustomerLogin customerLogin) {
        if (customerLogin == null) {
            return true;
        }
        // return true if all fields are blank even customerLogin is not null
        if (customerLogin.getId() == null && StringUtils.isBlank(customerLogin.getFirstName()) &&
                StringUtils.isBlank(customerLogin.getLastName()) &&
                StringUtils.isBlank(customerLogin.getLoginEmail()) &&
                customerLogin.getActive() == null &&
                customerLogin.getCustomerLoginStatus() == null) {
            return true;
        }
        return false;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports firstName, lastName, loginEmail.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<CustomerLogin> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> loginEmailPath = root.get("loginEmail");

        // create the predicate expression for all the path
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate loginEmailPredicate = builder.like(loginEmailPath, likeSearch);

        Predicate predicate = builder.or(firstNamePredicate, lastNamePredicate,
                loginEmailPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, firstName, lastName, loginEmail, CustomerLoginStatus id, active.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param customer is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<CustomerLogin> root,
                                     CustomerLogin
                                             customer) {

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (customer.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(customer.getId()));
            return predicate;
        }

        // check firstName
        if (StringUtils.isNotBlank(customer.getFirstName())) {
            predicate = builder.like(root.<String>get("firstName"),
                    builder.literal("%" + customer.getFirstName() + "%"));
        }
        // check lastName
        if (StringUtils.isNotBlank(customer.getLastName())) {
            predicate = builder.like(root.<String>get("lastName"),
                    builder.literal("%" + customer.getLastName() + "%"));
        }
        // check email
        if (StringUtils.isNotBlank(customer.getLoginEmail())) {
            predicate = builder.like(root.<String>get("loginEmail"),
                    builder.literal("%" + customer.getLoginEmail() + "%"));
        }

        if (customer.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(customer.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check CustomerLogin Status id
        if (customer.getCustomerLoginStatus() != null && customer.getCustomerLoginStatus().getId
                () != null) {
            Predicate customerLoginStatusPredicate = builder.equal(
                    root.join("customerLoginStatus").<Long>get("id"),
                    builder.literal(customer.getCustomerLoginStatus().getId()));
            if (predicate == null) {
                predicate = customerLoginStatusPredicate;
            } else {
                predicate = builder.and(predicate, customerLoginStatusPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, firstName, lastName, loginEmail, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<CustomerLogin> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> loginEmailPath = root.get("loginEmail");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "firstName":
                        orderBy = builder.asc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.asc(lastNamePath);
                        break;
                    case "loginEmail":
                        orderBy = builder.asc(loginEmailPath);
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
                    case "firstName":
                        orderBy = builder.desc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.desc(lastNamePath);
                        break;
                    case "loginEmail":
                        orderBy = builder.desc(loginEmailPath);
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
