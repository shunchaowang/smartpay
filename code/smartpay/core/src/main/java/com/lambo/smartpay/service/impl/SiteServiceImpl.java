package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.SiteDao;
import com.lambo.smartpay.persistence.entity.Site;
import com.lambo.smartpay.service.SiteService;
import com.lambo.smartpay.util.ResourceProperties;
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
public class SiteServiceImpl implements SiteService {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private SiteDao siteDao;

    /**
     * Find site by the unique name.
     *
     * @param name
     * @return
     */
    @Override
    public Site findByName(String name) {
        if (StringUtils.isBlank(name)) {
            logger.debug("Name is blank.");
            return null;
        }
        return siteDao.findByName(name);
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
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        return siteDao.countByAdHocSearch(search, activeFlag);
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
    public List<Site> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        if (start == null) {
            logger.info("Start is null.");
            return null;
        }
        if (length == null) {
            logger.info("Length is null.");
            return null;
        }

        if (order == null) {
            logger.info("Order is null.");
            return null;
        }
        if (orderDir == null) {
            logger.info("OrderDir is null.");
            return null;
        }
        return siteDao.findByAdHocSearch(search, start, length, order, orderDir, activeFlag);
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param site contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Site site) {
        if (site == null) {
            logger.info("Site is null.");
        }
        return siteDao.countByAdvanceSearch(site);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param site   contains criteria if the field is not null or empty.
     * @param start
     * @param length @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Site> findByAdvanceSearch(Site site, Integer start, Integer length) {
        if (site == null) {
            logger.info("Site is null.");
        }
        if (start == null) {
            logger.info("Start is null.");
        }
        if (length == null) {
            logger.info("Length is null.");
        }
        return siteDao.findByAdvanceSearch(site, start, length);
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
        if (StringUtils.isBlank(site.getUrl())) {
            throw new MissingRequiredFieldException("Site URL is blank.");
        }
        if (site.getActive() == null) {
            throw new MissingRequiredFieldException("Site active is null.");
        }
        if (site.getSiteStatus() == null) {
            throw new MissingRequiredFieldException("Site status is null.");
        }
        if (site.getMerchant() == null) {
            throw new MissingRequiredFieldException("Site Merchant is null.");
        }

        /*
        // check uniqueness on sitename
        if (siteDao.findByName(site.getName()) != null) {
            throw new NotUniqueException("Site with sitename " + site.getName()
                    + " already exists.");
        }
        */
        site.setCreatedTime(Calendar.getInstance().getTime());
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
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
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
        if (site.getCreatedTime() == null) {
            throw new MissingRequiredFieldException("Site created time is null.");
        }
        if (site.getActive() == null) {
            throw new MissingRequiredFieldException("Site active is null.");
        }
        if (site.getSiteStatus() == null) {
            throw new MissingRequiredFieldException("Site status is null.");
        }
        if (site.getMerchant() == null) {
            throw new MissingRequiredFieldException("Site Merchent is null.");
        }

        // check uniqueness on sitename
        Site currentSite = siteDao.get(site.getId());
        if (!site.getName().equals(currentSite.getName())) {
            throw new MissingRequiredFieldException("Site name cannot be changed.");
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


    //TODO newly added methods


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
        /*if (site == null) {
            logger.info("Site is null.");
            return null;
        }*/
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
    public List<Site> findByCriteria(Site site, String search, Integer start, Integer length, String order,
                                  ResourceProperties.JpaOrderDir orderDir) {
        /*if (site == null) {
            logger.info("Site is null.");
            return null;
        }
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        if (start == null) {
            logger.info("Start is null.");
            return null;
        }
        if (length == null) {
            logger.info("Length is null.");
            return null;
        }
        if (order == null) {
            logger.info("Order is null.");
            return null;
        }
        if (orderDir == null) {
            logger.info("OrderDir is null.");
            return null;
        }*/
        return siteDao.findByCriteria(site, search, start, length, order, orderDir);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param site contains all criteria for equals, like name equals xx and active equals
     *             true, etc.
     *             it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Site site) {

        return siteDao.countByCriteria(site, null);
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

        return siteDao.countByCriteria(null, search);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param site     contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Site> findByCriteria(Site site, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return siteDao.findByCriteria(site, null, start, length, order, orderDir);
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
    public List<Site> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return siteDao.findByCriteria(null, search, start, length, order, orderDir);
    }

    @Override
    public List<Site> getAll() {
        return null;
    }
}
