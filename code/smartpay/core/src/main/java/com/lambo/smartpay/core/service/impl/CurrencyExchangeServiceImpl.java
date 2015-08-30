package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CurrencyExchangeDao;
import com.lambo.smartpay.core.persistence.entity.CurrencyExchange;
import com.lambo.smartpay.core.service.CurrencyExchangeService;
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
 * Created by swang on 8/20/2015.
 */
@Service("currencyExchangeService")
public class CurrencyExchangeServiceImpl extends GenericQueryServiceImpl<CurrencyExchange, Long>
        implements CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);
    @Autowired
    private CurrencyExchangeDao currencyExchangeDao;

    @Override
    @Transactional
    public CurrencyExchange create(CurrencyExchange currencyExchange)
            throws MissingRequiredFieldException, NotUniqueException {
        if (currencyExchange == null) {
            logger.debug("Currency exchange is null.");
            throw new MissingRequiredFieldException("Currency exchange is null.");
        }
        if (currencyExchange.getAmountFrom() == null) {
            logger.debug("Currency exchange from amount is null.");
            throw new MissingRequiredFieldException("Currency exchange from amount is null.");
        }
        if (currencyExchange.getAmountTo() == null) {
            logger.debug("Currency exchange to amount is null.");
            throw new MissingRequiredFieldException("Currency exchange to amount is null.");
        }
        if (currencyExchange.getCurrencyFrom() == null) {
            logger.debug("Currency exchange currency from is null.");
            throw new MissingRequiredFieldException("Currency exchange from currency is null.");
        }
        if (currencyExchange.getCurrencyTo() == null) {
            logger.debug("Currency exchange currency to is null.");
            throw new MissingRequiredFieldException("Currency exchange currency to is null.");
        }

        Date date = Calendar.getInstance().getTime();
        currencyExchange.setCreatedTime(date);
        currencyExchange.setActive(true);
        return currencyExchangeDao.create(currencyExchange);
    }

    @Override
    public CurrencyExchange get(Long id) throws NoSuchEntityException {
        return currencyExchangeDao.get(id);
    }

    @Override
    @Transactional
    public CurrencyExchange update(CurrencyExchange currencyExchange)
            throws MissingRequiredFieldException, NotUniqueException {
        if (currencyExchange == null) {
            logger.debug("Currency exchange is null.");
            throw new MissingRequiredFieldException("Currency exchange is null.");
        }
        if (currencyExchange.getAmountFrom() == null) {
            logger.debug("Currency exchange from amount is null.");
            throw new MissingRequiredFieldException("Currency exchange from amount is null.");
        }
        if (currencyExchange.getAmountTo() == null) {
            logger.debug("Currency exchange to amount is null.");
            throw new MissingRequiredFieldException("Currency exchange to amount is null.");
        }
        if (currencyExchange.getCurrencyFrom() == null) {
            logger.debug("Currency exchange currency from is null.");
            throw new MissingRequiredFieldException("Currency exchange from currency is null.");
        }
        if (currencyExchange.getCurrencyTo() == null) {
            logger.debug("Currency exchange currency to is null.");
            throw new MissingRequiredFieldException("Currency exchange currency to is null.");
        }
        if (currencyExchange.getCreatedTime() == null) {
            logger.debug("Currency exchange create time is null.");
            throw new MissingRequiredFieldException("Currency exchange create time is null.");
        }
        if (currencyExchange.getActive() == null) {
            logger.debug("Currency exchange active is null.");
            throw new MissingRequiredFieldException("Currency exchange active is null.");
        }

        Date date = Calendar.getInstance().getTime();
        currencyExchange.setUpdatedTime(date);
        return currencyExchangeDao.update(currencyExchange);
    }

    @Transactional
    @Override
    public CurrencyExchange delete(Long id) throws NoSuchEntityException {
        CurrencyExchange currencyExchange = currencyExchangeDao.get(id);
        currencyExchangeDao.delete(id);
        return currencyExchange;
    }

    @Override
    public List<CurrencyExchange> getAll() {
        return currencyExchangeDao.getAll();
    }

    @Override
    public Long countAll() {
        return currencyExchangeDao.countAll();
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     * This class is abstract which needs to be implemented by subclasses.
     *
     * @param currencyExchange contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     *                         it means no criteria on exact equals if t is null.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(CurrencyExchange currencyExchange, String search) {
        return currencyExchangeDao.countByCriteria(currencyExchange, search);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     * This class is abstract which needs to be implemented by subclasses.
     *
     * @param currencyExchange contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     *                         it means no criteria on exact equals if t is null.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     * @param start            first position of the result.
     * @param length           max record of the result.
     * @param order            order by field, default is id.
     * @param orderDir         order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<CurrencyExchange> findByCriteria(CurrencyExchange currencyExchange, String search,
                                                 Integer start, Integer length, String order,
                                                 ResourceProperties.JpaOrderDir orderDir) {
        return currencyExchangeDao.findByCriteria(currencyExchange, search, start, length, order,
                orderDir);
    }
}
