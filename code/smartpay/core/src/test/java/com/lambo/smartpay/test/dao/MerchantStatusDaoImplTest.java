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
 * Integration test for MerchantStatusDaoImpl.
 * Created by swang on 2/19/2015.
 */
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
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

        // create one with active is false
        MerchantStatus merchantStatus = new MerchantStatus();
        merchantStatus.setName("ad hoc " + 3);
        merchantStatus.setCode("00" + 3);
        merchantStatus.setActive(false);
        merchantStatusDao.create(merchantStatus);

        Long countByAdHoc = merchantStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = merchantStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = merchantStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = merchantStatusDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = merchantStatusDao.countByAdHocSearch("1", null);
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

        // create one with active is false
        MerchantStatus merchantStatus = new MerchantStatus();
        merchantStatus.setName("ad hoc " + 3);
        merchantStatus.setCode("00" + 3);
        merchantStatus.setActive(false);
        merchantStatusDao.create(merchantStatus);

        // testing order asc
        List<MerchantStatus> statuses =
                merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        MerchantStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<MerchantStatus> activeStatuses =
                merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<MerchantStatus> archivedStatuses =
                merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = merchantStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = merchantStatusDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir
                        .ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());

        // we don't want to have wildcard search on id actually
//        List<MerchantStatus> findById =
//                merchantStatusDao.findByAdHocSearch("1", 0, 10, "id", ResourceUtil.JpaOrderDir
// .ASC, null);
//        assertNotNull(findById);
    }

    /*
    @Test
    @Transactional
    public void testSwitchMerchantStatus() {

        LOG.info("Testing switchMerchantStatus.");
        MerchantStatus merchantStatus = new MerchantStatus();
        merchantStatus.setName("switch");
        merchantStatus.setCode("switch");
        merchantStatus.setActive(true);
        merchantStatus = merchantStatusDao.create(merchantStatus);
        assertEquals(true, merchantStatus.getActive());

        try {
            merchantStatus = merchantStatusDao.switchMerchantStatus(merchantStatus.getId(), false);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(false, merchantStatus.getActive());

        try {
            merchantStatus = merchantStatusDao.switchMerchantStatus(merchantStatus.getId(), false);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(false, merchantStatus.getActive());

        try {
            merchantStatus = merchantStatusDao.switchMerchantStatus(merchantStatus.getId(), true);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(true, merchantStatus.getActive());
    }
    */
}
