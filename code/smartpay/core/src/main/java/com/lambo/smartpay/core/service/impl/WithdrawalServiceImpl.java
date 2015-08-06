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

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public Withdrawal requestWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException {

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
    public Withdrawal requestDepositWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException {

        if (withdrawal == null) {
            return null;
        }
        WithdrawalStatus withdrawalStatus = withdrawalStatusDao.findByCode(ResourceProperties
                .WITHDRAWAL_STATUS_DEPOSIT_PENDING_CODE);
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
    public Withdrawal approvedepositWithdrawal(Withdrawal withdrawal)
            throws MissingRequiredFieldException, NotUniqueException {
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

    /**
     * @param withdrawal         contains criteria of active, merchant
     * @param withdrawalStatuses collection of withdrawal status
     * @param rangeStart         start date
     * @param rangeEnd           end date
     * @return number of result
     */
    @Override
    public Long countByAdvanceCriteria(Withdrawal withdrawal,
                                       Set<WithdrawalStatus> withdrawalStatuses, Date rangeStart,
                                       Date rangeEnd) {

        CriteriaBuilder builder = withdrawalDao.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Withdrawal> root = query.from(Withdrawal.class);

        Predicate predicate = null;

        if (!isBlank(withdrawal)) {
            predicate = equalPredicate(builder, root, withdrawal);
        }

        if (!isBlank(withdrawalStatuses)) {
            Predicate inPredicate = inPredicate(root, withdrawalStatuses);
            if (predicate == null) {
                predicate = inPredicate;
            } else {
                predicate = builder.and(predicate, inPredicate);
            }
        }

        if (rangeStart != null && rangeEnd != null) {
            Predicate rangePredicate = rangePredicate(builder, root, rangeStart, rangeEnd);
            if (predicate == null) {
                predicate = rangePredicate;
            } else {
                predicate = builder.and(predicate, rangePredicate);
            }
        }
        if (predicate != null) {
            query.where(predicate);
        }

        TypedQuery<Long> typedQuery = withdrawalDao.createCountQuery(query);

        return withdrawalDao.countAllByCriteria(typedQuery);
    }

    /**
     * @param withdrawal         contains criteria of active, merchant
     * @param withdrawalStatuses collection of withdrawal status
     * @param rangeStart         start date
     * @param rangeEnd           end date
     * @return list of result
     */
    @Override
    public List<Withdrawal> findByAdvanceCriteria(Withdrawal withdrawal,
                                                  Set<WithdrawalStatus> withdrawalStatuses,
                                                  Date rangeStart, Date rangeEnd) {

        CriteriaBuilder builder = withdrawalDao.getCriteriaBuilder();
        CriteriaQuery<Withdrawal> query = builder.createQuery(Withdrawal.class);
        Root<Withdrawal> root = query.from(Withdrawal.class);

        Predicate predicate = null;

        if (!isBlank(withdrawal)) {
            predicate = equalPredicate(builder, root, withdrawal);
        }

        if (!isBlank(withdrawalStatuses)) {
            Predicate inPredicate = inPredicate(root, withdrawalStatuses);
            if (predicate == null) {
                predicate = inPredicate;
            } else {
                predicate = builder.and(predicate, inPredicate);
            }
        }

        if (rangeStart != null && rangeEnd != null) {
            Predicate rangePredicate = rangePredicate(builder, root,
                    rangeStart, rangeEnd);
            if (predicate == null) {
                predicate = rangePredicate;
            } else {
                predicate = builder.and(predicate, rangePredicate);
            }
        }

        if (predicate != null) {
            query.where(predicate);
        }

        Path<Date> createdTimePath = root.get("createdTime");
        Order order = builder.desc(createdTimePath);
        query.orderBy(order);

        TypedQuery<Withdrawal> typedQuery = withdrawalDao.createQuery(query);

        return withdrawalDao.findAllByCriteria(typedQuery);
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

    private Boolean isBlank(Withdrawal withdrawal) {
        return withdrawal == null || withdrawal.getMerchant() == null
                && withdrawal.getActive() == null;
    }

    private Boolean isBlank(Set<WithdrawalStatus> withdrawalStatuses) {
        return withdrawalStatuses == null || withdrawalStatuses.isEmpty();
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Root<Withdrawal> root,
                                     Withdrawal withdrawal) {
        Predicate predicate = null;

        // check if active is set
        if (withdrawal.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(withdrawal.getActive()));
        }

        // check merchant
        if (withdrawal.getMerchant() != null && withdrawal.getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(root.join("merchant").<Long>get("id"),
                    builder.literal(withdrawal.getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate inPredicate(Root<Withdrawal> root, Set<WithdrawalStatus> withdrawalStatuses) {

        Predicate predicate = null;

        // check Withdrawal Status
        if (withdrawalStatuses != null) {
            List<Long> withdrawStatusIds = new ArrayList<>();
            for (WithdrawalStatus withdrawalStatus : withdrawalStatuses) {
                withdrawStatusIds.add(withdrawalStatus.getId());
            }
            Expression<Long> withdrawalStatusIdExp = root.join("withdrawalStatus").<Long>get("id");
            predicate = withdrawalStatusIdExp.in(withdrawStatusIds);
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Withdrawal> root,
                                     Date rangeStart, Date rangeEnd) {
        return builder.between(root.<Date>get("createdTime"),
                builder.literal(rangeStart), builder.literal(rangeEnd));
    }
}
