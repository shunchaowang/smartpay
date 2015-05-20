package com.lambo.smartpay.manage.config;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Permission;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.PermissionService;
import com.lambo.smartpay.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by swang on 5/19/2015.
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    //    @Resource
//    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object target, Object permission) {

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
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User loginUser = userService.findByUsername(username);
            return new SecurityUser(loginUser);
        }
        return null;
    }

    private Permission getPermission(Object permission) {
        if (!(permission instanceof String)) {
            return null;
        }
        Permission permissionObject = null;
        try {
            permissionObject = permissionService.findByName((String) permission);
        } catch (NoSuchEntityException e) {
            logger.debug(e.getMessage());
            return null;
        }
        return permissionObject;
    }
}
