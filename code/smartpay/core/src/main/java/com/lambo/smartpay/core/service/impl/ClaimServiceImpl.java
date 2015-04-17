package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.ClaimDao;
import com.lambo.smartpay.core.persistence.entity.Claim;
import com.lambo.smartpay.core.service.ClaimService;
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
 * Created by swang on 4/17/2015.
 */
@Service("claimService")
public class ClaimServiceImpl extends GenericDateQueryServiceImpl<Claim, Long>
        implements ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimServiceImpl.class);

    @Autowired
    private ClaimDao claimDao;

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param claim            contains all criteria for equals, like name equals xx and active
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
    public Long countByCriteria(Claim claim, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return claimDao.countByCriteria(claim, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param claim            contains all criteria for equals, like name equals xx and active
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
    public List<Claim> findByCriteria(Claim claim, String search, Integer start, Integer length,
                                      String order, ResourceProperties.JpaOrderDir orderDir,
                                      Date createdTimeStart, Date createdTimeEnd) {
        return claimDao.findByCriteria(claim, search, start, length,
                order, orderDir, createdTimeStart, createdTimeEnd);
    }

    @Override
    @Transactional
    public Claim create(Claim claim) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (claim == null) {
            throw new MissingRequiredFieldException("Claim is null.");
        }
        if (claim.getPayment() == null) {
            throw new MissingRequiredFieldException("Claim payment is null.");
        }
        claim.setCreatedTime(date);
        claim.setActive(true);
        return claimDao.create(claim);
    }

    @Override
    public Claim get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Claim claim = claimDao.get(id);
        if (claim == null) {
            throw new NoSuchEntityException("No claim " + id);
        }
        return claim;
    }

    @Override
    @Transactional
    public Claim update(Claim claim) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (claim == null) {
            throw new MissingRequiredFieldException("Claim is null.");
        }
        if (claim.getId() == null) {
            throw new MissingRequiredFieldException("Claim id is null.");
        }
        if (claim.getPayment() == null) {
            throw new MissingRequiredFieldException("Claim payment is null.");
        }
        claim.setUpdatedTime(date);
        return claimDao.update(claim);
    }

    @Override
    @Transactional
    public Claim delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Claim claim = claimDao.get(id);
        if (claim == null) {
            throw new NoSuchEntityException("No claim " + id);
        }
        claimDao.delete(id);
        return claim;
    }

    @Override
    public List<Claim> getAll() {
        return claimDao.getAll();
    }

    @Override
    public Long countAll() {
        return claimDao.countAll();
    }
}
