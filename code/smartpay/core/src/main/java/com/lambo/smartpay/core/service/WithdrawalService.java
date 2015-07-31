package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.Withdrawal;

/**
 * Created by swang on 3/25/2015.
 */
public interface WithdrawalService extends GenericDateQueryService<Withdrawal, Long> {

    Withdrawal requestWithdrawal(Withdrawal withdrawal);

    Withdrawal approveWithdrawal(Withdrawal withdrawal);

    Withdrawal declineWithdrawal(Withdrawal withdrawal);
}
