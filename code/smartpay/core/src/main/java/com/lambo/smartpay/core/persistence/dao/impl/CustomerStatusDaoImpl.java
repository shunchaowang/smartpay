package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CustomerStatusDao;
import com.lambo.smartpay.core.persistence.entity.CustomerStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao Impl for CustomerStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("customerStatusDao")
public class CustomerStatusDaoImpl extends GenericLookupDaoImpl<CustomerStatus, Long>
        implements CustomerStatusDao {
}
