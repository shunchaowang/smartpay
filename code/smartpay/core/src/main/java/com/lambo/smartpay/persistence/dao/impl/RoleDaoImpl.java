package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.RoleDao;
import com.lambo.smartpay.persistence.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("roleDao")
public class RoleDaoImpl extends LookupGenericDaoImpl<Role, Long>
        implements RoleDao {
}
