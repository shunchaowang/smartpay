package com.lambo.smartpay.manage.config;

import com.lambo.smartpay.core.persistence.entity.Permission;
import com.lambo.smartpay.core.persistence.entity.Role;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements spring security UserDetails to support authentication.
 * Created by swang on 3/12/2015.
 */
public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    public SecurityUser(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setEmail(user.getEmail());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setFirstName(user.getFirstName());
            this.setLastName(user.getLastName());
            this.setMerchant(user.getMerchant());
            this.setActive(user.getActive());
            this.setUserStatus(user.getUserStatus());

            // set user's permissions
            this.setPermissions(new HashSet<Permission>());
            if (user.getPermissions() != null) {
                this.getPermissions().addAll(user.getPermissions());
            }
            // we don't need to add user's roles to the permission,
            // we are going with user-permission based access control,
            // role is used as a mark
            // add user's roles' permission to permissions
//            if (user.getRoles() != null) {
//                this.setRoles(user.getRoles());
//                for (Role role : user.getRoles()) {
//                    this.getPermissions().addAll(role.getPermissions());
//                }
//            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<Role> roles = this.getRoles();

        if (roles != null) {
            for (Role role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.getUserStatus().getCode().equals(ResourceProperties.USER_STATUS_FROZEN_CODE)) {
            return false;
        }
        return true;
    }
}
