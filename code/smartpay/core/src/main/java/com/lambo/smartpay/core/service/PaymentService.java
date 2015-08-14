package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.util.ResourceProperties;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by swang on 3/25/2015.
 */
public interface PaymentService extends GenericDateQueryService<Payment, Long> {

    Payment initiatePaymentClaim(Payment payment);

    Payment processPaymentClaim(Payment payment);

    Payment resolvePaymentClaim(Payment payment);

    Payment approvePaymentClaim(Payment payment);

    Payment withdrawPayment(Payment payment, Withdrawal withdrawal);

    Payment unwithdrawPayment(Payment payment);

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return number of result
     */
    Long countByWithdrawalCriteria(Payment payment, Set<PaymentStatus> paymentStatuses,
                                   Date rangeStart, Date rangeEnd);

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return list of result
     */
    List<Payment> findByWithdrawalCriteria(Payment payment,
                                           Set<PaymentStatus> paymentStatuses,
                                           Date rangeStart, Date rangeEnd);

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param search          filter
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return number of result
     */
    Long countByAdvanceCriteria(Payment payment, String search, Set<PaymentStatus> paymentStatuses,
                                Date rangeStart, Date rangeEnd);

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param search          filter
     * @param start           offset
     * @param length          total records
     * @param order           order by
     * @param orderDir        order direction
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return list of result
     */
    List<Payment> findByAdvanceCriteria(Payment payment, String search, Integer start,
                                        Integer length,
                                        String order, ResourceProperties.JpaOrderDir orderDir,
                                        Set<PaymentStatus> paymentStatuses,
                                        Date rangeStart, Date rangeEnd);
}
