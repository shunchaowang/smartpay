package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CustomerDao;
import com.lambo.smartpay.core.persistence.entity.Customer;
import com.lambo.smartpay.core.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by swang on 3/27/2015.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDao customerDao;

    /**
     * Find user by the unique email.
     *
     * @param email
     * @return
     */
    @Override
    public Customer findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            logger.debug("Email is blank.");
            return null;
        }
        return customerDao.findByEmail(email);
    }

    @Transactional
    @Override
    public Customer create(Customer customer)
            throws MissingRequiredFieldException, NotUniqueException {

        if (customer == null) {
            throw new MissingRequiredFieldException("User is null.");
        }
        // TODO other checks

        // check uniqueness on username and email
        if (customerDao.findByEmail(customer.getEmail()) != null) {
            throw new NotUniqueException("Customer with email " + customer.getEmail()
                    + " already exists.");
        }

        customer.setCreatedTime(Calendar.getInstance().getTime());
        customer.setActive(true);
        return customerDao.create(customer);
    }

    @Override
    public Customer get(Long id) throws NoSuchEntityException {
        return customerDao.get(id);
    }

    @Transactional
    @Override
    public Customer update(Customer customer)
            throws MissingRequiredFieldException, NotUniqueException {
        customer.setUpdatedTime(Calendar.getInstance().getTime());
        return customerDao.update(customer);
    }

    @Transactional
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
