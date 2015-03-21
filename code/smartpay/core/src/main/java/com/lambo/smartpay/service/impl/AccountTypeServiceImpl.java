package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.AccountTypeDao;
import com.lambo.smartpay.persistence.entity.AccountType;
import com.lambo.smartpay.service.AccountTypeService;
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
@Service("accountTypeService")
public class AccountTypeServiceImpl implements AccountTypeService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTypeServiceImpl.class);
    @Autowired
    private AccountTypeDao accountTypeDao;

    /**
     * Find AccountType by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public AccountType findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return accountTypeDao.findByName(name);
    }

    /**
     * Find AccountType by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public AccountType findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return accountTypeDao.findByCode(code);
    }

    /**
     * Create a new AccountType.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param accountType
     * @return
     */
    @Transactional
    @Override
    public AccountType create(AccountType accountType) throws MissingRequiredFieldException,
            NotUniqueException {
        if (accountType == null) {
            throw new MissingRequiredFieldException("AccountType is null.");
        }
        if (StringUtils.isBlank(accountType.getName()) ||
                StringUtils.isBlank(accountType.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (accountTypeDao.findByName(accountType.getName()) != null) {
            throw new NotUniqueException("AccountType with name " + accountType.getName() +
                    " already exists.");
        }
        if (accountTypeDao.findByCode(accountType.getCode()) != null) {
            throw new NotUniqueException("AccountType with code " + accountType.getName() +
                    " already exists.");
        }
        accountType.setActive(true);
        // pass all checks, create the object
        return accountTypeDao.create(accountType);
    }

    /**
     * Get AccountType by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public AccountType get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (accountTypeDao.get(id) == null) {
            throw new NoSuchEntityException("AccountType with id " + id +
                    " does not exist.");
        }
        return accountTypeDao.get(id);
    }

    /**
     * Update an existing AccountType.
     * Must check unique fields if are unique.
     *
     * @param accountType
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public AccountType update(AccountType accountType) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (accountType == null) {
            throw new MissingRequiredFieldException("AccountType is null.");
        }
        if (accountType.getId() == null) {
            throw new MissingRequiredFieldException("AccountType id is null.");
        }
        if (StringUtils.isBlank(accountType.getName()) ||
                StringUtils.isBlank(accountType.getCode())) {
            throw new MissingRequiredFieldException("AccountType name or code is blank.");
        }
        // checking uniqueness
        AccountType namedAccountType = accountTypeDao.findByName(accountType.getName());
        if (namedAccountType != null &&
                !namedAccountType.getId().equals(accountType.getId())) {
            throw new NotUniqueException("AccountType with name " + accountType.getName() +
                    " already exists.");
        }
        AccountType codedAccountType = accountTypeDao.findByCode(accountType.getCode());
        if (codedAccountType != null &&
                !codedAccountType.getId().equals(accountType.getId())) {
            throw new NotUniqueException("AccountType with code " + accountType.getCode() +
                    " already exists.");
        }
        return accountTypeDao.update(accountType);
    }

    /**
     * Delete an existing AccountType.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public AccountType delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        AccountType accountType = accountTypeDao.get(id);
        if (accountType == null) {
            throw new NoSuchEntityException("AccountType with id " + id +
                    " does not exist.");
        }
        accountTypeDao.delete(id);
        return accountType;
    }

    @Override
    public List<AccountType> getAll() {
        return accountTypeDao.getAll();
    }

    @Override
    public Long countAll() {
        return accountTypeDao.countAll();
    }
}
