package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.CredentialDao;
import com.lambo.smartpay.dao.CredentialStatusDao;
import com.lambo.smartpay.dao.CredentialTypeDao;
import com.lambo.smartpay.model.Credential;
import com.lambo.smartpay.model.CredentialStatus;
import com.lambo.smartpay.model.CredentialType;
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
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("dev")
public class CredentialDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialDaoImplTest.class);

    private String submittedCredentialStatusCode = "400";
    private String certificateCredentialTypeCode = "100";

    @Autowired
    private CredentialDao credentialDao;
    @Autowired
    private CredentialStatusDao credentialStatusDao;
    @Autowired
    private CredentialTypeDao credentialTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing crud of Credential.");
        CredentialStatus submittedCredentialStatus = credentialStatusDao.findByCode
                (submittedCredentialStatusCode);
        CredentialType certificateCredentialType = credentialTypeDao.findByCode
                (certificateCredentialTypeCode);
        Date today = Calendar.getInstance().getTime();

        Credential credential = new Credential();
        credential.setContent("xyz");
        credential.setExpirationDate(today);
        credential.setCreatedTime(today);
        credential.setActive(true);
        credential.setCredentialStatus(submittedCredentialStatus);
        credential.setCredentialType(certificateCredentialType);

        credential = credentialDao.create(credential);
        assertNotNull(credential);
    }
}
