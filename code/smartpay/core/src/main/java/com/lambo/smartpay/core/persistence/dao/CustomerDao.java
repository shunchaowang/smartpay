package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.Customer;

/**
 * Created by swang on 3/6/2015.
 */
public interface CustomerDao extends GenericQueryDao<Customer, Long> {
    Customer findByEmail(String email);
}
