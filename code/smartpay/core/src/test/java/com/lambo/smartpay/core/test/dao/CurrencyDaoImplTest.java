package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.CurrencyDao;
import com.lambo.smartpay.core.persistence.entity.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Integration test for currency dao impl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CurrencyDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyDaoImplTest.class);

    @Autowired
    CurrencyDao currencyDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new Currency");
        Currency currency = new Currency();
        currency.setName("name");
        currency.setDescription("Description");
        currency.setCode("001");
        currency.setActive(true);

        currency = currencyDao.create(currency);

        assertNotNull(currency);

        currency = currencyDao.get(currency.getId());
        assertNotNull(currency);

        currency.setName("updated name");
        currency = currencyDao.update(currency);
        assertNotNull(currency);
        assertEquals("updated name", currency.getName());

        currencyDao.delete(currency.getId());
        assertNull(currencyDao.get(currency.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all Currency.");
        List<Currency> currencies = currencyDao.getAll();
        assertNotNull(currencies);
    }
}
