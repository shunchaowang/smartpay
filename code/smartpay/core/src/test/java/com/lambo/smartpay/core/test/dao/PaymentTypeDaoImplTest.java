package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.PaymentTypeDao;
import com.lambo.smartpay.core.persistence.entity.PaymentType;
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
public class PaymentTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentTypeDaoImplTest.class);

    @Autowired
    PaymentTypeDao paymentTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new PaymentType");
        PaymentType paymentType = new PaymentType();
        paymentType.setName("name");
        paymentType.setDescription("Description");
        paymentType.setCode("001");
        paymentType.setActive(true);

        paymentType = paymentTypeDao.create(paymentType);

        assertNotNull(paymentType);

        paymentType = paymentTypeDao.get(paymentType.getId());
        assertNotNull(paymentType);

        paymentType.setName("updated name");
        paymentType = paymentTypeDao.update(paymentType);
        assertNotNull(paymentType);
        assertEquals("updated name", paymentType.getName());

        paymentTypeDao.delete(paymentType.getId());
        assertNull(paymentTypeDao.get(paymentType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all PaymentType.");
        List<PaymentType> paymentTypes = paymentTypeDao.getAll();
        assertNotNull(paymentTypes);
    }
}
