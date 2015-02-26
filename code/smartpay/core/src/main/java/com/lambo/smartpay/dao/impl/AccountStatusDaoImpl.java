package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.AccountStatusDao;
import com.lambo.smartpay.model.AccountStatus;
import org.springframework.stereotype.Repository;

/**
 * Integration test for AccountStatusDaoImpl.
 * Created by swang on 2/25/2015.
 */
@Repository("accountStatusDao")
public class AccountStatusDaoImpl extends LookupGenericDaoImpl<AccountStatus, Long>
        implements AccountStatusDao {

}
