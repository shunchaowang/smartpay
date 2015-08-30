package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.WithdrawalStatusDao;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("withdrawalStatusDao")
public class WithdrawalStatusDaoImpl extends GenericLookupDaoImpl<WithdrawalStatus, Long>
        implements WithdrawalStatusDao {
}
