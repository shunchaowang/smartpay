package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.CustomerStatusDao;
import com.lambo.smartpay.model.CustomerStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao Impl for CustomerStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerStatusDao")
public class CustomerStatusDaoImpl extends LookupGenericDaoImpl<CustomerStatus, Long>
        implements CustomerStatusDao {
}
