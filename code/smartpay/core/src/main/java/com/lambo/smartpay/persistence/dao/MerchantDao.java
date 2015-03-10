package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Merchant;

/**
 * Created by swang on 2/19/2015.
 */
public interface MerchantDao extends GenericQueryDao<Merchant, Long> {

    /**
     * Find merchant by name.
     *
     * @param name name of the merchant.
     * @return return the object if found, null if not found.
     */
    Merchant findByName(String name);
}
