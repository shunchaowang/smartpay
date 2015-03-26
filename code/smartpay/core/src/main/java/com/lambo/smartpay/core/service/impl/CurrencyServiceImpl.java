package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CurrencyDao;
import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.service.CurrencyService;
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
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
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
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

    @Override
    public List<Currency> getAll() {
        return currencyDao.getAll();
    }

    @Override
    public Long countAll() {
        return currencyDao.countAll();
    }
}
