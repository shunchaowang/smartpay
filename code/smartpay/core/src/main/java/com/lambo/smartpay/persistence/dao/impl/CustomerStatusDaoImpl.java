package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CustomerStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao Impl for CustomerStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerStatusDao")
public class CustomerStatusDaoImpl extends GenericLookupDaoImpl<CustomerStatus, Long>
        implements CustomerStatusDao {
}
