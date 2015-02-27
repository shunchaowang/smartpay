package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.PaymentStatusDao;
import com.lambo.smartpay.model.PaymentStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("paymentStatusDao")
public class PaymentStatusDaoImpl extends LookupGenericDaoImpl<PaymentStatus, Long>
        implements PaymentStatusDao {
}
