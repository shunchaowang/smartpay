package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl class for CredentialType.
 * Created by swang on 2/26/2015.
 */
@Repository("credentialTypeDao")
public class CredentialTypeDaoImpl extends GenericLookupDaoImpl<CredentialType, Long>
        implements CredentialTypeDao {
}
