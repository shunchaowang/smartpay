package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.AccountStatusDao;
import com.lambo.smartpay.persistence.entity.AccountStatus;
import com.lambo.smartpay.service.AccountStatusService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service needs to check if the parameters passed in are null or empty.
 * Service also takes care of Transactional management.
 * Created by swang on 3/9/2015.
 */
@Repository("accountStatusService")
public class AccountStatusServiceImpl implements AccountStatusService {

    private static final Logger logger = LoggerFactory.getLogger(AccountStatusServiceImpl.class);
    @Autowired
    private AccountStatusDao accountStatusDao;

    /**
     * Find AccountStatus by name.
     *
     * @param name
     * @return
     * @throws NoSuchEntityException
     */
    @Override
    public AccountStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return accountStatusDao.findByName(name);
    }

    /**
     * Find AccountStatus by code.
     *
     * @param code
     * @return
     * @throws NoSuchEntityException
     */
    @Override
    public AccountStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return accountStatusDao.findByCode(code);
    }

    /**
     * Create a new AccountStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param accountStatus
     * @return
     */
    @Transactional
    @Override
    public AccountStatus create(AccountStatus accountStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (accountStatus == null) {
            throw new MissingRequiredFieldException("AccountStatus is null.");
        }
        if (StringUtils.isBlank(accountStatus.getName()) ||
                StringUtils.isBlank(accountStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (accountStatusDao.findByName(accountStatus.getName()) != null) {
            throw new NotUniqueException("AccountStatus with name " + accountStatus.getName() +
                    " already exists.");
        }
        if (accountStatusDao.findByCode(accountStatus.getCode()) != null) {
            throw new NotUniqueException("AccountStatus with code " + accountStatus.getName() +
                    " already exists.");
        }
        accountStatus.setActive(true);
        // pass all checks, create the object
        return accountStatusDao.create(accountStatus);
    }

    /**
     * Get AccountStatus by id.
     *
     * @param id
     * @return
     * @throws NoSuchEntityException
     */
    @Override
    public AccountStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (accountStatusDao.get(id) == null) {
            throw new NoSuchEntityException("AccountStatus with id " + id +
                    " does not exist.");
        }
        return accountStatusDao.get(id);
    }

    /**
     * Update an existing AccountStatus.
     * Must check unique fields if are unique.
     *
     * @param accountStatus
     * @return
     * @throws MissingRequiredFieldException
     * @throws NotUniqueException
     */
    @Transactional
    @Override
    public AccountStatus update(AccountStatus accountStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (accountStatus == null) {
            throw new MissingRequiredFieldException("AccountStatus is null.");
        }
        if (accountStatus.getId() == null) {
            throw new MissingRequiredFieldException("AccountStatus id is null.");
        }
        if (StringUtils.isBlank(accountStatus.getName()) ||
                StringUtils.isBlank(accountStatus.getCode())) {
            throw new MissingRequiredFieldException("AccountStatus name or code is blank.");
        }
        // checking uniqueness
        AccountStatus namedAccountStatus = accountStatusDao.findByName(accountStatus.getName());
        if (namedAccountStatus != null &&
                !namedAccountStatus.getId().equals(accountStatus.getId())) {
            throw new NotUniqueException("AccountStatus with name " + accountStatus.getName() +
                    " already exists.");
        }
        AccountStatus codedAccountStatus = accountStatusDao.findByCode(accountStatus.getCode());
        if (codedAccountStatus != null &&
                !codedAccountStatus.getId().equals(accountStatus.getId())) {
            throw new NotUniqueException("AccountStatus with code " + accountStatus.getCode() +
                    " already exists.");
        }
        return accountStatusDao.update(accountStatus);
    }

    /**
     * Delete an existing AccountStatus.
     *
     * @param id
     * @return
     * @throws NoSuchEntityException
     */
    @Transactional
    @Override
    public AccountStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        AccountStatus accountStatus = accountStatusDao.get(id);
        if (accountStatus == null) {
            throw new NoSuchEntityException("AccountStatus with id " + id +
                    " does not exist.");
        }
        accountStatusDao.delete(id);
        return accountStatus;
    }
}
