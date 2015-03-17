package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.persistence.entity.CredentialType;
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
 * Integration test for CredentialTypeDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CredentialTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialTypeDaoImplTest.class);

    @Autowired
    CredentialTypeDao credentialTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new CredentialType");
        CredentialType credentialType = new CredentialType();
        credentialType.setName("name");
        credentialType.setDescription("Description");
        credentialType.setCode("001");
        credentialType.setActive(true);

        credentialType = credentialTypeDao.create(credentialType);

        assertNotNull(credentialType);

        credentialType = credentialTypeDao.get(credentialType.getId());
        assertNotNull(credentialType);

        credentialType.setName("updated name");
        credentialType = credentialTypeDao.update(credentialType);
        assertNotNull(credentialType);
        assertEquals("updated name", credentialType.getName());

        credentialTypeDao.delete(credentialType.getId());
        assertNull(credentialTypeDao.get(credentialType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all CredentialType.");
        List<CredentialType> credentialTypes = credentialTypeDao.getAll();
        assertNotNull(credentialTypes);
    }
}
