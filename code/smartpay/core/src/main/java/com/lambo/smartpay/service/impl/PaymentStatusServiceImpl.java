package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.persistence.entity.PaymentStatus;
import com.lambo.smartpay.service.PaymentStatusService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service needs to check if the parameters passed in are null or empty.
 * Service also takes care of Transactional management.
 * Created by swang on 3/9/2015.
 */
@Service("paymentStatusService")
public class PaymentStatusServiceImpl implements PaymentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusServiceImpl.class);
    @Autowired
    private PaymentStatusDao paymentStatusDao;

    /**
     * Find PaymentStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return paymentStatusDao.findByName(name);
    }

    /**
     * Find PaymentStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return paymentStatusDao.findByCode(code);
    }

    /**
     * Create a new PaymentStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param paymentStatus
     * @return
     */
    @Transactional
    @Override
    public PaymentStatus create(PaymentStatus paymentStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (paymentStatus == null) {
            throw new MissingRequiredFieldException("PaymentStatus is null.");
        }
        if (StringUtils.isBlank(paymentStatus.getName()) ||
                StringUtils.isBlank(paymentStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (paymentStatusDao.findByName(paymentStatus.getName()) != null) {
            throw new NotUniqueException("PaymentStatus with name " + paymentStatus.getName() +
                    " already exists.");
        }
        if (paymentStatusDao.findByCode(paymentStatus.getCode()) != null) {
            throw new NotUniqueException("PaymentStatus with code " + paymentStatus.getName() +
                    " already exists.");
        }
        paymentStatus.setActive(true);
        // pass all checks, create the object
        return paymentStatusDao.create(paymentStatus);
    }

    /**
     * Get PaymentStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public PaymentStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (paymentStatusDao.get(id) == null) {
            throw new NoSuchEntityException("PaymentStatus with id " + id +
                    " does not exist.");
        }
        return paymentStatusDao.get(id);
    }

    /**
     * Update an existing PaymentStatus.
     * Must check unique fields if are unique.
     *
     * @param paymentStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public PaymentStatus update(PaymentStatus paymentStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (paymentStatus == null) {
            throw new MissingRequiredFieldException("PaymentStatus is null.");
        }
        if (paymentStatus.getId() == null) {
            throw new MissingRequiredFieldException("PaymentStatus id is null.");
        }
        if (StringUtils.isBlank(paymentStatus.getName()) ||
                StringUtils.isBlank(paymentStatus.getCode())) {
            throw new MissingRequiredFieldException("PaymentStatus name or code is blank.");
        }
        // checking uniqueness
        PaymentStatus namedPaymentStatus = paymentStatusDao.findByName(paymentStatus.getName());
        if (namedPaymentStatus != null &&
                !namedPaymentStatus.getId().equals(paymentStatus.getId())) {
            throw new NotUniqueException("PaymentStatus with name " + paymentStatus.getName() +
                    " already exists.");
        }
        PaymentStatus codedPaymentStatus = paymentStatusDao.findByCode(paymentStatus.getCode());
        if (codedPaymentStatus != null &&
                !codedPaymentStatus.getId().equals(paymentStatus.getId())) {
            throw new NotUniqueException("PaymentStatus with code " + paymentStatus.getCode() +
                    " already exists.");
        }
        return paymentStatusDao.update(paymentStatus);
    }

    /**
     * Delete an existing PaymentStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public PaymentStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        PaymentStatus paymentStatus = paymentStatusDao.get(id);
        if (paymentStatus == null) {
            throw new NoSuchEntityException("PaymentStatus with id " + id +
                    " does not exist.");
        }
        paymentStatusDao.delete(id);
        return paymentStatus;
    }

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        return null;
    }

    /**
     * Find all T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    @Override
    public List<PaymentStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                 String order, ResourceProperties.JpaOrderDir
            orderDir,
                                                 Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param paymentStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(PaymentStatus paymentStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param paymentStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length        @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<PaymentStatus> findByAdvanceSearch(PaymentStatus paymentStatus, Integer start,
                                                   Integer length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param paymentStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param search        instance wildcard search keyword, like name likes %xx%, etc.
     *                      it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(PaymentStatus paymentStatus, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param paymentStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(PaymentStatus paymentStatus) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * <p/>
     * it means no criteria on exact equals if t is null.
     *
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param paymentStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param search        instance wildcard search keyword, like name likes %xx%, etc.
     *                      it means no criteria with wildcard search if search is null.
     * @param start         first position of the result.
     * @param length        max record of the result.
     * @param order         order by field, default is id.
     * @param orderDir      order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<PaymentStatus> findByCriteria(PaymentStatus paymentStatus, String search, Integer
            start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param paymentStatus contains all criteria for equals, like name equals xx and active equals
     *                      true, etc.
     *                      it means no criteria on exact equals if t is null.
     * @param start         first position of the result.
     * @param length        max record of the result.
     * @param order         order by field, default is id.
     * @param orderDir      order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<PaymentStatus> findByCriteria(PaymentStatus paymentStatus, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<PaymentStatus> findByCriteria(String search, Integer start, Integer length,
                                              String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    @Override
    public List<PaymentStatus> getAll() {
        return null;
    }
}
