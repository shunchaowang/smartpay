package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.User;

/**
 * Created by swang on 3/10/2015.
 */
public interface UserService extends GenericQueryService<User, Long> {

    /**
     * Create a merchant admin for the merchant.
     *
     * @param user
     * @return
     */
    User createMerchantAdmin(User user);

    /**
     * Create a merchant operator for the merchant.
     *
     * @param user
     * @return
     */
    User createMerchantOperator(User user);
}
