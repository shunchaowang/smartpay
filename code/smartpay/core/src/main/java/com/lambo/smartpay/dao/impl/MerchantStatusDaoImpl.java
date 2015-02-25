package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.model.MerchantStatus;
import org.springframework.stereotype.Repository;

/**
 * A wrapper of LookupGenericDaoImpl for MerchantStatus.
 * Created by swang on 2/17/2015.
 */
@Repository("merchantStatusDao")
public class MerchantStatusDaoImpl extends LookupGenericDaoImpl<MerchantStatus, Long>
        implements MerchantStatusDao {

}
