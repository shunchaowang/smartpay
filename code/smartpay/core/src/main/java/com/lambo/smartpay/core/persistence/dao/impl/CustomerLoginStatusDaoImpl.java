package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.core.persistence.entity.CustomerLoginStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for CustomerLoginStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerLoginStatusDao")
public class CustomerLoginStatusDaoImpl extends GenericLookupDaoImpl<CustomerLoginStatus, Long>
        implements CustomerLoginStatusDao {
}
