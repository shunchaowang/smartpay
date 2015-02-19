package com.lambo.smartpay.dao;

import com.lambo.smartpay.model.MerchantStatus;

/**
 * Created by swang on 2/17/2015.
 */
public interface MerchantStatusDao extends GenericDao<MerchantStatus, Long> {

    MerchantStatus findByName(String name);

}
