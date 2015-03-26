package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
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
}
