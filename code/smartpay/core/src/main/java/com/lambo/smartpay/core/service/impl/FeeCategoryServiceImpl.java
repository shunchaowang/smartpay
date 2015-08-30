package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.FeeCategoryDao;
import com.lambo.smartpay.core.persistence.entity.FeeCategory;
import com.lambo.smartpay.core.service.FeeCategoryService;
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
@Service("feeCategoryService")
public class FeeCategoryServiceImpl implements FeeCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(FeeCategoryServiceImpl.class);
    @Autowired
    private FeeCategoryDao feeCategoryDao;

    @Override
    public FeeCategory findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return feeCategoryDao.findByName(name);
    }

    @Override
    public FeeCategory findByCode(String code) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        // call dao
        return feeCategoryDao.findByCode(code);
    }

    @Override
    @Transactional
    public FeeCategory create(FeeCategory feeCategory)
            throws MissingRequiredFieldException, NotUniqueException {
        if (feeCategory == null) {
            throw new MissingRequiredFieldException("feeCategory is null.");
        }
        if (StringUtils.isBlank(feeCategory.getName()) ||
                StringUtils.isBlank(feeCategory.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (feeCategoryDao.findByName(feeCategory.getName()) != null) {
            throw new NotUniqueException("feeCategory with name " + feeCategory.getName() +
                    " already exists.");
        }
        if (feeCategoryDao.findByCode(feeCategory.getCode()) != null) {
            throw new NotUniqueException("FeeType with code " + feeCategory.getCode() +
                    " already exists.");
        }
        feeCategory.setActive(true);
        // pass all checks, create the object
        return feeCategoryDao.create(feeCategory);
    }

    @Override
    public FeeCategory get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (feeCategoryDao.get(id) == null) {
            throw new NoSuchEntityException("FeeCategory with id " + id +
                    " does not exist.");
        }
        return feeCategoryDao.get(id);
    }

    @Override
    @Transactional
    public FeeCategory update(FeeCategory feeCategory)
            throws MissingRequiredFieldException, NotUniqueException {
        // checking missing fields
        if (feeCategory == null) {
            throw new MissingRequiredFieldException("FeeCategory is null.");
        }
        if (feeCategory.getId() == null) {
            throw new MissingRequiredFieldException("FeeCategory id is null.");
        }
        if (StringUtils.isBlank(feeCategory.getName()) ||
                StringUtils.isBlank(feeCategory.getCode())) {
            throw new MissingRequiredFieldException("FeeCategory name or code is blank.");
        }
        // checking uniqueness
        FeeCategory namedFeeCategory = feeCategoryDao.findByName(feeCategory.getName());
        if (namedFeeCategory != null &&
                !namedFeeCategory.getId().equals(feeCategory.getId())) {
            throw new NotUniqueException("FeeCategory with name " + feeCategory.getName() +
                    " already exists.");
        }
        FeeCategory codedFeeCategory = feeCategoryDao.findByCode(feeCategory.getCode());
        if (codedFeeCategory != null &&
                !codedFeeCategory.getId().equals(feeCategory.getId())) {
            throw new NotUniqueException("FeeCategory with code " + feeCategory.getCode() +
                    " already exists.");
        }
        return feeCategoryDao.update(feeCategory);
    }

    @Transactional
    @Override
    public FeeCategory delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        FeeCategory feeCategory = feeCategoryDao.get(id);
        if (feeCategory == null) {
            throw new NoSuchEntityException("FeeCategory with id " + id +
                    " does not exist.");
        }
        feeCategoryDao.delete(id);
        return feeCategory;
    }

    @Override
    public List<FeeCategory> getAll() {
        return feeCategoryDao.getAll();
    }

    @Override
    public Long countAll() {
        return feeCategoryDao.countAll();
    }
}
