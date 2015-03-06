package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Customer;

/**
 * Created by swang on 3/6/2015.
 */
public interface CustomerDao extends GenericQueryDao<Customer, Long> {
    Customer findByEmail(String email);
}
