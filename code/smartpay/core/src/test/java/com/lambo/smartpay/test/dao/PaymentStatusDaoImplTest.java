package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.persistence.entity.PaymentStatus;
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
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class PaymentStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentStatusDaoImplTest.class);

    @Autowired
    PaymentStatusDao paymentStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new PaymentStatus");
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setName("name");
        paymentStatus.setDescription("Description");
        paymentStatus.setCode("001");
        paymentStatus.setActive(true);

        paymentStatus = paymentStatusDao.create(paymentStatus);

        assertNotNull(paymentStatus);

        paymentStatus = paymentStatusDao.get(paymentStatus.getId());
        assertNotNull(paymentStatus);

        paymentStatus.setName("updated name");
        paymentStatus = paymentStatusDao.update(paymentStatus);
        assertNotNull(paymentStatus);
        assertEquals("updated name", paymentStatus.getName());

        paymentStatusDao.delete(paymentStatus.getId());
        assertNull(paymentStatusDao.get(paymentStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all PaymentStatus.");
        List<PaymentStatus> paymentStatuses = paymentStatusDao.getAll();
        assertNotNull(paymentStatuses);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setName("ad hoc " + i);
            paymentStatus.setCode("00" + i);
            paymentStatus.setActive(true);
            paymentStatusDao.create(paymentStatus);
        }

        // create one with active is false
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setName("ad hoc " + 3);
        paymentStatus.setCode("00" + 3);
        paymentStatus.setActive(false);
        paymentStatusDao.create(paymentStatus);

        Long countByAdHoc = paymentStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = paymentStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = paymentStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = paymentStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = paymentStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setName("ad hoc " + i);
            paymentStatus.setCode("00" + i);
            paymentStatus.setActive(true);
            paymentStatusDao.create(paymentStatus);
        }

        // create one with active is false
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setName("ad hoc " + 3);
        paymentStatus.setCode("00" + 3);
        paymentStatus.setActive(false);
        paymentStatusDao.create(paymentStatus);

        // testing order asc
        List<PaymentStatus> statuses =
                paymentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        PaymentStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<PaymentStatus> activeStatuses =
                paymentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<PaymentStatus> archivedStatuses =
                paymentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = paymentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = paymentStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
