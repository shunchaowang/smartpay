package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.AccountStatusDao;
import com.lambo.smartpay.core.persistence.entity.AccountStatus;
import org.springframework.stereotype.Repository;

/**
 * Integration test for AccountStatusDaoImpl.
 * Created by swang on 2/25/2015.
 */
@Repository("accountStatusDao")
public class AccountStatusDaoImpl extends GenericLookupDaoImpl<AccountStatus, Long>
        implements AccountStatusDao {

}
