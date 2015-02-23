package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.model.MerchantStatus;
import com.lambo.smartpay.util.ResourceUtil;
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            MerchantStatus merchantStatus = new MerchantStatus();
            merchantStatus.setName("ad hoc " + i);
            merchantStatus.setCode("00" + i);
            merchantStatus.setActive(true);
            merchantStatusDao.create(merchantStatus);
        }

        Long countByAdHoc = merchantStatusDao.countByAdHocSearch("ad hoc");
        assertEquals(new Long(3), countByAdHoc);

        Long countByX = merchantStatusDao.countByAdHocSearch("X");
        assertEquals(new Long(0), countByX);

        Long countById = merchantStatusDao.countByAdHocSearch("1");
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            MerchantStatus merchantStatus = new MerchantStatus();
            merchantStatus.setName("ad hoc " + i);
            merchantStatus.setCode("00" + i);
            merchantStatus.setActive(true);
            merchantStatusDao.create(merchantStatus);
        }

        // testing order asc
        List<MerchantStatus> statuses =
                merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC);
        assertEquals(3, statuses.size());

        MerchantStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        // testing order desc
        statuses =
                merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.DESC);
        assertEquals(3, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("002", status.getCode());

        statuses = merchantStatusDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());

        List<MerchantStatus> findById =
                merchantStatusDao.findByAdHocSearch("1", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC);
        assertNotNull(findById);
    }
}
