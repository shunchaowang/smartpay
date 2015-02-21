package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.model.MerchantStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by swang on 2/19/2015.
 */
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("dev")
public class MerchantStatusDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantStatusDaoTest.class);

    @Autowired
    private MerchantStatusDao merchantStatusDao;

    @Test
    @Transactional
    public void testCrud() {

        // create new merchant status
        MerchantStatus merchantStatus = new MerchantStatus();
        merchantStatus.setName("name");
        merchantStatus.setDescription("Description");
        merchantStatus = merchantStatusDao.create(merchantStatus);

        assertNotNull(merchantStatus);
        assertEquals("name", merchantStatus.getName());

        // read the newly created merchant status
        merchantStatus = merchantStatusDao.get(merchantStatus.getId());
        assertNotNull(merchantStatus);

        // update the name
        merchantStatus.setName("updated name");
        merchantStatus = merchantStatusDao.update(merchantStatus);
        assertNotNull(merchantStatus);
        assertEquals("updated name", merchantStatus.getName());

        // delete
        merchantStatusDao.delete(merchantStatus.getId());
        assertNull(merchantStatusDao.get(merchantStatus.getId()));

    }

    @Test
    public void testGetAll() {
        List<MerchantStatus> merchantStatuses = merchantStatusDao.getAll();
        assertNotNull(merchantStatuses);
    }
}
