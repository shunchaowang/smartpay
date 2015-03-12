package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import com.lambo.smartpay.service.EncryptionTypeService;
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
@Service("encryptionTypeService")
public class EncryptionTypeServiceImpl implements EncryptionTypeService {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionTypeServiceImpl.class);
    @Autowired
    private EncryptionTypeDao encryptionTypeDao;

    /**
     * Find EncryptionType by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public EncryptionType findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return encryptionTypeDao.findByName(name);
    }

    /**
     * Find EncryptionType by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public EncryptionType findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return encryptionTypeDao.findByCode(code);
    }

    /**
     * Create a new EncryptionType.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param encryptionType
     * @return
     */
    @Transactional
    @Override
    public EncryptionType create(EncryptionType encryptionType) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (encryptionType == null) {
            throw new MissingRequiredFieldException("EncryptionType is null.");
        }
        if (StringUtils.isBlank(encryptionType.getName()) ||
                StringUtils.isBlank(encryptionType.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (encryptionTypeDao.findByName(encryptionType.getName()) != null) {
            throw new NotUniqueException("EncryptionType with name " + encryptionType.getName() +
                    " already exists.");
        }
        if (encryptionTypeDao.findByCode(encryptionType.getCode()) != null) {
            throw new NotUniqueException("EncryptionType with code " + encryptionType.getName() +
                    " already exists.");
        }
        encryptionType.setActive(true);
        // pass all checks, create the object
        return encryptionTypeDao.create(encryptionType);
    }

    /**
     * Get EncryptionType by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public EncryptionType get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (encryptionTypeDao.get(id) == null) {
            throw new NoSuchEntityException("EncryptionType with id " + id +
                    " does not exist.");
        }
        return encryptionTypeDao.get(id);
    }

    /**
     * Update an existing EncryptionType.
     * Must check unique fields if are unique.
     *
     * @param encryptionType
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public EncryptionType update(EncryptionType encryptionType) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (encryptionType == null) {
            throw new MissingRequiredFieldException("EncryptionType is null.");
        }
        if (encryptionType.getId() == null) {
            throw new MissingRequiredFieldException("EncryptionType id is null.");
        }
        if (StringUtils.isBlank(encryptionType.getName()) ||
                StringUtils.isBlank(encryptionType.getCode())) {
            throw new MissingRequiredFieldException("EncryptionType name or code is blank.");
        }
        // checking uniqueness
        EncryptionType namedEncryptionType = encryptionTypeDao.findByName(encryptionType.getName());
        if (namedEncryptionType != null &&
                !namedEncryptionType.getId().equals(encryptionType.getId())) {
            throw new NotUniqueException("EncryptionType with name " + encryptionType.getName() +
                    " already exists.");
        }
        EncryptionType codedEncryptionType = encryptionTypeDao.findByCode(encryptionType.getCode());
        if (codedEncryptionType != null &&
                !codedEncryptionType.getId().equals(encryptionType.getId())) {
            throw new NotUniqueException("EncryptionType with code " + encryptionType.getCode() +
                    " already exists.");
        }
        return encryptionTypeDao.update(encryptionType);
    }

    /**
     * Delete an existing EncryptionType.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public EncryptionType delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        EncryptionType encryptionType = encryptionTypeDao.get(id);
        if (encryptionType == null) {
            throw new NoSuchEntityException("EncryptionType with id " + id +
                    " does not exist.");
        }
        encryptionTypeDao.delete(id);
        return encryptionType;
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
    public List<EncryptionType> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceProperties.JpaOrderDir
            orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param encryptionType contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(EncryptionType encryptionType) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param encryptionType contains criteria if the field is not null or empty.
     * @param start
     * @param length         @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<EncryptionType> findByAdvanceSearch(EncryptionType encryptionType, Integer start,
                                                    Integer length) {
        return null;
    }
}
