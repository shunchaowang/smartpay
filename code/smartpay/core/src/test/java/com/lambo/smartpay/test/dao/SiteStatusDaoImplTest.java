package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.persistence.entity.SiteStatus;
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
public class SiteStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(SiteStatusDaoImplTest.class);

    @Autowired
    SiteStatusDao siteStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new SiteStatus");
        SiteStatus siteStatus = new SiteStatus();
        siteStatus.setName("name");
        siteStatus.setDescription("Description");
        siteStatus.setCode("001");
        siteStatus.setActive(true);

        siteStatus = siteStatusDao.create(siteStatus);

        assertNotNull(siteStatus);

        siteStatus = siteStatusDao.get(siteStatus.getId());
        assertNotNull(siteStatus);

        siteStatus.setName("updated name");
        siteStatus = siteStatusDao.update(siteStatus);
        assertNotNull(siteStatus);
        assertEquals("updated name", siteStatus.getName());

        siteStatusDao.delete(siteStatus.getId());
        assertNull(siteStatusDao.get(siteStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all SiteStatus.");
        List<SiteStatus> siteStatuses = siteStatusDao.getAll();
        assertNotNull(siteStatuses);
    }
}
