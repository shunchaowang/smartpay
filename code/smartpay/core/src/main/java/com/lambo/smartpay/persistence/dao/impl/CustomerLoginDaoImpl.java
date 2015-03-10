package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CustomerLoginDao;
import com.lambo.smartpay.persistence.entity.CustomerLogin;
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
                                                 String order, ResourceUtil.JpaOrderDir orderDir,
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
                                   String order, ResourceUtil.JpaOrderDir orderDir) {

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
}
