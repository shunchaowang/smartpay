package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
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
}
