package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for CredentialStatus.
 * Created by swang on 2/26/2015.
 */
@Repository("credentialStatusDao")
public class CredentialStatusDaoImpl extends GenericLookupDaoImpl<CredentialStatus, Long>
        implements CredentialStatusDao {
}
