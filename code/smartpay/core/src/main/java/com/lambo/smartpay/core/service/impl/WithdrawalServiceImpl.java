package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.WithdrawalDao;
import com.lambo.smartpay.core.persistence.dao.WithdrawalStatusDao;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;
import com.lambo.smartpay.core.service.WithdrawalService;
import com.lambo.smartpay.core.util.ResourceProperties;
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
@Service("withdrawalService")
public class WithdrawalServiceImpl extends GenericDateQueryServiceImpl<Withdrawal, Long>
        implements WithdrawalService {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalServiceImpl.class);
    @Autowired
    private WithdrawalDao withdrawalDao;
    @Autowired
    private WithdrawalStatusDao withdrawalStatusDao;

    @Override
    @Transactional
    public Withdrawal requestWithdrawal(Withdrawal withdrawal) throws MissingRequiredFieldException, NotUniqueException {

        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_PENDING_CODE);
        withdrawal.setWithdrawalStatus(withdrawalStatus);
        return this.create(withdrawal);
    }

    @Override
    @Transactional
    public Withdrawal requestDepositWithdrawal(Withdrawal withdrawal) throws MissingRequiredFieldException, NotUniqueException {

        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_DEPOSIT_APPROVED_CODE);
        withdrawal.setWithdrawalStatus(withdrawalStatus);
        return this.create(withdrawal);
    }

    @Override
    @Transactional
    public Withdrawal approveWithdrawal(Withdrawal withdrawal) {
        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_APPROVED_CODE);
        withdrawal.setWithdrawalStatus(withdrawalStatus);
        return withdrawalDao.update(withdrawal);
    }

    @Override
    @Transactional
    public Withdrawal approvedepositWithdrawal(Withdrawal withdrawal) throws MissingRequiredFieldException, NotUniqueException {
        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_DEPOSIT_APPROVED_CODE);
        withdrawal.setWithdrawalStatus(withdrawalStatus);
        return this.update(withdrawal);
    }
    @Override
    @Transactional
    public Withdrawal declineWithdrawal(Withdrawal withdrawal) {
        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_DECLINED_CODE);
        withdrawal.setWithdrawalStatus(withdrawalStatus);
        return withdrawalDao.update(withdrawal);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param withdrawal       contains all criteria for equals, like name equals xx and active
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
    public Long countByCriteria(Withdrawal withdrawal, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return withdrawalDao.countByCriteria(withdrawal, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param withdrawal       contains all criteria for equals, like name equals xx and active
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
    public List<Withdrawal> findByCriteria(Withdrawal withdrawal, String search, Integer start,
                                           Integer length, String order,
                                           ResourceProperties.JpaOrderDir orderDir,
                                           Date createdTimeStart, Date createdTimeEnd) {
        return withdrawalDao.findByCriteria(withdrawal, search, start, length,
                order, orderDir, createdTimeStart, createdTimeEnd);
    }

    @Transactional
    @Override
    public Withdrawal create(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (withdrawal == null) {
            throw new MissingRequiredFieldException("Withdrawal is null.");
        }
        if (withdrawal.getBalance() == null) {
            throw new MissingRequiredFieldException("Balance is null.");
        }
        if (withdrawal.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (withdrawal.getMerchant() == null) {
            throw new MissingRequiredFieldException("Withdrawal merchant is null.");
        }
        if (withdrawal.getRequestedBy() == null) {
            throw new MissingRequiredFieldException("Withdrawal requested user is null.");
        }
        if (withdrawal.getWithdrawalStatus() == null) {
            throw new MissingRequiredFieldException("Withdrawal status is null.");
        }

        withdrawal.setActive(true);
        withdrawal.setCreatedTime(date);

        return withdrawalDao.create(withdrawal);
    }

    @Override
    public Withdrawal get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Withdrawal withdrawal = withdrawalDao.get(id);
        if (withdrawal == null) {
            throw new NoSuchEntityException("Withdrawal is null.");
        }
        return withdrawal;
    }

    @Transactional
    @Override
    public Withdrawal update(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (withdrawal == null) {
            throw new MissingRequiredFieldException("Withdrawal is null.");
        }
        if (withdrawal.getId() == null) {
            throw new MissingRequiredFieldException("Withdrawal id is null.");
        }
        if (withdrawal.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (withdrawal.getWithdrawalStatus() == null) {
            throw new MissingRequiredFieldException("Withdrawal status is null.");
        }

        withdrawal.setUpdatedTime(date);

        return withdrawalDao.update(withdrawal);
    }

    @Transactional
    @Override
    public Withdrawal delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Withdrawal withdrawal = withdrawalDao.get(id);
        if (withdrawal == null) {
            throw new NoSuchEntityException("Withdrawal is null.");
        }
        withdrawalDao.delete(id);
        return withdrawal;
    }

    @Override
    public List<Withdrawal> getAll() {
        return withdrawalDao.getAll();
    }

    @Override
    public Long countAll() {
        return withdrawalDao.countAll();
    }
}
