package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.service.CredentialStatusService;
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
@Service("credentialStatusService")
public class CredentialStatusServiceImpl implements CredentialStatusService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialStatusServiceImpl.class);
    @Autowired
    private CredentialStatusDao credentialStatusDao;

    /**
     * Find CredentialStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public CredentialStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return credentialStatusDao.findByName(name);
    }

    /**
     * Find CredentialStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public CredentialStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return credentialStatusDao.findByCode(code);
    }

    /**
     * Create a new CredentialStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param credentialStatus
     * @return
     */
    @Transactional
    @Override
    public CredentialStatus create(CredentialStatus credentialStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (credentialStatus == null) {
            throw new MissingRequiredFieldException("CredentialStatus is null.");
        }
        if (StringUtils.isBlank(credentialStatus.getName()) ||
                StringUtils.isBlank(credentialStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (credentialStatusDao.findByName(credentialStatus.getName()) != null) {
            throw new NotUniqueException("CredentialStatus with name " + credentialStatus.getName
                    () +
                    " already exists.");
        }
        if (credentialStatusDao.findByCode(credentialStatus.getCode()) != null) {
            throw new NotUniqueException("CredentialStatus with code " + credentialStatus.getName
                    () +
                    " already exists.");
        }
        credentialStatus.setActive(true);
        // pass all checks, create the object
        return credentialStatusDao.create(credentialStatus);
    }

    /**
     * Get CredentialStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public CredentialStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (credentialStatusDao.get(id) == null) {
            throw new NoSuchEntityException("CredentialStatus with id " + id +
                    " does not exist.");
        }
        return credentialStatusDao.get(id);
    }

    /**
     * Update an existing CredentialStatus.
     * Must check unique fields if are unique.
     *
     * @param credentialStatus
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public CredentialStatus update(CredentialStatus credentialStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (credentialStatus == null) {
            throw new MissingRequiredFieldException("CredentialStatus is null.");
        }
        if (credentialStatus.getId() == null) {
            throw new MissingRequiredFieldException("CredentialStatus id is null.");
        }
        if (StringUtils.isBlank(credentialStatus.getName()) ||
                StringUtils.isBlank(credentialStatus.getCode())) {
            throw new MissingRequiredFieldException("CredentialStatus name or code is blank.");
        }
        // checking uniqueness
        CredentialStatus namedCredentialStatus = credentialStatusDao.findByName(credentialStatus
                .getName());
        if (namedCredentialStatus != null &&
                !namedCredentialStatus.getId().equals(credentialStatus.getId())) {
            throw new NotUniqueException("CredentialStatus with name " + credentialStatus.getName
                    () +
                    " already exists.");
        }
        CredentialStatus codedCredentialStatus = credentialStatusDao.findByCode(credentialStatus
                .getCode());
        if (codedCredentialStatus != null &&
                !codedCredentialStatus.getId().equals(credentialStatus.getId())) {
            throw new NotUniqueException("CredentialStatus with code " + credentialStatus.getCode
                    () +
                    " already exists.");
        }
        return credentialStatusDao.update(credentialStatus);
    }

    /**
     * Delete an existing CredentialStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public CredentialStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        CredentialStatus credentialStatus = credentialStatusDao.get(id);
        if (credentialStatus == null) {
            throw new NoSuchEntityException("CredentialStatus with id " + id +
                    " does not exist.");
        }
        credentialStatusDao.delete(id);
        return credentialStatus;
    }

    @Override
    public List<CredentialStatus> getAll() {
        return credentialStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return credentialStatusDao.countAll();
    }
}
