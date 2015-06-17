package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Permission;
import com.lambo.smartpay.core.persistence.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by swang on 6/16/2015.
 */
public class UserPermissionCommand {

    private Long userId;

    private List<String> permissions;

    public UserPermissionCommand() {
        permissions = new ArrayList<>();
    }

    public UserPermissionCommand(User user) {
        userId = user.getId();
        permissions = new ArrayList<>();
        Set<Permission> userPermissions = user.getPermissions();
        if (userPermissions != null && !userPermissions.isEmpty()) {
            for (Permission permission : userPermissions) {
                permissions.add(permission.getName());
            }
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
