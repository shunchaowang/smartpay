package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.MerchantStatus;

/**
 * Created by swang on 2/17/2015.
 */
public interface MerchantStatusService {

    MerchantStatus create(MerchantStatus merchantStatus);

    MerchantStatus get(String name);

    MerchantStatus update(MerchantStatus merchantStatus);

    Boolean delete(MerchantStatus merchantStatus);

}
