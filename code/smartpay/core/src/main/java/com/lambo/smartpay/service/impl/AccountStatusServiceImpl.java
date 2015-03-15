package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.AccountStatusDao;
import com.lambo.smartpay.persistence.entity.AccountStatus;
import com.lambo.smartpay.service.AccountStatusService;
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
@Service("accountStatusService")
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
    public List<AccountStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                 String order, ResourceProperties.JpaOrderDir
            orderDir,
                                                 Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param accountStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(AccountStatus accountStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param accountStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length        @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<AccountStatus> findByAdvanceSearch(AccountStatus accountStatus, Integer start,
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
     * @param accountStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param search        instance wildcard search keyword, like name likes %xx%, etc.
     *                      it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(AccountStatus accountStatus, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param accountStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(AccountStatus accountStatus) {
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
     * @param accountStatus contains all criteria for equals, like name equals xx and active equals
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
    public List<AccountStatus> findByCriteria(AccountStatus accountStatus, String search, Integer
            start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param accountStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param start         first position of the result.
     * @param length        max record of the result.
     * @param order         order by field, default is id.
     * @param orderDir      order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<AccountStatus> findByCriteria(AccountStatus accountStatus, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
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
    public List<AccountStatus> findByCriteria(String search, Integer start, Integer length,
                                              String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }
}
