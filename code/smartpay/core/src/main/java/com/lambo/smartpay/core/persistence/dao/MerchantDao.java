package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.Merchant;

/**
 * Created by swang on 2/19/2015.
 */
public interface MerchantDao extends GenericQueryDao<Merchant, Long> {

    /**
     * Find merchant by the unique identity.
     *
     * @param identity identity of the merchant.
     * @return return the object if found, null if not found.
     */
    Merchant findByIdentity(String identity);
}
