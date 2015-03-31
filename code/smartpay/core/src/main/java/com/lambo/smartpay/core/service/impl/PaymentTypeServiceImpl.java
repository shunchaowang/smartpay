package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.PaymentTypeDao;
import com.lambo.smartpay.core.persistence.entity.PaymentType;
import com.lambo.smartpay.core.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swang on 3/31/2015.
 */
@Service("paymentTypeService")
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    private PaymentTypeDao paymentTypeDao;

    @Override
    public PaymentType findByName(String name) throws NoSuchEntityException {
        return paymentTypeDao.findByName(name);
    }

    @Override
    public PaymentType findByCode(String code) throws NoSuchEntityException {
        return paymentTypeDao.findByCode(code);
    }

    @Override
    public PaymentType create(PaymentType paymentType)
            throws MissingRequiredFieldException, NotUniqueException {
        return paymentTypeDao.create(paymentType);
    }

    @Override
    public PaymentType get(Long id) throws NoSuchEntityException {
        return paymentTypeDao.get(id);
    }

    @Override
    public PaymentType update(PaymentType paymentType)
            throws MissingRequiredFieldException, NotUniqueException {
        return paymentTypeDao.update(paymentType);
    }

    @Override
    public PaymentType delete(Long id) throws NoSuchEntityException {
        PaymentType paymentType = paymentTypeDao.get(id);
        paymentTypeDao.delete(id);
        return paymentType;
    }

    @Override
    public List<PaymentType> getAll() {
        return paymentTypeDao.getAll();
    }

    @Override
    public Long countAll() {
        return paymentTypeDao.countAll();
    }
}
