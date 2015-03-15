package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerLoginStatus;
import com.lambo.smartpay.service.CustomerLoginStatusService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service needs to check if the parameters passed in are null or empty.
 * Service also takes care of Transactional management.
 * Created by swang on 3/9/2015.
 */
@Service("customerLoginStatusService")
public class CustomerLoginStatusServiceImpl implements CustomerLoginStatusService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoginStatusServiceImpl
            .class);
    @Autowired
    private CustomerLoginStatusDao customerLoginStatusDao;

    /**
     * Find CustomerLoginStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerLoginStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return customerLoginStatusDao.findByName(name);
    }

    /**
     * Find CustomerLoginStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerLoginStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return customerLoginStatusDao.findByCode(code);
    }

    /**
     * Create a new CustomerLoginStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param customerLoginStatus
     * @return
     */
    @Transactional
    @Override
    public CustomerLoginStatus create(CustomerLoginStatus customerLoginStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (customerLoginStatus == null) {
            throw new MissingRequiredFieldException("CustomerLoginStatus is null.");
        }
        if (StringUtils.isBlank(customerLoginStatus.getName()) ||
                StringUtils.isBlank(customerLoginStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (customerLoginStatusDao.findByName(customerLoginStatus.getName()) != null) {
            throw new NotUniqueException("CustomerLoginStatus with name " + customerLoginStatus
                    .getName() +
                    " already exists.");
        }
        if (customerLoginStatusDao.findByCode(customerLoginStatus.getCode()) != null) {
            throw new NotUniqueException("CustomerLoginStatus with code " + customerLoginStatus
                    .getName() +
                    " already exists.");
        }
        customerLoginStatus.setActive(true);
        // pass all checks, create the object
        return customerLoginStatusDao.create(customerLoginStatus);
    }

    /**
     * Get CustomerLoginStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerLoginStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (customerLoginStatusDao.get(id) == null) {
            throw new NoSuchEntityException("CustomerLoginStatus with id " + id +
                    " does not exist.");
        }
        return customerLoginStatusDao.get(id);
    }

    /**
     * Update an existing CustomerLoginStatus.
     * Must check unique fields if are unique.
     *
     * @param customerLoginStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public CustomerLoginStatus update(CustomerLoginStatus customerLoginStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (customerLoginStatus == null) {
            throw new MissingRequiredFieldException("CustomerLoginStatus is null.");
        }
        if (customerLoginStatus.getId() == null) {
            throw new MissingRequiredFieldException("CustomerLoginStatus id is null.");
        }
        if (StringUtils.isBlank(customerLoginStatus.getName()) ||
                StringUtils.isBlank(customerLoginStatus.getCode())) {
            throw new MissingRequiredFieldException("CustomerLoginStatus name or code is blank.");
        }
        // checking uniqueness
        CustomerLoginStatus namedCustomerLoginStatus = customerLoginStatusDao.findByName
                (customerLoginStatus.getName());
        if (namedCustomerLoginStatus != null &&
                !namedCustomerLoginStatus.getId().equals(customerLoginStatus.getId())) {
            throw new NotUniqueException("CustomerLoginStatus with name " + customerLoginStatus
                    .getName() +
                    " already exists.");
        }
        CustomerLoginStatus codedCustomerLoginStatus = customerLoginStatusDao.findByCode
                (customerLoginStatus.getCode());
        if (codedCustomerLoginStatus != null &&
                !codedCustomerLoginStatus.getId().equals(customerLoginStatus.getId())) {
            throw new NotUniqueException("CustomerLoginStatus with code " + customerLoginStatus
                    .getCode() +
                    " already exists.");
        }
        return customerLoginStatusDao.update(customerLoginStatus);
    }

    /**
     * Delete an existing CustomerLoginStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public CustomerLoginStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        CustomerLoginStatus customerLoginStatus = customerLoginStatusDao.get(id);
        if (customerLoginStatus == null) {
            throw new NoSuchEntityException("CustomerLoginStatus with id " + id +
                    " does not exist.");
        }
        customerLoginStatusDao.delete(id);
        return customerLoginStatus;
    }

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        return null;
    }

    /**
     * Find all T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    @Override
    public List<CustomerLoginStatus> findByAdHocSearch(String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param customerLoginStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(CustomerLoginStatus customerLoginStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param customerLoginStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length              @return List of the T matching search ordered by id with
     *                            pagination.
     */
    @Override
    public List<CustomerLoginStatus> findByAdvanceSearch(CustomerLoginStatus customerLoginStatus,
                                                         Integer start, Integer length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param customerLoginStatus contains all criteria for equals, like name equals xx and
     *                            active equals
     *                            true, etc.
     *                            it means no criteria on exact equals if t is null.
     * @param search              instance wildcard search keyword, like name likes %xx%, etc.
     *                            it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CustomerLoginStatus customerLoginStatus, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param customerLoginStatus contains all criteria for equals, like name equals xx and
     *                            active equals
     *                            true, etc.
     *                            it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CustomerLoginStatus customerLoginStatus) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * <p/>
     * it means no criteria on exact equals if t is null.
     *
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param customerLoginStatus contains all criteria for equals, like name equals xx and
     *                            active equals
     *                            true, etc.
     *                            it means no criteria on exact equals if t is null.
     * @param search              instance wildcard search keyword, like name likes %xx%, etc.
     *                            it means no criteria with wildcard search if search is null.
     * @param start               first position of the result.
     * @param length              max record of the result.
     * @param order               order by field, default is id.
     * @param orderDir            order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerLoginStatus> findByCriteria(CustomerLoginStatus customerLoginStatus,
                                                    String search, Integer start, Integer length,
                                                    String order, ResourceProperties.JpaOrderDir
                                                            orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param customerLoginStatus contains all criteria for equals, like name equals xx and
     *                            active equals
     *                            true, etc.
     *                            it means no criteria on exact equals if t is null.
     * @param start               first position of the result.
     * @param length              max record of the result.
     * @param order               order by field, default is id.
     * @param orderDir            order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerLoginStatus> findByCriteria(CustomerLoginStatus customerLoginStatus,
                                                    Integer start, Integer length, String order,
                                                    ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerLoginStatus> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }
}
