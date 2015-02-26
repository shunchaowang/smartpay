package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.CredentialStatusDao;
import com.lambo.smartpay.model.CredentialStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for CredentialStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("credentialStatusDao")
public class CredentialStatusDaoImpl extends LookupGenericDaoImpl<CredentialStatus, Long>
        implements CredentialStatusDao {
}
