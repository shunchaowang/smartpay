package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.CurrencyDao;
import com.lambo.smartpay.model.Currency;
import com.lambo.smartpay.util.ResourceUtil;
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
@ContextConfiguration(classes = {AppConfig.class})
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Currency currency = new Currency();
            currency.setName("ad hoc " + i);
            currency.setCode("00" + i);
            currency.setActive(true);
            currencyDao.create(currency);
        }

        // create one with active is false
        Currency currency = new Currency();
        currency.setName("ad hoc " + 3);
        currency.setCode("00" + 3);
        currency.setActive(false);
        currencyDao.create(currency);

        Long countByAdHoc = currencyDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = currencyDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = currencyDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = currencyDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = currencyDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Currency currency = new Currency();
            currency.setName("ad hoc " + i);
            currency.setCode("00" + i);
            currency.setActive(true);
            currencyDao.create(currency);
        }

        // create one with active is false
        Currency currency = new Currency();
        currency.setName("ad hoc " + 3);
        currency.setCode("00" + 3);
        currency.setActive(false);
        currencyDao.create(currency);

        // testing order asc
        List<Currency> currencies =
                currencyDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, currencies.size());

        Currency status = currencies.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<Currency> activeStatuses =
                currencyDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<Currency> archivedStatuses =
                currencyDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        currencies = currencyDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, currencies.size());

        status = currencies.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        currencies = currencyDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(currencies);
        assertEquals(0, currencies.size());
    }
}
