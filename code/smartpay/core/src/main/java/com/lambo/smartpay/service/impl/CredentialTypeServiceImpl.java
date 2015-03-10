package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.persistence.entity.CredentialType;
import com.lambo.smartpay.service.CredentialTypeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CredentialType create(CredentialType credentialType) throws MissingRequiredFieldException,
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
    public CredentialType update(CredentialType credentialType) throws MissingRequiredFieldException,
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
}
