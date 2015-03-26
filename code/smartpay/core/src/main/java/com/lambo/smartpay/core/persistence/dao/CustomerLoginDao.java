package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.CustomerLogin;

/**
 * Created by swang on 3/6/2015.
 */
public interface CustomerLoginDao extends GenericQueryDao<CustomerLogin, Long> {
    CustomerLogin findByLoginEmail(String loginEmail);
}
