package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.ReturnStatusDao;
import com.lambo.smartpay.persistence.entity.ReturnStatus;
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            ReturnStatus returnStatus = new ReturnStatus();
            returnStatus.setName("ad hoc " + i);
            returnStatus.setCode("00" + i);
            returnStatus.setActive(true);
            returnStatusDao.create(returnStatus);
        }

        // create one with active is false
        ReturnStatus returnStatus = new ReturnStatus();
        returnStatus.setName("ad hoc " + 3);
        returnStatus.setCode("00" + 3);
        returnStatus.setActive(false);
        returnStatusDao.create(returnStatus);

        Long countByAdHoc = returnStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = returnStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = returnStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = returnStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = returnStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            ReturnStatus returnStatus = new ReturnStatus();
            returnStatus.setName("ad hoc " + i);
            returnStatus.setCode("00" + i);
            returnStatus.setActive(true);
            returnStatusDao.create(returnStatus);
        }

        // create one with active is false
        ReturnStatus returnStatus = new ReturnStatus();
        returnStatus.setName("ad hoc " + 3);
        returnStatus.setCode("00" + 3);
        returnStatus.setActive(false);
        returnStatusDao.create(returnStatus);

        // testing order asc
        List<ReturnStatus> statuses =
                returnStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        null);
        assertEquals(4, statuses.size());

        ReturnStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<ReturnStatus> activeStatuses =
                returnStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<ReturnStatus> archivedStatuses =
                returnStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir
                                .ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = returnStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = returnStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir
                        .ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
