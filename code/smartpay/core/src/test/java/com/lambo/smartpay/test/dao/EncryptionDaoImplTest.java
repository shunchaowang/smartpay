package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.EncryptionDao;
import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by swang on 3/2/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class EncryptionDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionDaoImplTest.class);

    private String md5EncryptionTypeCode = "100";

    @Autowired
    private EncryptionDao encryptionDao;
    @Autowired
    private EncryptionTypeDao encryptionTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing crud of Encryption.");
        EncryptionType md5EncryptionType = encryptionTypeDao.findByCode
                (md5EncryptionTypeCode);
        Date today = Calendar.getInstance().getTime();

        Encryption encryption = new Encryption();
        encryption.setKey("xyz");
        encryption.setCreatedTime(today);
        encryption.setActive(true);
        encryption.setEncryptionType(md5EncryptionType);

        encryption = encryptionDao.create(encryption);
        assertNotNull(encryption);

        encryption = encryptionDao.get(encryption.getId());
        assertNotNull(encryption);

        encryption.setKey("xyz updated");
        encryption = encryptionDao.update(encryption);
        assertNotNull(encryption);
        assertEquals("xyz updated", encryption.getKey());

        encryptionDao.delete(encryption.getId());

        assertNull(encryptionDao.get(encryption.getId()));
    }
}
