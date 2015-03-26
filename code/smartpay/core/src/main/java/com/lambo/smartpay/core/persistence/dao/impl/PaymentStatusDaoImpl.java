package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("paymentStatusDao")
public class PaymentStatusDaoImpl extends GenericLookupDaoImpl<PaymentStatus, Long>
        implements PaymentStatusDao {
}
