package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.entity.EncryptionType;
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
 * Integration test for EncryptionTypeDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class EncryptionTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionTypeDaoImplTest.class);

    @Autowired
    EncryptionTypeDao encryptionTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new EncryptionType");
        EncryptionType encryptionType = new EncryptionType();
        encryptionType.setName("name");
        encryptionType.setDescription("Description");
        encryptionType.setCode("001");
        encryptionType.setActive(true);

        encryptionType = encryptionTypeDao.create(encryptionType);

        assertNotNull(encryptionType);

        encryptionType = encryptionTypeDao.get(encryptionType.getId());
        assertNotNull(encryptionType);

        encryptionType.setName("updated name");
        encryptionType = encryptionTypeDao.update(encryptionType);
        assertNotNull(encryptionType);
        assertEquals("updated name", encryptionType.getName());

        encryptionTypeDao.delete(encryptionType.getId());
        assertNull(encryptionTypeDao.get(encryptionType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all EncryptionType.");
        List<EncryptionType> encryptionTypes = encryptionTypeDao.getAll();
        assertNotNull(encryptionTypes);
    }
}
