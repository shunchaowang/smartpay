package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CurrencyDao;
import com.lambo.smartpay.persistence.entity.Currency;
import com.lambo.smartpay.service.CurrencyService;
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
@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    @Autowired
    private CurrencyDao currencyDao;

    /**
     * Find Currency by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Currency findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return currencyDao.findByName(name);
    }

    /**
     * Find Currency by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Currency findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return currencyDao.findByCode(code);
    }

    /**
     * Create a new Currency.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param currency
     * @return
     */
    @Transactional
    @Override
    public Currency create(Currency currency) throws MissingRequiredFieldException,
            NotUniqueException {
        if (currency == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }
        if (StringUtils.isBlank(currency.getName()) ||
                StringUtils.isBlank(currency.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (currencyDao.findByName(currency.getName()) != null) {
            throw new NotUniqueException("Currency with name " + currency.getName() +
                    " already exists.");
        }
        if (currencyDao.findByCode(currency.getCode()) != null) {
            throw new NotUniqueException("Currency with code " + currency.getName() +
                    " already exists.");
        }
        currency.setActive(true);
        // pass all checks, create the object
        return currencyDao.create(currency);
    }

    /**
     * Get Currency by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Currency get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (currencyDao.get(id) == null) {
            throw new NoSuchEntityException("Currency with id " + id +
                    " does not exist.");
        }
        return currencyDao.get(id);
    }

    /**
     * Update an existing Currency.
     * Must check unique fields if are unique.
     *
     * @param currency
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public Currency update(Currency currency) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (currency == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }
        if (currency.getId() == null) {
            throw new MissingRequiredFieldException("Currency id is null.");
        }
        if (StringUtils.isBlank(currency.getName()) ||
                StringUtils.isBlank(currency.getCode())) {
            throw new MissingRequiredFieldException("Currency name or code is blank.");
        }
        // checking uniqueness
        Currency namedCurrency = currencyDao.findByName(currency.getName());
        if (namedCurrency != null &&
                !namedCurrency.getId().equals(currency.getId())) {
            throw new NotUniqueException("Currency with name " + currency.getName() +
                    " already exists.");
        }
        Currency codedCurrency = currencyDao.findByCode(currency.getCode());
        if (codedCurrency != null &&
                !codedCurrency.getId().equals(currency.getId())) {
            throw new NotUniqueException("Currency with code " + currency.getCode() +
                    " already exists.");
        }
        return currencyDao.update(currency);
    }

    /**
     * Delete an existing Currency.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public Currency delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Currency currency = currencyDao.get(id);
        if (currency == null) {
            throw new NoSuchEntityException("Currency with id " + id +
                    " does not exist.");
        }
        currencyDao.delete(id);
        return currency;
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
    public List<Currency> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param currency contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Currency currency) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param currency contains criteria if the field is not null or empty.
     * @param start
     * @param length   @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Currency> findByAdvanceSearch(Currency currency, Integer start, Integer length) {
        return null;
    }
}