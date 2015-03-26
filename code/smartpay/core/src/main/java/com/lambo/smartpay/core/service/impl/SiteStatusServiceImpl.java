package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.service.SiteStatusService;
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
@Service("siteStatusService")
public class SiteStatusServiceImpl implements SiteStatusService {

    private static final Logger logger = LoggerFactory.getLogger(SiteStatusServiceImpl.class);
    @Autowired
    private SiteStatusDao siteStatusDao;

    /**
     * Find SiteStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public SiteStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return siteStatusDao.findByName(name);
    }

    /**
     * Find SiteStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public SiteStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return siteStatusDao.findByCode(code);
    }

    /**
     * Create a new SiteStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param siteStatus
     * @return
     */
    @Transactional
    @Override
    public SiteStatus create(SiteStatus siteStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (siteStatus == null) {
            throw new MissingRequiredFieldException("SiteStatus is null.");
        }
        if (StringUtils.isBlank(siteStatus.getName()) ||
                StringUtils.isBlank(siteStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (siteStatusDao.findByName(siteStatus.getName()) != null) {
            throw new NotUniqueException("SiteStatus with name " + siteStatus.getName() +
                    " already exists.");
        }
        if (siteStatusDao.findByCode(siteStatus.getCode()) != null) {
            throw new NotUniqueException("SiteStatus with code " + siteStatus.getName() +
                    " already exists.");
        }
        siteStatus.setActive(true);
        // pass all checks, create the object
        return siteStatusDao.create(siteStatus);
    }

    /**
     * Get SiteStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public SiteStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (siteStatusDao.get(id) == null) {
            throw new NoSuchEntityException("SiteStatus with id " + id +
                    " does not exist.");
        }
        return siteStatusDao.get(id);
    }

    /**
     * Update an existing SiteStatus.
     * Must check unique fields if are unique.
     *
     * @param siteStatus
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public SiteStatus update(SiteStatus siteStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (siteStatus == null) {
            throw new MissingRequiredFieldException("SiteStatus is null.");
        }
        if (siteStatus.getId() == null) {
            throw new MissingRequiredFieldException("SiteStatus id is null.");
        }
        if (StringUtils.isBlank(siteStatus.getName()) ||
                StringUtils.isBlank(siteStatus.getCode())) {
            throw new MissingRequiredFieldException("SiteStatus name or code is blank.");
        }
        // checking uniqueness
        SiteStatus namedSiteStatus = siteStatusDao.findByName(siteStatus.getName());
        if (namedSiteStatus != null &&
                !namedSiteStatus.getId().equals(siteStatus.getId())) {
            throw new NotUniqueException("SiteStatus with name " + siteStatus.getName() +
                    " already exists.");
        }
        SiteStatus codedSiteStatus = siteStatusDao.findByCode(siteStatus.getCode());
        if (codedSiteStatus != null &&
                !codedSiteStatus.getId().equals(siteStatus.getId())) {
            throw new NotUniqueException("SiteStatus with code " + siteStatus.getCode() +
                    " already exists.");
        }
        return siteStatusDao.update(siteStatus);
    }

    /**
     * Delete an existing SiteStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public SiteStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        SiteStatus siteStatus = siteStatusDao.get(id);
        if (siteStatus == null) {
            throw new NoSuchEntityException("SiteStatus with id " + id +
                    " does not exist.");
        }
        siteStatusDao.delete(id);
        return siteStatus;
    }

    @Override
    public List<SiteStatus> getAll() {
        return siteStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return siteStatusDao.countAll();
    }
}
