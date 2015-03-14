package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CustomerLoginDao;
import com.lambo.smartpay.persistence.entity.CustomerLogin;
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
        return typedQuery.getSingleResult();
    }

    /**
     * Count number of CustomerLogin matching the search. Support ad hoc search on attributes of
     * CustomerLogin.
     * <p/>
     * Support loginEmail, firstName, lastName, CustomerLoginStatus name.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
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
     * Find all CustomerLogin matching the search. Support ad hoc search on attributes of
     * CustomerLogin.
     * <p/>
     * Support loginEmail, firstName, lastName, CustomerLoginStatus name.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the CustomerLogin.
     */
    @Override
    public List<CustomerLogin> findByAdHocSearch(String search, Integer start, Integer length,
                                                 String order, ResourceProperties.JpaOrderDir
            orderDir,
                                                 Boolean activeFlag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerLogin> query = builder.createQuery(CustomerLogin.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
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

        TypedQuery<CustomerLogin> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);

        logger.debug("findByAdHocSearch query is " + typedQuery);
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Count CustomerLogin by criteria. Support attributes of CustomerLogin.
     * <p/>
     * Support id, loginEmail, firstName, lastName, active, CustomerLoginStatus id.
     *
     * @param customerLogin contains criteria if the field is not null or empty.
     * @return number of the CustomerLogin matching search.
     */
    @Override
    public Long countByAdvanceSearch(CustomerLogin customerLogin) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(builder.count(root));

        Predicate predicate = formulatePredicate(builder, root, customerLogin);

        query.where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery);
        return super.countAllByCriteria(typedQuery);
    }

    /**
     * Find CustomerLogin by criteria. Support attributes of CustomerLogin.
     * <p/>
     * Support id, loginEmail, firstName, lastName, active, CustomerLoginStatus id.
     *
     * @param customerLogin contains criteria if the field is not null or empty.
     * @return List of the T matching search ordered by id without pagination.
     */
    @Override
    public List<CustomerLogin> findByAdvanceSearch(CustomerLogin customerLogin) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerLogin> query = builder.createQuery(CustomerLogin.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, customerLogin);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<CustomerLogin> typedQuery = entityManager.createQuery(query);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param customerLogin contains criteria if the field is not null or empty.
     * @param start
     * @param length        @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<CustomerLogin> findByAdvanceSearch(CustomerLogin customerLogin, Integer start,
                                                   Integer length) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerLogin> query = builder.createQuery(CustomerLogin.class);
        Root<CustomerLogin> root = query.from(CustomerLogin.class);
        query.select(root);

        Predicate predicate = formulatePredicate(builder, root, customerLogin);

        query.where(predicate);

        // default order is id DESC
        query.orderBy(builder.desc(root.get("id")));

        TypedQuery<CustomerLogin> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(length);
        logger.debug("countByAdHocSearch query is " + typedQuery.toString());
        return super.findAllByCriteria(typedQuery);
    }

    /**
     * Formulate JPA or Predicate for CriteriaQuery.
     * Support loginEmail, firstName, lastName, CustomerLoginStatus name.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<CustomerLogin> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> loginEmailPath = root.get("loginEmail");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        // join on CustomerLoginStatus to search on CustomerLoginStatus name
        Path<String> customerLoginStatusPath = root.join("customerLoginStatus").get("name");

        // create the predicate expression for all the path
        Predicate loginEmailPredicate = builder.like(loginEmailPath, likeSearch);
        Predicate firstNamePredicate = builder.like(firstNamePath, likeSearch);
        Predicate lastNamePredicate = builder.like(lastNamePath, likeSearch);
        Predicate customerLoginStatusPredicate = builder.like(customerLoginStatusPath, likeSearch);

        Predicate predicate = builder.or(loginEmailPredicate, firstNamePredicate,
                lastNamePredicate, customerLoginStatusPredicate);

        // we don't want to have wildcard search on id actually
        // create id predicate expression of search is numeric
//        if (StringUtils.isNumeric(search)) {
//            Path<Long> idPath = root.get("id");
//            Predicate idPredicate = builder.equal(idPath, builder.literal(Long.valueOf(search)));
//            predicate = builder.or(predicate, idPredicate);
//        }

        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + predicate.toString());
        return predicate;
    }

    /**
     * Formulate JPA Predicate for CriteriaQuery.
     * Support id, loginEmail, firstName, lastName, active, CustomerLoginStatus id.
     *
     * @param builder       is the JPA CriteriaBuilder.
     * @param root          is the root of the CriteriaQuery.
     * @param customerLogin is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate formulatePredicate(CriteriaBuilder builder, Root<CustomerLogin> root,
                                         CustomerLogin customerLogin) {

        // neither of createdTime cannot be null
        if (customerLogin.getId() == null &&
                StringUtils.isBlank(customerLogin.getLoginEmail()) &&
                StringUtils.isBlank(customerLogin.getFirstName()) &&
                StringUtils.isBlank(customerLogin.getLastName()) &&
                customerLogin.getActive() == null &&
                customerLogin.getCustomerLoginStatus() == null) {
            return null;
        }

//        Predicate predicate = builder.between(root.<Date>get("createdTime"),
//                builder.literal(createdTimeBegin), builder.literal(createdTimeEnd));

        Predicate predicate = null;
        // check id, if id != null, query by id and return
        if (customerLogin.getId() != null) {
            predicate = builder.equal(root.<Long>get("id"), builder.literal(customerLogin.getId()));
            return predicate;
        }

        // check loginEmail
        if (StringUtils.isNotBlank(customerLogin.getLoginEmail())) {
            predicate = builder.like(root.<String>get("loginEmail"),
                    builder.literal("%" + customerLogin.getLoginEmail() + "%"));
        }
        // check firstName
        if (StringUtils.isNotBlank(customerLogin.getFirstName())) {
            predicate = builder.like(root.<String>get("firstName"),
                    builder.literal("%" + customerLogin.getFirstName() + "%"));
        }
        // check loginEmail
        if (StringUtils.isNotBlank(customerLogin.getLastName())) {
            predicate = builder.like(root.<String>get("lastName"),
                    builder.literal("%" + customerLogin.getLastName() + "%"));
        }

        if (customerLogin.getActive() != null) {
            Predicate activePredicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(customerLogin.getActive()));
            if (predicate == null) {
                predicate = activePredicate;
            } else {
                predicate = builder.and(predicate, activePredicate);
            }
        }

        // check CustomerLoginStatus id
        if (customerLogin.getCustomerLoginStatus() != null &&
                customerLogin.getCustomerLoginStatus().getId() != null) {
            Predicate customerLoginStatusPredicate = builder.equal(
                    root.join("customerLoginStatus").<Long>get("id"),
                    builder.literal(customerLogin.getCustomerLoginStatus().getId()));
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
     * Formulate JPA Order for CriteriaQuery.
     * Supports id, loginEmail, firstName, lastName, CustomerLoginStatus name, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order formulateOrderBy(CriteriaBuilder builder, Root<CustomerLogin> root,
                                   String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<String> loginEmailPath = root.get("loginEmail");
        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> customerLoginStatusPath = root.join("customerLoginStatus").get("name");
        Path<Date> createdTimePath = root.get("createdTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "loginEmail":
                        orderBy = builder.asc(loginEmailPath);
                        break;
                    case "firstName":
                        orderBy = builder.asc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.asc(lastNamePath);
                        break;
                    case "customerLoginStatus":
                        orderBy = builder.asc(customerLoginStatusPath);
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
                    case "loginEmail":
                        orderBy = builder.desc(loginEmailPath);
                        break;
                    case "firstName":
                        orderBy = builder.desc(firstNamePath);
                        break;
                    case "lastName":
                        orderBy = builder.desc(lastNamePath);
                        break;
                    case "customerLoginStatus":
                        orderBy = builder.desc(customerLoginStatusPath);
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

    //TODO NEWLY FROM HERE

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
        return super.findAllByCriteria(typedQuery);
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
