package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.core.persistence.dao.WithdrawalStatusDao;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.core.service.WithdrawalStatusService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chensf on 8/1/2015.
 */
@Service("WithdrawalStatusService")
public class WithdrawalStatusServiceImpl implements WithdrawalStatusService {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalStatusServiceImpl.class);
    @Autowired
    private WithdrawalStatusDao withdrawalStatusDao;

    /**
     * Find WithdrawalStatus by name.
     *
     * @param name
     * @return
     * @throws NoSuchEntityException
     */
    @Override
    public WithdrawalStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return withdrawalStatusDao.findByName(name);
    }

    /**
     * Find WithdrawalStatus by code.
     *
     * @param code
     * @return
     * @throws NoSuchEntityException
     */
    @Override
    public WithdrawalStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return withdrawalStatusDao.findByCode(code);
    }

    /**
     * Create a new WithdrawalStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param withdrawalStatus
     * @return
     */
    @Transactional
    @Override
    public WithdrawalStatus create(WithdrawalStatus withdrawalStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (withdrawalStatus == null) {
            throw new MissingRequiredFieldException("WithdrawalStatus is null.");
        }
        if (StringUtils.isBlank(withdrawalStatus.getName()) ||
                StringUtils.isBlank(withdrawalStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (withdrawalStatusDao.findByName(withdrawalStatus.getName()) != null) {
            throw new NotUniqueException("WithdrawalStatus with name " + withdrawalStatus.getName() +
                    " already exists.");
        }
        if (withdrawalStatusDao.findByCode(withdrawalStatus.getCode()) != null) {
            throw new NotUniqueException("WithdrawalStatus with code " + withdrawalStatus.getName() +
                    " already exists.");
        }
        withdrawalStatus.setActive(true);
        // pass all checks, create the object
        return withdrawalStatusDao.create(withdrawalStatus);
    }

    /**
     * Get WithdrawalStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public WithdrawalStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (withdrawalStatusDao.get(id) == null) {
            throw new NoSuchEntityException("WithdrawalStatus with id " + id +
                    " does not exist.");
        }
        return withdrawalStatusDao.get(id);
    }

    /**
     * Update an existing WithdrawalStatus.
     * Must check unique fields if are unique.
     *
     * @param withdrawalStatus
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public WithdrawalStatus update(WithdrawalStatus withdrawalStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (withdrawalStatus == null) {
            throw new MissingRequiredFieldException("WithdrawalStatus is null.");
        }
        if (withdrawalStatus.getId() == null) {
            throw new MissingRequiredFieldException("WithdrawalStatus id is null.");
        }
        if (StringUtils.isBlank(withdrawalStatus.getName()) ||
                StringUtils.isBlank(withdrawalStatus.getCode())) {
            throw new MissingRequiredFieldException("WithdrawalStatus name or code is blank.");
        }
        // checking uniqueness
        WithdrawalStatus namedOrderStatus = withdrawalStatusDao.findByName(withdrawalStatus.getName());
        if (namedOrderStatus != null &&
                !namedOrderStatus.getId().equals(withdrawalStatus.getId())) {
            throw new NotUniqueException("WithdrawalStatus with name " + withdrawalStatus.getName() +
                    " already exists.");
        }
        WithdrawalStatus codedOrderStatus = withdrawalStatusDao.findByCode(withdrawalStatus.getCode());
        if (codedOrderStatus != null &&
                !codedOrderStatus.getId().equals(withdrawalStatus.getId())) {
            throw new NotUniqueException("WithdrawalStatus with code " + withdrawalStatus.getCode() +
                    " already exists.");
        }
        return withdrawalStatusDao.update(withdrawalStatus);
    }

    /**
     * Delete an existing WithdrawalStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public WithdrawalStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.get(id);
        if (withdrawalStatus == null) {
            throw new NoSuchEntityException("WithdrawalStatus with id " + id +
                    " does not exist.");
        }
        withdrawalStatusDao.delete(id);
        return withdrawalStatus;
    }

    @Override
    public List<WithdrawalStatus> getAll() {
        return withdrawalStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return withdrawalStatusDao.countAll();
    }
}
