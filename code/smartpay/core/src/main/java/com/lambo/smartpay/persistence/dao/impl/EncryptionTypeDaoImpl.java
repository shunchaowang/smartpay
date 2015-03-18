package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for EncryptionType.
 * Created by swang on 2/26/2015.
 */
@Repository("encryptionTypeDao")
public class EncryptionTypeDaoImpl extends GenericLookupDaoImpl<EncryptionType, Long>
        implements EncryptionTypeDao {
}
