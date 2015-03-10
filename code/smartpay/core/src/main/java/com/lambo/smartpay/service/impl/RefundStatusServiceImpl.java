package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.persistence.entity.RefundStatus;
import com.lambo.smartpay.service.RefundStatusService;
import com.lambo.smartpay.util.ResourceUtil;
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
@Service("refundStatusService")
public class RefundStatusServiceImpl implements RefundStatusService {

    private static final Logger logger = LoggerFactory.getLogger(RefundStatusServiceImpl.class);
    @Autowired
    private RefundStatusDao refundStatusDao;

    /**
     * Find RefundStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public RefundStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return refundStatusDao.findByName(name);
    }

    /**
     * Find RefundStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public RefundStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return refundStatusDao.findByCode(code);
    }

    /**
     * Create a new RefundStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param refundStatus
     * @return
     */
    @Transactional
    @Override
    public RefundStatus create(RefundStatus refundStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (refundStatus == null) {
            throw new MissingRequiredFieldException("RefundStatus is null.");
        }
        if (StringUtils.isBlank(refundStatus.getName()) ||
                StringUtils.isBlank(refundStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (refundStatusDao.findByName(refundStatus.getName()) != null) {
            throw new NotUniqueException("RefundStatus with name " + refundStatus.getName() +
                    " already exists.");
        }
        if (refundStatusDao.findByCode(refundStatus.getCode()) != null) {
            throw new NotUniqueException("RefundStatus with code " + refundStatus.getName() +
                    " already exists.");
        }
        refundStatus.setActive(true);
        // pass all checks, create the object
        return refundStatusDao.create(refundStatus);
    }

    /**
     * Get RefundStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public RefundStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (refundStatusDao.get(id) == null) {
            throw new NoSuchEntityException("RefundStatus with id " + id +
                    " does not exist.");
        }
        return refundStatusDao.get(id);
    }

    /**
     * Update an existing RefundStatus.
     * Must check unique fields if are unique.
     *
     * @param refundStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public RefundStatus update(RefundStatus refundStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (refundStatus == null) {
            throw new MissingRequiredFieldException("RefundStatus is null.");
        }
        if (refundStatus.getId() == null) {
            throw new MissingRequiredFieldException("RefundStatus id is null.");
        }
        if (StringUtils.isBlank(refundStatus.getName()) ||
                StringUtils.isBlank(refundStatus.getCode())) {
            throw new MissingRequiredFieldException("RefundStatus name or code is blank.");
        }
        // checking uniqueness
        RefundStatus namedRefundStatus = refundStatusDao.findByName(refundStatus.getName());
        if (namedRefundStatus != null &&
                !namedRefundStatus.getId().equals(refundStatus.getId())) {
            throw new NotUniqueException("RefundStatus with name " + refundStatus.getName() +
                    " already exists.");
        }
        RefundStatus codedRefundStatus = refundStatusDao.findByCode(refundStatus.getCode());
        if (codedRefundStatus != null &&
                !codedRefundStatus.getId().equals(refundStatus.getId())) {
            throw new NotUniqueException("RefundStatus with code " + refundStatus.getCode() +
                    " already exists.");
        }
        return refundStatusDao.update(refundStatus);
    }

    /**
     * Delete an existing RefundStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public RefundStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        RefundStatus refundStatus = refundStatusDao.get(id);
        if (refundStatus == null) {
            throw new NoSuchEntityException("RefundStatus with id " + id +
                    " does not exist.");
        }
        refundStatusDao.delete(id);
        return refundStatus;
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
    public List<RefundStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                String order, ResourceUtil.JpaOrderDir orderDir,
                                                Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param refundStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(RefundStatus refundStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param refundStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length       @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<RefundStatus> findByAdvanceSearch(RefundStatus refundStatus, Integer start,
                                                  Integer length) {
        return null;
    }
}
