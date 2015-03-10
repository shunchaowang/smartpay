package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.entity.FeeType;
import com.lambo.smartpay.service.FeeTypeService;
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
@Service("feeTypeService")
public class FeeTypeServiceImpl implements FeeTypeService {

    private static final Logger logger = LoggerFactory.getLogger(FeeTypeServiceImpl.class);
    @Autowired
    private FeeTypeDao feeTypeDao;

    /**
     * Find FeeType by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public FeeType findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return feeTypeDao.findByName(name);
    }

    /**
     * Find FeeType by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public FeeType findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return feeTypeDao.findByCode(code);
    }

    /**
     * Create a new FeeType.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param feeType
     * @return
     */
    @Transactional
    @Override
    public FeeType create(FeeType feeType) throws MissingRequiredFieldException,
            NotUniqueException {
        if (feeType == null) {
            throw new MissingRequiredFieldException("FeeType is null.");
        }
        if (StringUtils.isBlank(feeType.getName()) ||
                StringUtils.isBlank(feeType.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (feeTypeDao.findByName(feeType.getName()) != null) {
            throw new NotUniqueException("FeeType with name " + feeType.getName() +
                    " already exists.");
        }
        if (feeTypeDao.findByCode(feeType.getCode()) != null) {
            throw new NotUniqueException("FeeType with code " + feeType.getName() +
                    " already exists.");
        }
        feeType.setActive(true);
        // pass all checks, create the object
        return feeTypeDao.create(feeType);
    }

    /**
     * Get FeeType by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public FeeType get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (feeTypeDao.get(id) == null) {
            throw new NoSuchEntityException("FeeType with id " + id +
                    " does not exist.");
        }
        return feeTypeDao.get(id);
    }

    /**
     * Update an existing FeeType.
     * Must check unique fields if are unique.
     *
     * @param feeType
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public FeeType update(FeeType feeType) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (feeType == null) {
            throw new MissingRequiredFieldException("FeeType is null.");
        }
        if (feeType.getId() == null) {
            throw new MissingRequiredFieldException("FeeType id is null.");
        }
        if (StringUtils.isBlank(feeType.getName()) ||
                StringUtils.isBlank(feeType.getCode())) {
            throw new MissingRequiredFieldException("FeeType name or code is blank.");
        }
        // checking uniqueness
        FeeType namedFeeType = feeTypeDao.findByName(feeType.getName());
        if (namedFeeType != null &&
                !namedFeeType.getId().equals(feeType.getId())) {
            throw new NotUniqueException("FeeType with name " + feeType.getName() +
                    " already exists.");
        }
        FeeType codedFeeType = feeTypeDao.findByCode(feeType.getCode());
        if (codedFeeType != null &&
                !codedFeeType.getId().equals(feeType.getId())) {
            throw new NotUniqueException("FeeType with code " + feeType.getCode() +
                    " already exists.");
        }
        return feeTypeDao.update(feeType);
    }

    /**
     * Delete an existing FeeType.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public FeeType delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        FeeType feeType = feeTypeDao.get(id);
        if (feeType == null) {
            throw new NoSuchEntityException("FeeType with id " + id +
                    " does not exist.");
        }
        feeTypeDao.delete(id);
        return feeType;
    }
}
