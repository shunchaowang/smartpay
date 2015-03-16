package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.AccountTypeDao;
import com.lambo.smartpay.persistence.entity.AccountType;
import com.lambo.smartpay.service.AccountTypeService;
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
    public List<AccountType> findByAdHocSearch(String search, Integer start, Integer length,
                                               String order, ResourceProperties.JpaOrderDir
            orderDir,
                                               Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param accountType contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(AccountType accountType) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param accountType contains criteria if the field is not null or empty.
     * @param start
     * @param length      @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<AccountType> findByAdvanceSearch(AccountType accountType, Integer start, Integer
            length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param accountType contains all criteria for equals, like name equals xx and active equals
     *                    true, etc.
     *                    it means no criteria on exact equals if t is null.
     * @param search      instance wildcard search keyword, like name likes %xx%, etc.
     *                    it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(AccountType accountType, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param accountType contains all criteria for equals, like name equals xx and active equals
     *                    true, etc.
     *                    it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(AccountType accountType) {
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
     * @param accountType contains all criteria for equals, like name equals xx and active equals
     *                    true, etc.
     *                    it means no criteria on exact equals if t is null.
     * @param search      instance wildcard search keyword, like name likes %xx%, etc.
     *                    it means no criteria with wildcard search if search is null.
     * @param start       first position of the result.
     * @param length      max record of the result.
     * @param order       order by field, default is id.
     * @param orderDir    order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<AccountType> findByCriteria(AccountType accountType, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param accountType contains all criteria for equals, like name equals xx and active equals
     *                    true, etc.
     *                    it means no criteria on exact equals if t is null.
     * @param start       first position of the result.
     * @param length      max record of the result.
     * @param order       order by field, default is id.
     * @param orderDir    order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<AccountType> findByCriteria(AccountType accountType, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
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
    public List<AccountType> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    @Override
    public List<AccountType> getAll() {
        return null;
    }
}
