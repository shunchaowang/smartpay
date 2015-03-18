package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.PaymentTypeDao;
import com.lambo.smartpay.persistence.entity.PaymentType;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("paymentTypeDao")
public class PaymentTypeDaoImpl extends GenericLookupDaoImpl<PaymentType, Long>
        implements PaymentTypeDao {
}
