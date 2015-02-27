package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.PermissionDao;
import com.lambo.smartpay.model.Permission;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("permissionDao")
public class PermissionDaoImpl extends LookupGenericDaoImpl<Permission, Long>
        implements PermissionDao {
}
