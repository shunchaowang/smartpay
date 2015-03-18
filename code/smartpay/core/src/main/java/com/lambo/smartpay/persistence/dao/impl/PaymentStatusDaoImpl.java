package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.persistence.entity.PaymentStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("paymentStatusDao")
public class PaymentStatusDaoImpl extends GenericLookupDaoImpl<PaymentStatus, Long>
        implements PaymentStatusDao {
}
