package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.PaymentStatusDao;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
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
}
