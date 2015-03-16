package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.persistence.entity.CredentialType;
import com.lambo.smartpay.service.CredentialTypeService;
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
@Service("credentialTypeService")
public class CredentialTypeServiceImpl implements CredentialTypeService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialTypeServiceImpl.class);
    @Autowired
    private CredentialTypeDao credentialTypeDao;

    /**
     * Find CredentialType by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CredentialType findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return credentialTypeDao.findByName(name);
    }

    /**
     * Find CredentialType by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CredentialType findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return credentialTypeDao.findByCode(code);
    }

    /**
     * Create a new CredentialType.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param credentialType
     * @return
     */
    @Transactional
    @Override
    public CredentialType create(CredentialType credentialType) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (credentialType == null) {
            throw new MissingRequiredFieldException("CredentialType is null.");
        }
        if (StringUtils.isBlank(credentialType.getName()) ||
                StringUtils.isBlank(credentialType.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (credentialTypeDao.findByName(credentialType.getName()) != null) {
            throw new NotUniqueException("CredentialType with name " + credentialType.getName() +
                    " already exists.");
        }
        if (credentialTypeDao.findByCode(credentialType.getCode()) != null) {
            throw new NotUniqueException("CredentialType with code " + credentialType.getName() +
                    " already exists.");
        }
        credentialType.setActive(true);
        // pass all checks, create the object
        return credentialTypeDao.create(credentialType);
    }

    /**
     * Get CredentialType by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public CredentialType get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (credentialTypeDao.get(id) == null) {
            throw new NoSuchEntityException("CredentialType with id " + id +
                    " does not exist.");
        }
        return credentialTypeDao.get(id);
    }

    /**
     * Update an existing CredentialType.
     * Must check unique fields if are unique.
     *
     * @param credentialType
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public CredentialType update(CredentialType credentialType) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (credentialType == null) {
            throw new MissingRequiredFieldException("CredentialType is null.");
        }
        if (credentialType.getId() == null) {
            throw new MissingRequiredFieldException("CredentialType id is null.");
        }
        if (StringUtils.isBlank(credentialType.getName()) ||
                StringUtils.isBlank(credentialType.getCode())) {
            throw new MissingRequiredFieldException("CredentialType name or code is blank.");
        }
        // checking uniqueness
        CredentialType namedCredentialType = credentialTypeDao.findByName(credentialType.getName());
        if (namedCredentialType != null &&
                !namedCredentialType.getId().equals(credentialType.getId())) {
            throw new NotUniqueException("CredentialType with name " + credentialType.getName() +
                    " already exists.");
        }
        CredentialType codedCredentialType = credentialTypeDao.findByCode(credentialType.getCode());
        if (codedCredentialType != null &&
                !codedCredentialType.getId().equals(credentialType.getId())) {
            throw new NotUniqueException("CredentialType with code " + credentialType.getCode() +
                    " already exists.");
        }
        return credentialTypeDao.update(credentialType);
    }

    /**
     * Delete an existing CredentialType.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public CredentialType delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        CredentialType credentialType = credentialTypeDao.get(id);
        if (credentialType == null) {
            throw new NoSuchEntityException("CredentialType with id " + id +
                    " does not exist.");
        }
        credentialTypeDao.delete(id);
        return credentialType;
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
    public List<CredentialType> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceProperties.JpaOrderDir
            orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param credentialType contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(CredentialType credentialType) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param credentialType contains criteria if the field is not null or empty.
     * @param start
     * @param length         @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<CredentialType> findByAdvanceSearch(CredentialType credentialType, Integer start,
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
     * @param credentialType contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param search         instance wildcard search keyword, like name likes %xx%, etc.
     *                       it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CredentialType credentialType, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param credentialType contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CredentialType credentialType) {
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
     * @param credentialType contains all criteria for equals, like name equals xx and active equals
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
    public List<CredentialType> findByCriteria(CredentialType credentialType, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param credentialType contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param start          first position of the result.
     * @param length         max record of the result.
     * @param order          order by field, default is id.
     * @param orderDir       order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CredentialType> findByCriteria(CredentialType credentialType, Integer start,
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
    public List<CredentialType> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    @Override
    public List<CredentialType> getAll() {
        return null;
    }
}
