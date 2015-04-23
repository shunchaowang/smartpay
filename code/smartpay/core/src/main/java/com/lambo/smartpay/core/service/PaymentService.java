package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.Payment;

/**
 * Created by swang on 3/25/2015.
 */
public interface PaymentService extends GenericDateQueryService<Payment, Long> {

    Payment initiatePaymentClaim(Payment payment);

    Payment processPaymentClaim(Payment payment);

    Payment resolvePaymentClaim(Payment payment);
}
