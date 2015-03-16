package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CustomerStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerStatus;
import com.lambo.smartpay.service.CustomerStatusService;
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
@Service("customerStatusService")
public class CustomerStatusServiceImpl implements CustomerStatusService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerStatusServiceImpl.class);
    @Autowired
    private CustomerStatusDao customerStatusDao;

    /**
     * Find CustomerStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return customerStatusDao.findByName(name);
    }

    /**
     * Find CustomerStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return customerStatusDao.findByCode(code);
    }

    /**
     * Create a new CustomerStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param customerStatus
     * @return
     */
    @Transactional
    @Override
    public CustomerStatus create(CustomerStatus customerStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (customerStatus == null) {
            throw new MissingRequiredFieldException("CustomerStatus is null.");
        }
        if (StringUtils.isBlank(customerStatus.getName()) ||
                StringUtils.isBlank(customerStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (customerStatusDao.findByName(customerStatus.getName()) != null) {
            throw new NotUniqueException("CustomerStatus with name " + customerStatus.getName() +
                    " already exists.");
        }
        if (customerStatusDao.findByCode(customerStatus.getCode()) != null) {
            throw new NotUniqueException("CustomerStatus with code " + customerStatus.getName() +
                    " already exists.");
        }
        customerStatus.setActive(true);
        // pass all checks, create the object
        return customerStatusDao.create(customerStatus);
    }

    /**
     * Get CustomerStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CustomerStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (customerStatusDao.get(id) == null) {
            throw new NoSuchEntityException("CustomerStatus with id " + id +
                    " does not exist.");
        }
        return customerStatusDao.get(id);
    }

    /**
     * Update an existing CustomerStatus.
     * Must check unique fields if are unique.
     *
     * @param customerStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public CustomerStatus update(CustomerStatus customerStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (customerStatus == null) {
            throw new MissingRequiredFieldException("CustomerStatus is null.");
        }
        if (customerStatus.getId() == null) {
            throw new MissingRequiredFieldException("CustomerStatus id is null.");
        }
        if (StringUtils.isBlank(customerStatus.getName()) ||
                StringUtils.isBlank(customerStatus.getCode())) {
            throw new MissingRequiredFieldException("CustomerStatus name or code is blank.");
        }
        // checking uniqueness
        CustomerStatus namedCustomerStatus = customerStatusDao.findByName(customerStatus.getName());
        if (namedCustomerStatus != null &&
                !namedCustomerStatus.getId().equals(customerStatus.getId())) {
            throw new NotUniqueException("CustomerStatus with name " + customerStatus.getName() +
                    " already exists.");
        }
        CustomerStatus codedCustomerStatus = customerStatusDao.findByCode(customerStatus.getCode());
        if (codedCustomerStatus != null &&
                !codedCustomerStatus.getId().equals(customerStatus.getId())) {
            throw new NotUniqueException("CustomerStatus with code " + customerStatus.getCode() +
                    " already exists.");
        }
        return customerStatusDao.update(customerStatus);
    }

    /**
     * Delete an existing CustomerStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public CustomerStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        CustomerStatus customerStatus = customerStatusDao.get(id);
        if (customerStatus == null) {
            throw new NoSuchEntityException("CustomerStatus with id " + id +
                    " does not exist.");
        }
        customerStatusDao.delete(id);
        return customerStatus;
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
    public List<CustomerStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceProperties.JpaOrderDir
            orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param customerStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(CustomerStatus customerStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param customerStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length         @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<CustomerStatus> findByAdvanceSearch(CustomerStatus customerStatus, Integer start,
                                                    Integer length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param customerStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param search         instance wildcard search keyword, like name likes %xx%, etc.
     *                       it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CustomerStatus customerStatus, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param customerStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CustomerStatus customerStatus) {
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
     * @param customerStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param search         instance wildcard search keyword, like name likes %xx%, etc.
     *                       it means no criteria with wildcard search if search is null.
     * @param start          first position of the result.
     * @param length         max record of the result.
     * @param order          order by field, default is id.
     * @param orderDir       order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerStatus> findByCriteria(CustomerStatus customerStatus, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param customerStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param start          first position of the result.
     * @param length         max record of the result.
     * @param order          order by field, default is id.
     * @param orderDir       order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CustomerStatus> findByCriteria(CustomerStatus customerStatus, Integer start,
                                               Integer length, String order, ResourceProperties
            .JpaOrderDir orderDir) {
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
    public List<CustomerStatus> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    @Override
    public List<CustomerStatus> getAll() {
        return null;
    }
}
