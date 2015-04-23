package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.PaymentDao;
import com.lambo.smartpay.core.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/25/2015.
 */
@Service("paymentService")
public class PaymentServiceImpl extends GenericDateQueryServiceImpl<Payment, Long>
        implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private PaymentStatusDao paymentStatusDao;

    @Override
    public Payment initiatePaymentClaim(Payment payment) {

        if (payment == null) {
            return null;
        }
        PaymentStatus paymentStatus = paymentStatusDao.findByCode(ResourceProperties
                .PAYMENT_STATUS_CLAIM_PENDING_CODE);
        payment.setPaymentStatus(paymentStatus);
        return paymentDao.update(payment);
    }

    @Override
    public Payment processPaymentClaim(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentStatus paymentStatus = paymentStatusDao.findByCode(ResourceProperties
                .PAYMENT_STATUS_CLAIM_IN_PROCESS_CODE);
        payment.setPaymentStatus(paymentStatus);
        return paymentDao.update(payment);
    }

    @Override
    public Payment resolvePaymentClaim(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentStatus paymentStatus = paymentStatusDao.findByCode(ResourceProperties
                .PAYMENT_STATUS_CLAIM_RESOLVED_CODE);
        payment.setPaymentStatus(paymentStatus);
        return paymentDao.update(payment);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param payment          contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     *                         it means no criteria on exact equals if t is null.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Payment payment, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return paymentDao.countByCriteria(payment, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param payment          contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     *                         it means no criteria on exact equals if t is null.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     * @param start            first position of the result.
     * @param length           max record of the result.
     * @param order            order by field, default is id.
     * @param orderDir         order direction on the order field. default is DESC.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return
     */
    @Override
    public List<Payment> findByCriteria(Payment payment, String search, Integer start,
                                        Integer length, String order,
                                        ResourceProperties.JpaOrderDir orderDir,
                                        Date createdTimeStart, Date createdTimeEnd) {
        return paymentDao.findByCriteria(payment, search, start, length,
                order, orderDir, createdTimeStart, createdTimeEnd);
    }

    @Transactional
    @Override
    public Payment create(Payment payment)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (payment == null) {
            throw new MissingRequiredFieldException("Payment is null.");
        }
        if (StringUtils.isBlank(payment.getBankTransactionNumber())) {
            throw new MissingRequiredFieldException("Transaction number is blank.");
        }
        if (payment.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (StringUtils.isBlank(payment.getBankReturnCode())) {
            throw new MissingRequiredFieldException("Bank return code is blank.");
        }
        if (StringUtils.isBlank(payment.getBankAccountNumber())) {
            throw new MissingRequiredFieldException("Bank account number is blank.");
        }
        if (payment.getBankAccountExpDate() == null) {
            throw new MissingRequiredFieldException("Bank account exp date is null.");
        }
        if (StringUtils.isBlank(payment.getBillFirstName())) {
            throw new MissingRequiredFieldException("Bill first name is blank.");
        }
        if (StringUtils.isBlank(payment.getBillLastName())) {
            throw new MissingRequiredFieldException("Bill last name is blank.");
        }
        if (StringUtils.isBlank(payment.getBillAddress1())) {
            throw new MissingRequiredFieldException("Bill address is blank.");
        }
        if (StringUtils.isBlank(payment.getBillCity())) {
            throw new MissingRequiredFieldException("Bill city is blank.");
        }
        if (StringUtils.isBlank(payment.getBillState())) {
            throw new MissingRequiredFieldException("Bill state is blank.");
        }
        if (StringUtils.isBlank(payment.getBillCountry())) {
            throw new MissingRequiredFieldException("Bill country is blank.");
        }
        if (StringUtils.isBlank(payment.getBillZipCode())) {
            throw new MissingRequiredFieldException("Bill zip code is blank.");
        }
        if (payment.getPaymentStatus() == null) {
            throw new MissingRequiredFieldException("Payment status is null.");
        }
        if (payment.getPaymentType() == null) {
            throw new MissingRequiredFieldException("Payment type is null.");
        }
        if (payment.getOrder() == null) {
            throw new MissingRequiredFieldException("Payment order is null.");
        }
        if (payment.getCurrency() == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }

        payment.setActive(true);
        payment.setCreatedTime(date);

        return paymentDao.create(payment);
    }

    @Override
    public Payment get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Payment payment = paymentDao.get(id);
        if (payment == null) {
            throw new NoSuchEntityException("Payment is null.");
        }
        return payment;
    }

    @Transactional
    @Override
    public Payment update(Payment payment)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (payment == null) {
            throw new MissingRequiredFieldException("Payment is null.");
        }
        if (payment.getId() == null) {
            throw new MissingRequiredFieldException("Payment id is null.");
        }
        if (StringUtils.isBlank(payment.getBankTransactionNumber())) {
            throw new MissingRequiredFieldException("Transaction number is blank.");
        }
        if (payment.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (StringUtils.isBlank(payment.getBankReturnCode())) {
            throw new MissingRequiredFieldException("Bank return code is blank.");
        }
        if (StringUtils.isBlank(payment.getBankAccountNumber())) {
            throw new MissingRequiredFieldException("Bank account number is blank.");
        }
        if (payment.getBankAccountExpDate() == null) {
            throw new MissingRequiredFieldException("Bank account exp date is null.");
        }
        if (StringUtils.isBlank(payment.getBillFirstName())) {
            throw new MissingRequiredFieldException("Bill first name is blank.");
        }
        if (StringUtils.isBlank(payment.getBillLastName())) {
            throw new MissingRequiredFieldException("Bill last name is blank.");
        }
        if (StringUtils.isBlank(payment.getBillAddress1())) {
            throw new MissingRequiredFieldException("Bill address is blank.");
        }
        if (StringUtils.isBlank(payment.getBillCity())) {
            throw new MissingRequiredFieldException("Bill city is blank.");
        }
        if (StringUtils.isBlank(payment.getBillState())) {
            throw new MissingRequiredFieldException("Bill state is blank.");
        }
        if (StringUtils.isBlank(payment.getBillCountry())) {
            throw new MissingRequiredFieldException("Bill country is blank.");
        }
        if (StringUtils.isBlank(payment.getBillZipCode())) {
            throw new MissingRequiredFieldException("Bill zip code is blank.");
        }
        if (payment.getPaymentStatus() == null) {
            throw new MissingRequiredFieldException("Payment status is null.");
        }
        if (payment.getPaymentType() == null) {
            throw new MissingRequiredFieldException("Payment type is null.");
        }
        if (payment.getOrder() == null) {
            throw new MissingRequiredFieldException("Payment order is null.");
        }
        if (payment.getCurrency() == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }

        payment.setUpdatedTime(date);

        return paymentDao.update(payment);
    }

    @Transactional
    @Override
    public Payment delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Payment payment = paymentDao.get(id);
        if (payment == null) {
            throw new NoSuchEntityException("Payment is null.");
        }
        paymentDao.delete(id);
        return payment;
    }

    @Override
    public List<Payment> getAll() {
        return paymentDao.getAll();
    }

    @Override
    public Long countAll() {
        return paymentDao.countAll();
    }
}
