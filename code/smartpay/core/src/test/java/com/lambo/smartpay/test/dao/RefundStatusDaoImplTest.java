package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.persistence.entity.RefundStatus;
import com.lambo.smartpay.util.ResourceProperties;
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
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class RefundStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(RefundStatusDaoImplTest.class);

    @Autowired
    RefundStatusDao refundStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new RefundStatus");
        RefundStatus refundStatus = new RefundStatus();
        refundStatus.setName("name");
        refundStatus.setDescription("Description");
        refundStatus.setCode("001");
        refundStatus.setActive(true);

        refundStatus = refundStatusDao.create(refundStatus);

        assertNotNull(refundStatus);

        refundStatus = refundStatusDao.get(refundStatus.getId());
        assertNotNull(refundStatus);

        refundStatus.setName("updated name");
        refundStatus = refundStatusDao.update(refundStatus);
        assertNotNull(refundStatus);
        assertEquals("updated name", refundStatus.getName());

        refundStatusDao.delete(refundStatus.getId());
        assertNull(refundStatusDao.get(refundStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all RefundStatus.");
        List<RefundStatus> refundStatuses = refundStatusDao.getAll();
        assertNotNull(refundStatuses);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            RefundStatus refundStatus = new RefundStatus();
            refundStatus.setName("ad hoc " + i);
            refundStatus.setCode("00" + i);
            refundStatus.setActive(true);
            refundStatusDao.create(refundStatus);
        }

        // create one with active is false
        RefundStatus refundStatus = new RefundStatus();
        refundStatus.setName("ad hoc " + 3);
        refundStatus.setCode("00" + 3);
        refundStatus.setActive(false);
        refundStatusDao.create(refundStatus);

        Long countByAdHoc = refundStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = refundStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = refundStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = refundStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = refundStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            RefundStatus refundStatus = new RefundStatus();
            refundStatus.setName("ad hoc " + i);
            refundStatus.setCode("00" + i);
            refundStatus.setActive(true);
            refundStatusDao.create(refundStatus);
        }

        // create one with active is false
        RefundStatus refundStatus = new RefundStatus();
        refundStatus.setName("ad hoc " + 3);
        refundStatus.setCode("00" + 3);
        refundStatus.setActive(false);
        refundStatusDao.create(refundStatus);

        // testing order asc
        List<RefundStatus> statuses =
                refundStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        null);
        assertEquals(4, statuses.size());

        RefundStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<RefundStatus> activeStatuses =
                refundStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<RefundStatus> archivedStatuses =
                refundStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = refundStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = refundStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir
                        .ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
