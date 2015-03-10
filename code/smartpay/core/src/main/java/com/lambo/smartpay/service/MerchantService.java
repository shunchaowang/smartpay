package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.Merchant;

/**
 * Created by swang on 3/10/2015.
 */
public interface MerchantService extends GenericQueryService<Merchant, Long> {

    Merchant findByName(String name);
}
