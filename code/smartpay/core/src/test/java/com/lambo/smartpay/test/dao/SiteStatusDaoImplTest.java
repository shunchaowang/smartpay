package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.persistence.entity.SiteStatus;
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            SiteStatus siteStatus = new SiteStatus();
            siteStatus.setName("ad hoc " + i);
            siteStatus.setCode("00" + i);
            siteStatus.setActive(true);
            siteStatusDao.create(siteStatus);
        }

        // create one with active is false
        SiteStatus siteStatus = new SiteStatus();
        siteStatus.setName("ad hoc " + 3);
        siteStatus.setCode("00" + 3);
        siteStatus.setActive(false);
        siteStatusDao.create(siteStatus);

        Long countByAdHoc = siteStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = siteStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = siteStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = siteStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = siteStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            SiteStatus siteStatus = new SiteStatus();
            siteStatus.setName("ad hoc " + i);
            siteStatus.setCode("00" + i);
            siteStatus.setActive(true);
            siteStatusDao.create(siteStatus);
        }

        // create one with active is false
        SiteStatus siteStatus = new SiteStatus();
        siteStatus.setName("ad hoc " + 3);
        siteStatus.setCode("00" + 3);
        siteStatus.setActive(false);
        siteStatusDao.create(siteStatus);

        // testing order asc
        List<SiteStatus> statuses =
                siteStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        SiteStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<SiteStatus> activeStatuses =
                siteStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<SiteStatus> archivedStatuses =
                siteStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = siteStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = siteStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
