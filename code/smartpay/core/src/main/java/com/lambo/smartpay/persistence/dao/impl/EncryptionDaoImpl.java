package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.EncryptionDao;
import com.lambo.smartpay.persistence.entity.Encryption;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/3/2015.
 */
@Repository("encryptionDao")
public class EncryptionDaoImpl extends GenericDaoImpl<Encryption, Long>
        implements EncryptionDao {
}
