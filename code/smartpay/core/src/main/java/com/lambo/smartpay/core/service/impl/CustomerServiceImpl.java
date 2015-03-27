package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CustomerDao;
import com.lambo.smartpay.core.persistence.entity.Customer;
import com.lambo.smartpay.core.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swang on 3/27/2015.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer create(Customer customer)
            throws MissingRequiredFieldException, NotUniqueException {
        return customerDao.create(customer);
    }

    @Override
    public Customer get(Long id) throws NoSuchEntityException {
        return customerDao.get(id);
    }

    @Override
    public Customer update(Customer customer)
            throws MissingRequiredFieldException, NotUniqueException {
        return customerDao.update(customer);
    }

    @Override
    public Customer delete(Long id) throws NoSuchEntityException {
        Customer customer = customerDao.get(id);
        customerDao.delete(id);
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerDao.getAll();
    }

    @Override
    public Long countAll() {
        return customerDao.countAll();
    }
}
