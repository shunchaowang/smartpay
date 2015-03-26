package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.RoleDao;
import com.lambo.smartpay.core.persistence.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("roleDao")
public class RoleDaoImpl extends GenericLookupDaoImpl<Role, Long>
        implements RoleDao {
}
