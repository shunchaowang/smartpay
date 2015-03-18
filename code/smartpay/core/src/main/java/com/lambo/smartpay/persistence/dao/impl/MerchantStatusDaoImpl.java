package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
import org.springframework.stereotype.Repository;

/**
 * A wrapper of LookupGenericDaoImpl for MerchantStatus.
 * Created by swang on 2/17/2015.
 */
@Repository("merchantStatusDao")
public class MerchantStatusDaoImpl extends GenericLookupDaoImpl<MerchantStatus, Long>
        implements MerchantStatusDao {

}
