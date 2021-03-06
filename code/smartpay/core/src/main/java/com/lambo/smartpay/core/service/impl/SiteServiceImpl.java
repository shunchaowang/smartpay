package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.SiteDao;
import com.lambo.smartpay.core.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Linly on 3/14/2015.
 * Modified by linly on 3/15/2015.
 */
@Service("siteService")
public class SiteServiceImpl extends GenericQueryServiceImpl<Site, Long> implements SiteService {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private SiteDao siteDao;
    @Autowired
    private SiteStatusDao siteStatusDao;

    @Override
    public Site findByUrl(String url) {
        if (StringUtils.isBlank(url)) {
            logger.debug("Url is blank.");
            return null;
        }
        return siteDao.findByUrl(url);
    }

    @Override
    public Site findByReturnUrl(String returnUrl) {
        if (StringUtils.isBlank(returnUrl)) {
            logger.debug("Url is blank.");
            return null;
        }
        return siteDao.findByReturnUrl(returnUrl);
    }

    @Override
    public Site findByIdentity(String identity) {
        if (StringUtils.isBlank(identity)) {
            logger.debug("Identity is blank.");
            return null;
        }
        return siteDao.findByIdentity(identity);
    }

    /**
     * Finally we decide to put all timestamp in service layer.
     *
     * @param site
     * @return
     * @throws MissingRequiredFieldException
     * @throws NotUniqueException
     */
    @Transactional
    @Override
    public Site create(Site site) throws MissingRequiredFieldException, NotUniqueException {
        if (site == null) {
            throw new MissingRequiredFieldException("Site is null.");
        }
        if (StringUtils.isBlank(site.getName())) {
            throw new MissingRequiredFieldException("Site name is blank.");
        }
        if (StringUtils.isBlank(site.getIdentity())) {
            throw new MissingRequiredFieldException("Site identity is blank.");
        }
        if (siteDao.findByIdentity(site.getIdentity()) != null) {
            throw new NotUniqueException("Site with identity " + site.getIdentity()
                    + " already exists.");
        }
        if (StringUtils.isBlank(site.getUrl())) {
            throw new MissingRequiredFieldException("Site URL is blank.");
        }
        if (StringUtils.isBlank(site.getReturnUrl())) {
            throw new MissingRequiredFieldException("Site return URL is blank.");
        }
        if (siteDao.findByIdentity(site.getIdentity()) != null) {
            throw new NotUniqueException("Site with identity " + site.getIdentity()
                    + " already exists.");
        }
        if (siteDao.findByUrl(site.getUrl()) != null) {
            throw new NotUniqueException("Site with url " + site.getUrl()
                    + " already exists.");
        }
        if (siteDao.findByReturnUrl(site.getReturnUrl()) != null) {
            throw new NotUniqueException("Site with return url " + site.getReturnUrl()
                    + " already exists.");
        }
        if (site.getSiteStatus() == null) {
            throw new MissingRequiredFieldException("Site status is null.");
        }
        if (site.getMerchant() == null) {
            throw new MissingRequiredFieldException("Site Merchant is null.");
        }

        site.setCreatedTime(Calendar.getInstance().getTime());
        site.setActive(true);
        return siteDao.create(site);
    }

    @Override
    public Site get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (siteDao.get(id) == null) {
            throw new NoSuchEntityException("Site with id " + id + " does not exist.");
        }
        return siteDao.get(id);
    }

    /**
     * Update a site.
     * Sitename is not allowed to change.
     *
     * @param site
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public Site update(Site site) throws MissingRequiredFieldException, NotUniqueException {
        if (site == null) {
            throw new MissingRequiredFieldException("Site is null.");
        }
        if (site.getId() == null) {
            throw new MissingRequiredFieldException("Site id is null.");
        }
        if (StringUtils.isBlank(site.getName())) {
            throw new MissingRequiredFieldException("Site name is blank.");
        }
        if (StringUtils.isBlank(site.getUrl())) {
            throw new MissingRequiredFieldException("Site URL is blank.");
        }
        if (StringUtils.isBlank(site.getReturnUrl())) {
            throw new MissingRequiredFieldException("Site return URL is blank.");
        }
        if (site.getActive() == null) {
            throw new MissingRequiredFieldException("Site active is null.");
        }
        if (site.getSiteStatus() == null) {
            throw new MissingRequiredFieldException("Site status is null.");
        }
        if (site.getMerchant() == null) {
            throw new MissingRequiredFieldException("Site merchant is null.");
        }

        // check uniqueness on identity
        Site currentSite = siteDao.get(site.getId());
        if (!site.getIdentity().equals(currentSite.getIdentity())) {
            throw new MissingRequiredFieldException("Site identity cannot be changed.");
        }

        // if change site url or return url
        if (!site.getUrl().equals(currentSite.getUrl())) {
            if (siteDao.findByUrl(site.getUrl()) != null) {
                throw new NotUniqueException("Site url " + site.getUrl() + " already exists.");
            }
        }
        if (!site.getReturnUrl().equals(currentSite.getReturnUrl())) {
            if (siteDao.findByReturnUrl(site.getReturnUrl()) != null) {
                throw new NotUniqueException("Site return url " + site.getReturnUrl() + " already" +
                        " exists.");
            }
        }

        // set updated time if not set
        site.setUpdatedTime(Calendar.getInstance().getTime());

        return siteDao.update(site);
    }

    @Transactional
    @Override
    public Site delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Site with id " + id + " does not exist.");
        }
        siteDao.delete(id);
        return site;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param site   contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Site site, String search) {

        return siteDao.countByCriteria(site, search);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param site     contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Site> findByCriteria(Site site, String search, Integer start, Integer length,
                                     String order,
                                     ResourceProperties.JpaOrderDir orderDir) {

        return siteDao.findByCriteria(site, search, start, length, order, orderDir);
    }

    @Override
    public List<Site> getAll() {
        return siteDao.getAll();
    }

    @Override
    public Long countAll() {
        return siteDao.countAll();
    }

    @Transactional
    @Override
    public Site freezeSite(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Site with id " + id +
                    " does not exist.");
        }
        SiteStatus siteStatus = siteStatusDao.findByCode(ResourceProperties
                .SITE_STATUS_FROZEN_CODE);
        site.setSiteStatus(siteStatus);
        site = siteDao.update(site);
        return site;
    }

    @Transactional
    @Override
    public Site unfreezeSite(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Site with id " + id +
                    " does not exist.");
        }
        SiteStatus siteStatus = siteStatusDao.findByCode(ResourceProperties
                .SITE_STATUS_APPROVED_CODE);
        site.setSiteStatus(siteStatus);
        site = siteDao.update(site);
        return site;
    }

    @Transactional
    @Override
    public Site approveSite(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Site with id " + id +
                    " does not exist.");
        }
        SiteStatus siteStatus = siteStatusDao.findByCode(ResourceProperties
                .SITE_STATUS_APPROVED_CODE);
        site.setSiteStatus(siteStatus);
        site = siteDao.update(site);
        return site;
    }

    @Transactional
    @Override
    public Site declineSite(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        SiteStatus siteStatus = siteStatusDao.findByCode(ResourceProperties
                .SITE_STATUS_DECLINED_CODE);
        site.setSiteStatus(siteStatus);
        site = siteDao.update(site);
        return site;
    }

    @Override
    public Boolean canOperate(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Site site = siteDao.get(id);
        if (site == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        SiteStatus siteStatus = siteStatusDao.findByCode(ResourceProperties
                .SITE_STATUS_APPROVED_CODE);
        return site.getSiteStatus().equals(siteStatus);
    }
}
