package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.UserStatusDao;
import com.lambo.smartpay.persistence.entity.UserStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("userStatusDao")
public class UserStatusDaoImpl extends LookupGenericDaoImpl<UserStatus, Long>
        implements UserStatusDao {
}
