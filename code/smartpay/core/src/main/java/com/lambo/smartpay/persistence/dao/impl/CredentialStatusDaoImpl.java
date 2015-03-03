package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for CredentialStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("credentialStatusDao")
public class CredentialStatusDaoImpl extends LookupGenericDaoImpl<CredentialStatus, Long>
        implements CredentialStatusDao {
}
