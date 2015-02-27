package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.PaymentTypeDao;
import com.lambo.smartpay.model.PaymentType;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("paymentTypeDao")
public class PaymentTypeDaoImpl extends LookupGenericDaoImpl<PaymentType, Long>
        implements PaymentTypeDao {
}
