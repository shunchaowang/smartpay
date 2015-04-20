package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.RefundDao;
import com.lambo.smartpay.core.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import com.lambo.smartpay.core.service.RefundService;
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
 * Created by swang on 4/11/2015.
 */
@Service("refundService")
public class RefundServiceImpl extends GenericDateQueryServiceImpl<Refund, Long>
        implements RefundService {

    private static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);
    @Autowired
    private RefundDao refundDao;
    @Autowired
    private RefundStatusDao refundStatusDao;

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param refund           contains all criteria for equals, like name equals xx and active
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
    public Long countByCriteria(Refund refund, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return refundDao.countByCriteria(refund, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param refund           contains all criteria for equals, like name equals xx and active
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
    public List<Refund> findByCriteria(Refund refund, String search, Integer start,
                                       Integer length,
                                       String order, ResourceProperties.JpaOrderDir orderDir,
                                       Date createdTimeStart, Date createdTimeEnd) {
        return refundDao.findByCriteria(refund, search,
                start, length, order, orderDir,
                createdTimeStart, createdTimeEnd);
    }

    @Override
    public Refund findByBankTransactionNumber(String bankTransactionNumber) {
        return refundDao.findByBankTransactionNumber(bankTransactionNumber);
    }

    @Override
    @Transactional
    public Refund create(Refund refund)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (refund == null) {
            throw new MissingRequiredFieldException("Refund is null.");
        }

        if (StringUtils.isBlank(refund.getBankAccountNumber())) {
            throw new MissingRequiredFieldException("Bank account number is null.");
        }
        if (StringUtils.isBlank(refund.getBankTransactionNumber())) {
            throw new MissingRequiredFieldException("Bank transaction number is null.");
        }

        if (refund.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (refund.getOrder() == null) {
            throw new MissingRequiredFieldException("Order is null.");
        }
        if (refund.getRefundStatus() == null) {
            throw new MissingRequiredFieldException("Refund status is null.");
        }
        if (refund.getCurrency() == null) {
            throw new MissingRequiredFieldException("Refund currency is null.");
        }

        refund.setBankAccountExpDate(date);
        refund.setCreatedTime(date);
        refund.setActive(true);

        return refundDao.create(refund);
    }

    @Override
    public Refund get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        return refundDao.get(id);
    }

    @Override
    @Transactional
    public Refund approveRefund(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Refund refund = refundDao.get(id);
        if (refund == null) {
            throw new NoSuchEntityException("Refund is null.");
        }
        RefundStatus approvedRefundStatus = refundStatusDao.findByCode(ResourceProperties
                .REFUND_STATUS_APPROVED_CODE);
        refund.setRefundStatus(approvedRefundStatus);
        return refundDao.update(refund);
    }

    @Override
    @Transactional
    public Refund update(Refund refund)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (refund == null) {
            throw new MissingRequiredFieldException("Refund is null.");
        }

        if (StringUtils.isBlank(refund.getBankAccountNumber())) {
            throw new MissingRequiredFieldException("Bank account number is null.");
        }
        if (StringUtils.isBlank(refund.getBankTransactionNumber())) {
            throw new MissingRequiredFieldException("Bank transaction number is null.");
        }

        if (refund.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (refund.getOrder() == null) {
            throw new MissingRequiredFieldException("Order is null.");
        }
        if (refund.getRefundStatus() == null) {
            throw new MissingRequiredFieldException("Refund status is null.");
        }
        if (refund.getCurrency() == null) {
            throw new MissingRequiredFieldException("Refund currency is null.");
        }
        if (refund.getBankAccountExpDate() == null) {
            throw new MissingRequiredFieldException("Bank account exp date is null.");
        }
        if (refund.getCreatedTime() == null) {
            throw new MissingRequiredFieldException("Created date is null.");
        }

        refund.setUpdatedTime(date);

        return refundDao.update(refund);
    }

    @Override
    @Transactional
    public Refund delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Refund refund = refundDao.get(id);
        if (refund == null) {
            throw new NoSuchEntityException("Refund doesn't exist " + id);
        }
        refundDao.delete(id);
        return refund;
    }

    @Override
    public List<Refund> getAll() {
        return refundDao.getAll();
    }

    @Override
    public Long countAll() {
        return refundDao.countAll();
    }
}
