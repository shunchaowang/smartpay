package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.model.CustomerLoginStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for CustomerLoginStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerLoginStatusDao")
public class CustomerLoginStatusDaoImpl extends LookupGenericDaoImpl<CustomerLoginStatus, Long>
        implements CustomerLoginStatusDao {
}
