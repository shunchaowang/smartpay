package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by swang on 3/25/2015.
 */
public interface WithdrawalService extends GenericDateQueryService<Withdrawal, Long> {

    Withdrawal requestWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException;

    Withdrawal approveWithdrawal(Withdrawal withdrawal);

    Withdrawal declineWithdrawal(Withdrawal withdrawal);

    Withdrawal requestDepositWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException;

    ;

    Withdrawal approvedepositWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException;

    /**
     * @param withdrawal         contains criteria of active, merchant
     * @param withdrawalStatuses collection of withdrawal status
     * @param rangeStart         start date
     * @param rangeEnd           end date
     * @return number of result
     */
    Long countByAdvanceCriteria(Withdrawal withdrawal, Set<WithdrawalStatus> withdrawalStatuses,
                                Date rangeStart, Date rangeEnd);

    /**
     * @param withdrawal         contains criteria of active, merchant
     * @param withdrawalStatuses collection of withdrawal status
     * @param rangeStart         start date
     * @param rangeEnd           end date
     * @return list of result
     */
    List<Withdrawal> findByAdvanceCriteria(Withdrawal withdrawal,
                                           Set<WithdrawalStatus> withdrawalStatuses,
                                           Date rangeStart, Date rangeEnd);
}
