package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.User;

/**
 * There are 3 built-in roles in system, admin for the administration of the system, they can do
 * anything;
 * merchant admin can do the administration of his own merchant;
 * merchant operator can only do partial operation on his own merchant.
 * Created by swang on 3/10/2015.
 */
public interface UserService extends GenericQueryService<User, Long> {

    /**
     * Find user by the unique username.
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByEmail(String email);
}
