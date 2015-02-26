package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.CredentialTypeDao;
import com.lambo.smartpay.model.CredentialType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for CredentialType.
 * Created by swang on 2/26/2015.
 */
@Repository("credentialTypeDao")
public class CredentialTypeDaoImpl extends LookupGenericDaoImpl<CredentialType, Long>
        implements CredentialTypeDao {
}
