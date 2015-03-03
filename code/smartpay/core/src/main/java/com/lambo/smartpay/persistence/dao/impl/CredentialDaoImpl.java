package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CredentialDao;
import com.lambo.smartpay.persistence.entity.Credential;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/2/2015.
 */
@Repository("credentialDao")
public class CredentialDaoImpl extends GenericDaoImpl<Credential, Long>
        implements CredentialDao {
}
