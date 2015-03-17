package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.persistence.entity.PaymentStatus;
import com.lambo.smartpay.service.PaymentStatusService;
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
@Service("paymentStatusService")
public class PaymentStatusServiceImpl implements PaymentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusServiceImpl.class);
    @Autowired
    private PaymentStatusDao paymentStatusDao;

    /**
     * Find PaymentStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return paymentStatusDao.findByName(name);
    }

    /**
     * Find PaymentStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return paymentStatusDao.findByCode(code);
    }

    /**
     * Create a new PaymentStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param paymentStatus
     * @return
     */
    @Transactional
    @Override
    public PaymentStatus create(PaymentStatus paymentStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (paymentStatus == null) {
            throw new MissingRequiredFieldException("PaymentStatus is null.");
        }
        if (StringUtils.isBlank(paymentStatus.getName()) ||
                StringUtils.isBlank(paymentStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (paymentStatusDao.findByName(paymentStatus.getName()) != null) {
            throw new NotUniqueException("PaymentStatus with name " + paymentStatus.getName() +
                    " already exists.");
        }
        if (paymentStatusDao.findByCode(paymentStatus.getCode()) != null) {
            throw new NotUniqueException("PaymentStatus with code " + paymentStatus.getName() +
                    " already exists.");
        }
        paymentStatus.setActive(true);
        // pass all checks, create the object
        return paymentStatusDao.create(paymentStatus);
    }

    /**
     * Get PaymentStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (paymentStatusDao.get(id) == null) {
            throw new NoSuchEntityException("PaymentStatus with id " + id +
                    " does not exist.");
        }
        return paymentStatusDao.get(id);
    }

    /**
     * Update an existing PaymentStatus.
     * Must check unique fields if are unique.
     *
     * @param paymentStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public PaymentStatus update(PaymentStatus paymentStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (paymentStatus == null) {
            throw new MissingRequiredFieldException("PaymentStatus is null.");
        }
        if (paymentStatus.getId() == null) {
            throw new MissingRequiredFieldException("PaymentStatus id is null.");
        }
        if (StringUtils.isBlank(paymentStatus.getName()) ||
                StringUtils.isBlank(paymentStatus.getCode())) {
            throw new MissingRequiredFieldException("PaymentStatus name or code is blank.");
        }
        // checking uniqueness
        PaymentStatus namedPaymentStatus = paymentStatusDao.findByName(paymentStatus.getName());
        if (namedPaymentStatus != null &&
                !namedPaymentStatus.getId().equals(paymentStatus.getId())) {
            throw new NotUniqueException("PaymentStatus with name " + paymentStatus.getName() +
                    " already exists.");
        }
        PaymentStatus codedPaymentStatus = paymentStatusDao.findByCode(paymentStatus.getCode());
        if (codedPaymentStatus != null &&
                !codedPaymentStatus.getId().equals(paymentStatus.getId())) {
            throw new NotUniqueException("PaymentStatus with code " + paymentStatus.getCode() +
                    " already exists.");
        }
        return paymentStatusDao.update(paymentStatus);
    }

    /**
     * Delete an existing PaymentStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public PaymentStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        PaymentStatus paymentStatus = paymentStatusDao.get(id);
        if (paymentStatus == null) {
            throw new NoSuchEntityException("PaymentStatus with id " + id +
                    " does not exist.");
        }
        paymentStatusDao.delete(id);
        return paymentStatus;
    }

    @Override
    public List<PaymentStatus> getAll() {
        return null;
    }
}
