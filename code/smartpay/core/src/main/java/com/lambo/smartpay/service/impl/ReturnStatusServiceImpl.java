package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.ReturnStatusDao;
import com.lambo.smartpay.persistence.entity.ReturnStatus;
import com.lambo.smartpay.service.ReturnStatusService;
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
@Service("returnStatusService")
public class ReturnStatusServiceImpl implements ReturnStatusService {

    private static final Logger logger = LoggerFactory.getLogger(ReturnStatusServiceImpl.class);
    @Autowired
    private ReturnStatusDao returnStatusDao;

    /**
     * Find ReturnStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ReturnStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return returnStatusDao.findByName(name);
    }

    /**
     * Find ReturnStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ReturnStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return returnStatusDao.findByCode(code);
    }

    /**
     * Create a new ReturnStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param returnStatus
     * @return
     */
    @Transactional
    @Override
    public ReturnStatus create(ReturnStatus returnStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (returnStatus == null) {
            throw new MissingRequiredFieldException("ReturnStatus is null.");
        }
        if (StringUtils.isBlank(returnStatus.getName()) ||
                StringUtils.isBlank(returnStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (returnStatusDao.findByName(returnStatus.getName()) != null) {
            throw new NotUniqueException("ReturnStatus with name " + returnStatus.getName() +
                    " already exists.");
        }
        if (returnStatusDao.findByCode(returnStatus.getCode()) != null) {
            throw new NotUniqueException("ReturnStatus with code " + returnStatus.getName() +
                    " already exists.");
        }
        returnStatus.setActive(true);
        // pass all checks, create the object
        return returnStatusDao.create(returnStatus);
    }

    /**
     * Get ReturnStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ReturnStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (returnStatusDao.get(id) == null) {
            throw new NoSuchEntityException("ReturnStatus with id " + id +
                    " does not exist.");
        }
        return returnStatusDao.get(id);
    }

    /**
     * Update an existing ReturnStatus.
     * Must check unique fields if are unique.
     *
     * @param returnStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public ReturnStatus update(ReturnStatus returnStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (returnStatus == null) {
            throw new MissingRequiredFieldException("ReturnStatus is null.");
        }
        if (returnStatus.getId() == null) {
            throw new MissingRequiredFieldException("ReturnStatus id is null.");
        }
        if (StringUtils.isBlank(returnStatus.getName()) ||
                StringUtils.isBlank(returnStatus.getCode())) {
            throw new MissingRequiredFieldException("ReturnStatus name or code is blank.");
        }
        // checking uniqueness
        ReturnStatus namedReturnStatus = returnStatusDao.findByName(returnStatus.getName());
        if (namedReturnStatus != null &&
                !namedReturnStatus.getId().equals(returnStatus.getId())) {
            throw new NotUniqueException("ReturnStatus with name " + returnStatus.getName() +
                    " already exists.");
        }
        ReturnStatus codedReturnStatus = returnStatusDao.findByCode(returnStatus.getCode());
        if (codedReturnStatus != null &&
                !codedReturnStatus.getId().equals(returnStatus.getId())) {
            throw new NotUniqueException("ReturnStatus with code " + returnStatus.getCode() +
                    " already exists.");
        }
        return returnStatusDao.update(returnStatus);
    }

    /**
     * Delete an existing ReturnStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public ReturnStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        ReturnStatus returnStatus = returnStatusDao.get(id);
        if (returnStatus == null) {
            throw new NoSuchEntityException("ReturnStatus with id " + id +
                    " does not exist.");
        }
        returnStatusDao.delete(id);
        return returnStatus;
    }

    @Override
    public List<ReturnStatus> getAll() {
        return null;
    }
}
