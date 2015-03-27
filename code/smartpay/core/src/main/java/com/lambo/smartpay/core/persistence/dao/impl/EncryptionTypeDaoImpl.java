package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for EncryptionType.
 * Created by swang on 2/26/2015.
 */
@Repository("encryptionTypeDao")
public class EncryptionTypeDaoImpl extends GenericLookupDaoImpl<EncryptionType, Long>
        implements EncryptionTypeDao {
}
