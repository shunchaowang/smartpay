package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
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
 * Integration test for MerchantStatusDaoImpl.
 * Created by swang on 2/19/2015.
 */
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class MerchantStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantStatusDaoImplTest.class);

    @Autowired
    private MerchantStatusDao merchantStatusDao;

    @Test
    @Transactional
    public void testCrud() {

        LOG.info("Testing creating new MerchantStatus.");
        // create new merchant status
        MerchantStatus merchantStatus = new MerchantStatus();
        merchantStatus.setName("name");
        merchantStatus.setCode("001");
        merchantStatus.setDescription("Description");
        merchantStatus.setActive(true);
        merchantStatus = merchantStatusDao.create(merchantStatus);

        assertNotNull(merchantStatus);
        assertEquals("name", merchantStatus.getName());

        LOG.info("Testing reading a MerchantStatus.");
        // read the newly created merchant status
        merchantStatus = merchantStatusDao.get(merchantStatus.getId());
        assertNotNull(merchantStatus);

        LOG.info("Testing updating a MerchantStatus.");
        // update the name
        merchantStatus.setName("updated name");
        merchantStatus = merchantStatusDao.update(merchantStatus);
        assertNotNull(merchantStatus);
        assertEquals("updated name", merchantStatus.getName());

        LOG.info("Testing deleting a MerchantStatus.");
        // delete
        merchantStatusDao.delete(merchantStatus.getId());
        assertNull(merchantStatusDao.get(merchantStatus.getId()));

    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all MerchantStatus.");
        List<MerchantStatus> merchantStatuses = merchantStatusDao.getAll();
        assertNotNull(merchantStatuses);
    }
}
