package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.RefundDao;
import com.lambo.smartpay.core.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.service.RefundService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    @Transactional
    public Refund withdrawRefund(Refund refund, Withdrawal withdrawal) {
        if (refund == null) {
            return null;
        }
        if (withdrawal == null || withdrawal.getId() == null) {
            return null;
        }
        refund.setWithdrawal(withdrawal);
        return refundDao.update(refund);
    }

    @Override
    @Transactional
    public Refund unwithdrawRefund(Refund refund) {
        if (refund == null) {
            return null;
        }
        refund.setWithdrawal(null);
        return refundDao.update(refund);
    }

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return number of result
     */
    @Override
    public Long countByWithdrawalCriteria(Refund refund, Set<RefundStatus> refundStatuses,
                                          Date rangeStart, Date rangeEnd) {

        CriteriaBuilder builder = refundDao.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Refund> root = query.from(Refund.class);

        Predicate predicate = null;

        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
        }

        if (!isBlank(refundStatuses)) {
            Predicate inPredicate = inPredicate(root, refundStatuses);
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

        TypedQuery<Long> typedQuery = refundDao.createCountQuery(query);

        return refundDao.countAllByCriteria(typedQuery);
    }

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return list of result
     */
    @Override
    public List<Refund> findByWithdrawalCriteria(Refund refund, Set<RefundStatus> refundStatuses,
                                                 Date rangeStart, Date rangeEnd) {

        CriteriaBuilder builder = refundDao.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);

        Predicate predicate = null;

        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
        }

        if (!isBlank(refundStatuses)) {
            Predicate inPredicate = inPredicate(root, refundStatuses);
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

        TypedQuery<Refund> typedQuery = refundDao.createQuery(query);

        return refundDao.findAllByCriteria(typedQuery);
    }

    /**
     * @param refund         contains criteria of active, merchant, withdrawal
     * @param search         filter
     * @param refundStatuses collection of refund status
     * @param rangeStart     start date
     * @param rangeEnd       end date
     * @return number of result
     */
    @Override
    public Long countByAdvanceCriteria(Refund refund, String search,
                                       Set<RefundStatus> refundStatuses, Date rangeStart,
                                       Date rangeEnd) {

        CriteriaBuilder builder = refundDao.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Refund> root = query.from(Refund.class);

        Predicate predicate = null;

        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
        }

        if (!isBlank(refundStatuses)) {
            Predicate inPredicate = inPredicate(root, refundStatuses);
            if (predicate == null) {
                predicate = inPredicate;
            } else {
                predicate = builder.and(predicate, inPredicate);
            }
        }

        // if search is not blank like predicate needs to be generated
        if (!StringUtils.isBlank(search)) {
            Predicate likePredicate = likePredicate(builder, root, search);
            if (predicate == null) {
                predicate = likePredicate;
            } else {
                predicate = builder.and(predicate, likePredicate);
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

        TypedQuery<Long> typedQuery = refundDao.createCountQuery(query);

        return refundDao.countAllByCriteria(typedQuery);
    }

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
    @Override
    public List<Refund> findByAdvanceCriteria(Refund refund, String search, Integer start,
                                              Integer length, String order,
                                              ResourceProperties.JpaOrderDir orderDir,
                                              Set<RefundStatus> refundStatuses, Date rangeStart,
                                              Date rangeEnd) {

        CriteriaBuilder builder = refundDao.getCriteriaBuilder();
        CriteriaQuery<Refund> query = builder.createQuery(Refund.class);
        Root<Refund> root = query.from(Refund.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(refund)) {
            predicate = equalPredicate(builder, root, refund);
        }

        if (!isBlank(refundStatuses)) {
            Predicate inPredicate = inPredicate(root, refundStatuses);
            if (predicate == null) {
                predicate = inPredicate;
            } else {
                predicate = builder.and(predicate, inPredicate);
            }
        }

        // if search is not blank like predicate needs to be generated
        if (!StringUtils.isBlank(search)) {
            Predicate likePredicate = likePredicate(builder, root, search);
            if (predicate == null) {
                predicate = likePredicate;
            } else {
                predicate = builder.and(predicate, likePredicate);
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
        if (StringUtils.isBlank(order)) {
            order = "id";
        }
        if (orderDir == null) {
            orderDir = ResourceProperties.JpaOrderDir.DESC;
        }
        query.orderBy(orderBy(builder, root, order, orderDir));

        TypedQuery<Refund> typedQuery = refundDao.createQuery(query);
        // pagination
        if (start != null) {
            typedQuery.setFirstResult(start);
        }
        if (length != null) {
            typedQuery.setMaxResults(length);
        }

        logger.debug("findByCriteria query is " + typedQuery);

        return refundDao.findAllByCriteria(typedQuery);
    }

    private Boolean isBlank(Refund refund) {
        return refund == null || refund.getActive() == null && refund.getOrder() == null
                && refund.getWithdrawal() == null;
    }

    private Boolean isBlank(Set<RefundStatus> refundStatuses) {
        return refundStatuses == null || refundStatuses.isEmpty();
    }

    private Predicate inPredicate(Root<Refund> root, Set<RefundStatus> refundStatuses) {

        Predicate predicate = null;

        // check Refund Status
        if (refundStatuses != null) {
            List<Long> refundStatusIds = new ArrayList<>();
            for (RefundStatus refundStatus : refundStatuses) {
                refundStatusIds.add(refundStatus.getId());
            }
            Expression<Long> refundStatusIdExp = root.join("refundStatus").<Long>get("id");
            predicate = refundStatusIdExp.in(refundStatusIds);
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    /**
     * Formulate JPA or Predicate on primitive fields with like criteria for CriteriaQuery.
     * Supports bankName.
     *
     * @param builder is the JPA CriteriaBuilder.
     * @param root    is the root of the CriteriaQuery.
     * @param search  is the search keyword.
     * @return JPA Predicate used by CriteriaQuery.
     */
    private Predicate likePredicate(CriteriaBuilder builder, Root<Refund> root, String
            search) {

        String likeSearch = "%" + search + "%";

        // get all paths for the query
        Path<String> bankTransactionNumberPath = root.get("bankTransactionNumber");

        // create the predicate expression for all the path
        Predicate bankTransactionNumberPredicate =
                builder.like(bankTransactionNumberPath, likeSearch);


        // create the final Predicate and return
        logger.debug("Formulated jpa predicate is " + bankTransactionNumberPredicate.toString());
        return bankTransactionNumberPredicate;
    }

    /**
     * Formulate JPA Order on primitive field for CriteriaQuery.
     * Supports id, createdTime.
     *
     * @param builder  is the JPA CriteriaBuilder.
     * @param root     is the root of the CriteriaQuery.
     * @param order    is the field name for the order.
     * @param orderDir is the order direction.
     * @return JPA Order for the CriteriaQuery.
     */
    private Order orderBy(CriteriaBuilder builder, Root<Refund> root,
                          String order, ResourceProperties.JpaOrderDir orderDir) {

        // get all supporting paths
        Path<Long> idPath = root.get("id");
        Path<Date> createdTimePath = root.get("createdTime");
        Path<Date> successTimePath = root.get("successTime");

        // create Order instance, default would be ORDER BY id DESC, newest to oldest
        Order orderBy = null;
        switch (orderDir) {
            case ASC:
                switch (order) {
                    case "id":
                        orderBy = builder.asc(idPath);
                        break;
                    case "createdTime":
                        orderBy = builder.asc(createdTimePath);
                        break;
                    case "successTime":
                        orderBy = builder.asc(successTimePath);
                        break;
                    default:
                        orderBy = builder.asc(idPath);
                }
                break;
            case DESC:
                switch (order) {
                    case "id":
                        orderBy = builder.desc(idPath);
                        break;
                    case "createdTime":
                        orderBy = builder.desc(createdTimePath);
                        break;
                    case "successTime":
                        orderBy = builder.desc(successTimePath);
                        break;
                    default:
                        orderBy = builder.desc(idPath);
                }
                break;
            default:
                orderBy = builder.desc(idPath);
                break;
        }

        logger.debug("Formulated order by clause is " + orderBy.toString());
        return orderBy;
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Root<Refund> root,
                                     Refund refund) {
        Predicate predicate = null;

        // check if active is set
        if (refund.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(refund.getActive()));
        }

        // check merchant
        if (refund.getOrder() != null && refund.getOrder().getSite() != null
                && refund.getOrder().getSite().getMerchant() != null
                && refund.getOrder().getSite().getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(root.join("order")
                            .join("site").join("merchant").<Long>get("id"),
                    builder.literal(refund.getOrder().getSite()
                            .getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        // check withdrawal
        Predicate withdrawalPredicate = null;
        if (refund.getWithdrawal() != null) {
            if (refund.getWithdrawal().getId() != null) {
                withdrawalPredicate = builder.equal(root.join("withdrawal")
                        .<Long>get("id"), builder.literal(refund.getWithdrawal().getId()));
            } else {
                withdrawalPredicate = builder.isNotNull(root.get("withdrawal"));
            }
        } else {
            withdrawalPredicate = builder.isNull(root.get("withdrawal"));
        }

        if (predicate == null) {
            predicate = withdrawalPredicate;
        } else {
            predicate = builder.and(predicate, withdrawalPredicate);
        }

        logger.debug("Formulated predicate is " + predicate);
        return predicate;
    }

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Refund> root,
                                     Date rangeStart, Date rangeEnd) {
        return builder.between(root.<Date>get("createdTime"),
                builder.literal(rangeStart), builder.literal(rangeEnd));
    }

}
