package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.service.MerchantStatusService;
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
@Service("merchantStatusService")
public class MerchantStatusServiceImpl implements MerchantStatusService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantStatusServiceImpl.class);
    @Autowired
    private MerchantStatusDao merchantStatusDao;

    /**
     * Find MerchantStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public MerchantStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return merchantStatusDao.findByName(name);
    }

    /**
     * Find MerchantStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public MerchantStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return merchantStatusDao.findByCode(code);
    }

    /**
     * Create a new MerchantStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param merchantStatus
     * @return
     */
    @Transactional
    @Override
    public MerchantStatus create(MerchantStatus merchantStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (merchantStatus == null) {
            throw new MissingRequiredFieldException("MerchantStatus is null.");
        }
        if (StringUtils.isBlank(merchantStatus.getName()) ||
                StringUtils.isBlank(merchantStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (merchantStatusDao.findByName(merchantStatus.getName()) != null) {
            throw new NotUniqueException("MerchantStatus with name " + merchantStatus.getName() +
                    " already exists.");
        }
        if (merchantStatusDao.findByCode(merchantStatus.getCode()) != null) {
            throw new NotUniqueException("MerchantStatus with code " + merchantStatus.getName() +
                    " already exists.");
        }
        merchantStatus.setActive(true);
        // pass all checks, create the object
        return merchantStatusDao.create(merchantStatus);
    }

    /**
     * Get MerchantStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public MerchantStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (merchantStatusDao.get(id) == null) {
            throw new NoSuchEntityException("MerchantStatus with id " + id +
                    " does not exist.");
        }
        return merchantStatusDao.get(id);
    }

    /**
     * Update an existing MerchantStatus.
     * Must check unique fields if are unique.
     *
     * @param merchantStatus
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public MerchantStatus update(MerchantStatus merchantStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (merchantStatus == null) {
            throw new MissingRequiredFieldException("MerchantStatus is null.");
        }
        if (merchantStatus.getId() == null) {
            throw new MissingRequiredFieldException("MerchantStatus id is null.");
        }
        if (StringUtils.isBlank(merchantStatus.getName()) ||
                StringUtils.isBlank(merchantStatus.getCode())) {
            throw new MissingRequiredFieldException("MerchantStatus name or code is blank.");
        }
        // checking uniqueness
        MerchantStatus namedMerchantStatus = merchantStatusDao.findByName(merchantStatus.getName());
        if (namedMerchantStatus != null &&
                !namedMerchantStatus.getId().equals(merchantStatus.getId())) {
            throw new NotUniqueException("MerchantStatus with name " + merchantStatus.getName() +
                    " already exists.");
        }
        MerchantStatus codedMerchantStatus = merchantStatusDao.findByCode(merchantStatus.getCode());
        if (codedMerchantStatus != null &&
                !codedMerchantStatus.getId().equals(merchantStatus.getId())) {
            throw new NotUniqueException("MerchantStatus with code " + merchantStatus.getCode() +
                    " already exists.");
        }
        return merchantStatusDao.update(merchantStatus);
    }

    /**
     * Delete an existing MerchantStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public MerchantStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.get(id);
        if (merchantStatus == null) {
            throw new NoSuchEntityException("MerchantStatus with id " + id +
                    " does not exist.");
        }
        merchantStatusDao.delete(id);
        return merchantStatus;
    }

    @Override
    public List<MerchantStatus> getAll() {
        return merchantStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return merchantStatusDao.countAll();
    }
}
