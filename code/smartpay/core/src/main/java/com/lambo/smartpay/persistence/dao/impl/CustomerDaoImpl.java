package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CustomerDao;
import com.lambo.smartpay.persistence.entity.Customer;
import com.lambo.smartpay.util.ResourceProperties;
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
@Repository("customerDao")
public class CustomerDaoImpl extends GenericDaoImpl<Customer, Long>
        implements CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

    /**
     * Find Customer by email.
     *
     * @param email email of Customer.
     * @return Customer with email or null.
     */
    @Override
    public Customer findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);

        Root<Customer> root = query.from(Customer.class);
        query.select(root);

        Path<String> path = root.get("email");
        Predicate predicate = builder.equal(path, email);
        query.where(predicate);

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);

        logger.debug("findByName query is " + typedQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Count number of Customer matching the search. Support ad hoc search on attributes of
     * Customer.
     * Supports firstName, lastName, email, CustomerStatus name, CustomerLogin loginEmail.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Customer> root = query.from(Customer.class);
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
     * Find all Customer matching the search. Support ad hoc search on attributes of Customer.
     * Supports firstName, lastName, email, CustomerStatus name, CustomerLogin loginEmail.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Customer.
     */
    @Override
    public List<Customer> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
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

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count Customer by criteria.
     * Support attributes of Customer.
     * Supports id, firstName, lastName, email, CustomerStatus id, CustomerLogin id, active.
     *
     * @param customer contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Customer customer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, customer);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find Customer by criteria.
     * Support attributes of Customer.
     * Supports id, firstName, lastName, email, CustomerStatus id, CustomerLogin id, active.
     *
     * @param customer contains criteria if the field is not null or empty.
     * @return List of the Customer matching search ordered by id without pagination.
     */
    @Override
    public List<Customer> findByAdvanceSearch(Customer customer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, customer);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param customer contains criteria if the field is not null or empty.
     * @param start
     * @param length   @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Customer> findByAdvanceSearch(Customer customer, Integer start, Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, customer);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    //TODO NEWLY FROM HERE


    /**
     * Test if T is blank for the query.
     *
     * @param customer null return false, all required fields are null return false.
     * @return
     */
    @Override
    public Boolean isBlank(Customer customer) {
        if (customer == null) {
            return true;
        }
        // return true if all fields are blank even customer is not null
        if (customer.getId() == null && StringUtils.isBlank(customer.getFirstName()) &&
                StringUtils.isBlank(customer.getLastName()) && customer.getActive() == null &&
                StringUtils.isBlank(customer.getEmail()) &&
                customer.getCustomerStatus() == null && customer.getCustomerLogin() == null) {
            return true;
        }
        return false;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param customer contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Customer customer, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(builder.count(root));

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(customer)) {
            predicate = equalPredicate(builder, root, customer);
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
     * @param customer contains all criteria for equals, like name equals xx and active equals
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
    public List<Customer> findByCriteria(Customer customer, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(customer)) {
            predicate = equalPredicate(builder, root, customer);
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
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);
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
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports firstName, lastName, email.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Customer> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");

        // create the predicate expression for all the path
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);

        Predicate predicate = builder.or(firstNamePredicate, lastNamePredicate, emailPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA and Predicate on fields with equal criteria for CriteriaQuery.
     * Supports id, firstName, lastName, email, CustomerStatus id, CustomerLogin id, active.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param customer is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate equalPredicate(CriteriaBuilder builder, Root<Customer> root, Customer
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
        if (StringUtils.isNotBlank(customer.getEmail())) {
            predicate = builder.like(root.<String>get("email"),
                    builder.literal("%" + customer.getEmail() + "%"));
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

        // check Customer Status id
        if (customer.getCustomerStatus() != null && customer.getCustomerStatus().getId() != null) {
            Predicate customerStatusPredicate = builder.equal(
                    root.join("customerStatus").<Long>get("id"),
                    builder.literal(customer.getCustomerStatus().getId()));
            if (predicate == null) {
                predicate = customerStatusPredicate;
            } else {
                predicate = builder.and(predicate, customerStatusPredicate);
            }
        }
        // check Customer Login id
        if (customer.getCustomerLogin() != null && customer.getCustomerStatus().getId() != null) {
            Predicate customerLoginPredicate = builder.equal(
                    root.join("customerLogin").<Long>get("id"),
                    builder.literal(customer.getCustomerLogin().getId()));
            if (predicate == null) {
                predicate = customerLoginPredicate;
            } else {
                predicate = builder.and(predicate, customerLoginPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, firstName, lastName, email, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<Customer> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
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

    //TODO NEWLY METHODS ENDS

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Supports firstName, lastName, email, CustomerStatus name, CustomerLogin loginEmail.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Customer> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");
        // join on CustomerStatus to search on CustomerStatus name
        Path<String> customerStatusPath = root.join("customerStatus").get("name");
        Path<String> customerLoginPath = root.join("customerLogin").get("loginEmail");

        // create the predicate expression for all the path
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate emailPredicate = builder.like(emailPath, likeSearch);
        Predicate customerStatusPredicate = builder.like(customerStatusPath, likeSearch);
        Predicate customerLoginPredicate = builder.like(customerLoginPath, likeSearch);

        Predicate predicate = builder.or(firstNamePredicate, lastNamePredicate,
                emailPredicate, customerStatusPredicate, customerLoginPredicate);

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Supports id, firstName, lastName, email, CustomerStatus id, CustomerLogin id, active.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param customer is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<Customer> root, Customer
            customer) {

        // neither of createdTime cannot be null
        if (customer.getId() == null && StringUtils.isBlank(customer.getFirstName()) &&
                StringUtils.isBlank(customer.getLastName()) && customer.getActive() == null &&
                StringUtils.isBlank(customer.getEmail()) &&
                customer.getCustomerStatus() == null && customer.getCustomerLogin() == null) {
            return null;
        }

//        Predicate predicate = builder.between(root.<Date>get("createdTime"),
//                builder.literal(createdTimeBegin), builder.literal(createdTimeEnd));

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
        if (StringUtils.isNotBlank(customer.getEmail())) {
            predicate = builder.like(root.<String>get("email"),
                    builder.literal("%" + customer.getEmail() + "%"));
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

        // check Customer Status id
        if (customer.getCustomerStatus() != null && customer.getCustomerStatus().getId() != null) {
            Predicate customerStatusPredicate = builder.equal(
                    root.join("customerStatus").<Long>get("id"),
                    builder.literal(customer.getCustomerStatus().getId()));
            if (predicate == null) {
                predicate = customerStatusPredicate;
            } else {
                predicate = builder.and(predicate, customerStatusPredicate);
            }
        }
        // check Customer Login id
        if (customer.getCustomerLogin() != null && customer.getCustomerStatus().getId() != null) {
            Predicate customerLoginPredicate = builder.equal(
                    root.join("customerLogin").<Long>get("id"),
                    builder.literal(customer.getCustomerLogin().getId()));
            if (predicate == null) {
                predicate = customerLoginPredicate;
            } else {
                predicate = builder.and(predicate, customerLoginPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, firstName, lastName, email, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<Customer> root,
                                   String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");
        Path<String> customerStatusPath = root.join("customerStatus").get("name");
        Path<String> customerLoginPath = root.join("customerLogin").get("loginEmail");
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
                    case "email":
                        orderBy = builder.asc(emailPath);
                        break;
                    case "customerStatus":
                        orderBy = builder.asc(customerStatusPath);
                        break;
                    case "customerLogin":
                        orderBy = builder.asc(customerLoginPath);
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
                    case "email":
                        orderBy = builder.desc(emailPath);
                        break;
                    case "customerStatus":
                        orderBy = builder.desc(customerStatusPath);
                        break;
                    case "customerLogin":
                        orderBy = builder.desc(customerLoginPath);
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
