package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CredentialDao;
import com.lambo.smartpay.core.persistence.entity.Credential;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/2/2015.
 */
@Repository("credentialDao")
public class CredentialDaoImpl extends GenericDaoImpl<Credential, Long>
        implements CredentialDao {
}
