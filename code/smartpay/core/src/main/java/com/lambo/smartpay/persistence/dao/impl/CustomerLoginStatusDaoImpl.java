package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerLoginStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for CustomerLoginStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerLoginStatusDao")
public class CustomerLoginStatusDaoImpl extends LookupGenericDaoImpl<CustomerLoginStatus, Long>
        implements CustomerLoginStatusDao {
}
