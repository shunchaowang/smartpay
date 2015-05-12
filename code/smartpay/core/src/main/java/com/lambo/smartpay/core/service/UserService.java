package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.User;

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
    //todo tbr
    User findByUsername(String username);

    /**
     * Find user by unique combination of merchantIdentity and username.
     *
     * @param merchantIdentity unique identity of merchant
     * @param username         username of user
     * @return User if found, null if not
     */
    User findByMerchantIdentityAndUsername(String merchantIdentity, String username);

    User findByEmail(String email);
}
