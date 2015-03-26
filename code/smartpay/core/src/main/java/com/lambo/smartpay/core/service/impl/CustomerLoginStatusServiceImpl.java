package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.core.persistence.entity.CustomerLoginStatus;
import com.lambo.smartpay.core.service.CustomerLoginStatusService;
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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

    @Override
    public List<CustomerLoginStatus> getAll() {
        return customerLoginStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return customerLoginStatusDao.countAll();
    }
}
