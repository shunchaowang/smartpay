package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.EncryptionDao;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/3/2015.
 */
@Repository("encryptionDao")
public class EncryptionDaoImpl extends GenericDaoImpl<Encryption, Long>
        implements EncryptionDao {
}
