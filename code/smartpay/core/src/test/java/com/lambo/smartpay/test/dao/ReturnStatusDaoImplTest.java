package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.ReturnStatusDao;
import com.lambo.smartpay.persistence.entity.ReturnStatus;
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
public class ReturnStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReturnStatusDaoImplTest.class);

    @Autowired
    ReturnStatusDao returnStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new ReturnStatus");
        ReturnStatus returnStatus = new ReturnStatus();
        returnStatus.setName("name");
        returnStatus.setDescription("Description");
        returnStatus.setCode("001");
        returnStatus.setActive(true);

        returnStatus = returnStatusDao.create(returnStatus);

        assertNotNull(returnStatus);

        returnStatus = returnStatusDao.get(returnStatus.getId());
        assertNotNull(returnStatus);

        returnStatus.setName("updated name");
        returnStatus = returnStatusDao.update(returnStatus);
        assertNotNull(returnStatus);
        assertEquals("updated name", returnStatus.getName());

        returnStatusDao.delete(returnStatus.getId());
        assertNull(returnStatusDao.get(returnStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all ReturnStatus.");
        List<ReturnStatus> returnStatuses = returnStatusDao.getAll();
        assertNotNull(returnStatuses);
    }
}
