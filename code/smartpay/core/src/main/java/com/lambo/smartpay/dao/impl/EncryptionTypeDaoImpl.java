package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.EncryptionTypeDao;
import com.lambo.smartpay.model.EncryptionType;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for EncryptionType.
 * Created by swang on 2/26/2015.
 */
@Repository("encryptionDao")
public class EncryptionTypeDaoImpl extends LookupGenericDaoImpl<EncryptionType, Long>
        implements EncryptionTypeDao {
}
