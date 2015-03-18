package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.AccountTypeDao;
import com.lambo.smartpay.persistence.entity.AccountType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for AccountType.
 * Created by swang on 2/26/2015.
 */
@Repository("accountTypeDao")
public class AccountTypeDaoImpl extends GenericLookupDaoImpl<AccountType, Long>
        implements AccountTypeDao {
}
