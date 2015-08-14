package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.PaymentDao;
import com.lambo.smartpay.core.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.service.PaymentService;
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
    @Transactional
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
    @Transactional
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
         @Transactional
         public Payment resolvePaymentClaim(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentStatus paymentStatus = paymentStatusDao.findByCode(ResourceProperties
                .PAYMENT_STATUS_CLAIM_RESOLVED_CODE);
        payment.setPaymentStatus(paymentStatus);
        return paymentDao.update(payment);
    }

    @Override
    @Transactional
    public Payment approvePaymentClaim(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentStatus paymentStatus = paymentStatusDao.findByCode(ResourceProperties
                .PAYMENT_STATUS_CLAIM_SUCCESSED_CODE);
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

    @Override
    @Transactional
    public Payment withdrawPayment(Payment payment, Withdrawal withdrawal) {
        if (payment == null) {
            return null;
        }
        if (withdrawal == null || withdrawal.getId() == null) {
            return null;
        }
        payment.setWithdrawal(withdrawal);
        return paymentDao.update(payment);
    }

    @Transactional
    @Override
    public Payment unwithdrawPayment(Payment payment) {
        if (payment == null) {
            return null;
        }
        payment.setWithdrawal(null);
        return paymentDao.update(payment);
    }

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return number of result
     */
    @Override
    public Long countByWithdrawalCriteria(Payment payment,
                                          Set<PaymentStatus> paymentStatuses,
                                          Date rangeStart, Date rangeEnd) {

        CriteriaBuilder builder = paymentDao.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Payment> root = query.from(Payment.class);

        Predicate predicate = null;

        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
        }

        if (!isBlank(paymentStatuses)) {
            Predicate inPredicate = inPredicate(root, paymentStatuses);
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

        TypedQuery<Long> typedQuery = paymentDao.createCountQuery(query);

        return paymentDao.countAllByCriteria(typedQuery);
    }

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return list of result
     */
    @Override
    public List<Payment> findByWithdrawalCriteria(Payment payment,
                                                  Set<PaymentStatus> paymentStatuses,
                                                  Date rangeStart, Date rangeEnd) {
        CriteriaBuilder builder = paymentDao.getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
        Root<Payment> root = query.from(Payment.class);

        Predicate predicate = null;

        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
        }

        if (!isBlank(paymentStatuses)) {
            Predicate inPredicate = inPredicate(root, paymentStatuses);
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

        TypedQuery<Payment> typedQuery = paymentDao.createQuery(query);

        return paymentDao.findAllByCriteria(typedQuery);
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

    /**
     * @param payment         contains criteria of active, merchant, withdrawal
     * @param search          filter
     * @param paymentStatuses collection of payment status
     * @param rangeStart      start date
     * @param rangeEnd        end date
     * @return number of result
     */
    @Override
    public Long countByAdvanceCriteria(Payment payment, String search,
                                       Set<PaymentStatus> paymentStatuses, Date rangeStart,
                                       Date rangeEnd) {

        CriteriaBuilder builder = paymentDao.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Payment> root = query.from(Payment.class);

        Predicate predicate = null;

        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
        }

        if (!isBlank(paymentStatuses)) {
            Predicate inPredicate = inPredicate(root, paymentStatuses);
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

        TypedQuery<Long> typedQuery = paymentDao.createCountQuery(query);

        return paymentDao.countAllByCriteria(typedQuery);
    }

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
    @Override
    public List<Payment> findByAdvanceCriteria(Payment payment, String search, Integer start,
                                               Integer length, String order,
                                               ResourceProperties.JpaOrderDir orderDir,
                                               Set<PaymentStatus> paymentStatuses, Date rangeStart,
                                               Date rangeEnd) {

        CriteriaBuilder builder = paymentDao.getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
        Root<Payment> root = query.from(Payment.class);
        query.select(root);

        Predicate predicate = null;
        // if customer is not null equal predicate needs to be generated
        if (!isBlank(payment)) {
            predicate = equalPredicate(builder, root, payment);
        }

        if (!isBlank(paymentStatuses)) {
            Predicate inPredicate = inPredicate(root, paymentStatuses);
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

        TypedQuery<Payment> typedQuery = paymentDao.createQuery(query);
        // pagination
        if (start != null) {
            typedQuery.setFirstResult(start);
        }
        if (length != null) {
            typedQuery.setMaxResults(length);
        }

        logger.debug("findByCriteria query is " + typedQuery);

        return paymentDao.findAllByCriteria(typedQuery);
    }

    private Boolean isBlank(Payment payment) {
        return payment == null || payment.getActive() == null && payment.getOrder() == null
                && payment.getWithdrawal() == null;
    }

    private Boolean isBlank(Set<PaymentStatus> paymentStatuses) {
        return paymentStatuses == null || paymentStatuses.isEmpty();
    }

    private Predicate inPredicate(Root<Payment> root, Set<PaymentStatus> paymentStatuses) {

        Predicate predicate = null;

        // check Payment Status
        if (paymentStatuses != null) {
            List<Long> paymentStatusIds = new ArrayList<>();
            for (PaymentStatus paymentStatus : paymentStatuses) {
                paymentStatusIds.add(paymentStatus.getId());
            }
            Expression<Long> paymentStatusIdExp = root.join("paymentStatus").<Long>get("id");
            predicate = paymentStatusIdExp.in(paymentStatusIds);
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
    private Predicate likePredicate(CriteriaBuilder builder, Root<Payment> root, String
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
    private Order orderBy(CriteriaBuilder builder, Root<Payment> root,
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

    private Predicate equalPredicate(CriteriaBuilder builder, Root<Payment> root,
                                     Payment payment) {
        Predicate predicate = null;

        // check if active is set
        if (payment.getActive() != null) {
            predicate = builder.equal(root.<Boolean>get("active"),
                    builder.literal(payment.getActive()));
        }

        // check merchant
        if (payment.getOrder() != null && payment.getOrder().getSite() != null
                && payment.getOrder().getSite().getMerchant() != null
                && payment.getOrder().getSite().getMerchant().getId() != null) {
            Predicate merchantPredicate = builder.equal(root.join("order")
                            .join("site").join("merchant").<Long>get("id"),
                    builder.literal(payment.getOrder().getSite()
                            .getMerchant().getId()));
            if (predicate == null) {
                predicate = merchantPredicate;
            } else {
                predicate = builder.and(predicate, merchantPredicate);
            }
        }

        // check withdrawal
        Predicate withdrawalPredicate = null;
        if (payment.getWithdrawal() != null) {
            if (payment.getWithdrawal().getId() != null) {
                withdrawalPredicate = builder.equal(root.join("withdrawal")
                        .<Long>get("id"), builder.literal(payment.getWithdrawal().getId()));
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

    private Predicate rangePredicate(CriteriaBuilder builder, Root<Payment> root,
                                     Date rangeStart, Date rangeEnd) {
        return builder.between(root.<Date>get("createdTime"),
                builder.literal(rangeStart), builder.literal(rangeEnd));
    }

}
