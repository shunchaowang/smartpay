package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.util.ResourceProperties;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by swang on 4/11/2015.
 */
public interface RefundService extends GenericDateQueryService<Refund, Long> {

    Refund findByBankTransactionNumber(String bankTransactionNumber);

    Refund approveRefund(Long id) throws NoSuchEntityException;

    Refund withdrawRefund(Refund refund, Withdrawal withdrawal);

    Refund unwithdrawRefund(Refund refund);

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return number of result
     */
    Long countByWithdrawalCriteria(Refund refund, Set<RefundStatus> refundStatuses,
                                   Date rangeStart, Date rangeEnd);

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return list of result
     */
    List<Refund> findByWithdrawalCriteria(Refund refund,
                                          Set<RefundStatus> refundStatuses,
                                          Date rangeStart, Date rangeEnd);

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param search         filter
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return number of result
     */
    Long countByAdvanceCriteria(Refund refund, String search, Set<RefundStatus> refundStatuses,
                                Date rangeStart, Date rangeEnd);

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param search         filter
     * @param start          offset
     * @param length         total records
     * @param order          order by
     * @param orderDir       order direction
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return list of result
     */
    List<Refund> findByAdvanceCriteria(Refund refund, String search, Integer start,
                                       Integer length,
                                       String order, ResourceProperties.JpaOrderDir orderDir,
                                       Set<RefundStatus> refundStatuses,
                                       Date rangeStart, Date rangeEnd);
}
