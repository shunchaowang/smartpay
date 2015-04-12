package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.Refund;

/**
 * Created by swang on 4/11/2015.
 */
public interface RefundService extends GenericDateQueryService<Refund, Long> {

    Refund findByBankTransactionNumber(String bankTransactionNumber);
}
