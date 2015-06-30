package com.lambo.smartpay.ecs.config;

import com.lambo.smartpay.core.persistence.entity.Permission;
import com.lambo.smartpay.ecs.web.controller.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by swang on 6/29/2015.
 */
@Component("customPermissionEvaluator")
public class CustomPermissionEvaluatorImpl implements CustomPermissionEvaluator {
    private static final Logger logger =
            LoggerFactory.getLogger(CustomPermissionEvaluatorImpl.class);

    //    @Resource
//    private CustomUserDetailsService customUserDetailsService;

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object target, Object permission) {
        logger.warn("evaluate permission: " + permission);
        SecurityUser user = getLogin(authentication);
        Permission permissionObject = getPermission(permission);
        if (user == null || permissionObject == null) {
            return false;
        }
        return hasPermission(user, permissionObject);
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        return false;
    }

    private Boolean hasPermission(SecurityUser user, Permission permission) {

        if (user.getPermissions().contains(permission)) {
            return true;
        }
        return false;
    }

    private SecurityUser getLogin(Authentication authentication) {
        return UserResource.getAuthenticatedUser(authentication);
    }

    private Permission getPermission(Object permission) {
        return UserResource.getPermission((String) permission);
    }
}
