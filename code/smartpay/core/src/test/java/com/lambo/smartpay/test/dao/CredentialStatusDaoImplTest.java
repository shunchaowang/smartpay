package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
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
 * Integration test for CredentialStatusDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CredentialStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialStatusDaoImplTest.class);

    @Autowired
    CredentialStatusDao credentialStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new CredentialStatus");
        CredentialStatus credentialStatus = new CredentialStatus();
        credentialStatus.setName("name");
        credentialStatus.setDescription("Description");
        credentialStatus.setCode("001");
        credentialStatus.setActive(true);

        credentialStatus = credentialStatusDao.create(credentialStatus);

        assertNotNull(credentialStatus);

        credentialStatus = credentialStatusDao.get(credentialStatus.getId());
        assertNotNull(credentialStatus);

        credentialStatus.setName("updated name");
        credentialStatus = credentialStatusDao.update(credentialStatus);
        assertNotNull(credentialStatus);
        assertEquals("updated name", credentialStatus.getName());

        credentialStatusDao.delete(credentialStatus.getId());
        assertNull(credentialStatusDao.get(credentialStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all CredentialStatus.");
        List<CredentialStatus> credentialStatuses = credentialStatusDao.getAll();
        assertNotNull(credentialStatuses);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CredentialStatus credentialStatus = new CredentialStatus();
            credentialStatus.setName("ad hoc " + i);
            credentialStatus.setCode("00" + i);
            credentialStatus.setActive(true);
            credentialStatusDao.create(credentialStatus);
        }

        // create one with active is false
        CredentialStatus credentialStatus = new CredentialStatus();
        credentialStatus.setName("ad hoc " + 3);
        credentialStatus.setCode("00" + 3);
        credentialStatus.setActive(false);
        credentialStatusDao.create(credentialStatus);

        Long countByAdHoc = credentialStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = credentialStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = credentialStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = credentialStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = credentialStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CredentialStatus credentialStatus = new CredentialStatus();
            credentialStatus.setName("ad hoc " + i);
            credentialStatus.setCode("00" + i);
            credentialStatus.setActive(true);
            credentialStatusDao.create(credentialStatus);
        }

        // create one with active is false
        CredentialStatus credentialStatus = new CredentialStatus();
        credentialStatus.setName("ad hoc " + 3);
        credentialStatus.setCode("00" + 3);
        credentialStatus.setActive(false);
        credentialStatusDao.create(credentialStatus);

        // testing order asc
        List<CredentialStatus> statuses =
                credentialStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        CredentialStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<CredentialStatus> activeStatuses =
                credentialStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<CredentialStatus> archivedStatuses =
                credentialStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = credentialStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = credentialStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
