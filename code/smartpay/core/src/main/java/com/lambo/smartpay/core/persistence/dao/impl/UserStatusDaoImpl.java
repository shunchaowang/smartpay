package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.UserStatusDao;
import com.lambo.smartpay.core.persistence.entity.UserStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("userStatusDao")
public class UserStatusDaoImpl extends GenericLookupDaoImpl<UserStatus, Long>
        implements UserStatusDao {
}
