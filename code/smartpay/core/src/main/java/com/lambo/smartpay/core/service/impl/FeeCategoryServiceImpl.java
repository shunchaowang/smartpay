package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.FeeCategoryDao;
import com.lambo.smartpay.core.persistence.entity.FeeCategory;
import com.lambo.smartpay.core.service.FeeCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public FeeCategory findByCode(String code) throws NoSuchEntityException {
        return null;
    }

    @Override
    public FeeCategory create(FeeCategory feeCategory)
            throws MissingRequiredFieldException, NotUniqueException {
        return null;
    }

    @Override
    public FeeCategory get(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public FeeCategory update(FeeCategory feeCategory)
            throws MissingRequiredFieldException, NotUniqueException {
        return null;
    }

    @Override
    public FeeCategory delete(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public List<FeeCategory> getAll() {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }
}
