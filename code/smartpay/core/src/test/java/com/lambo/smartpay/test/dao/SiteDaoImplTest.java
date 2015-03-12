package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.dao.MerchantDao;
import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.dao.SiteDao;
import com.lambo.smartpay.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
import com.lambo.smartpay.persistence.entity.CredentialType;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.FeeType;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
import com.lambo.smartpay.persistence.entity.Site;
import com.lambo.smartpay.persistence.entity.SiteStatus;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by swang on 3/6/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class SiteDaoImplTest {

    private static final Logger logger = LoggerFactory.getLogger(SiteDaoImplTest.class);

    @Autowired
    private MerchantStatusDao merchantStatusDao;
    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private SiteStatusDao siteStatusDao;
    @Autowired
    private SiteDao siteDao;
    @Autowired
    private CredentialTypeDao credentialTypeDao;
    @Autowired
    private CredentialStatusDao credentialStatusDao;
    @Autowired
    private FeeTypeDao feeTypeDao;
    @Autowired
    private EncryptionTypeDao encryptionTypeDao;

    private Date todayDate = Calendar.getInstance().getTime();

    private String normalMerchantStatusCode = "200";
    private String submittedCredentialStatusCode = "400";
    private String certificateCredentialTypeCode = "100";
    private String md5EncryptionTypeCode = "100";
    private String staticFeeTypeCode = "100";
    private String createdSiteStatusCode = "400";

    @Test
    @Transactional
    public void testCrud() {

        Merchant merchant = new Merchant();
        merchant.setActive(true);
        merchant.setName("xyz");
        merchant.setCreatedTime(todayDate);

        MerchantStatus normalMerchantStatus = merchantStatusDao.
                findByCode(normalMerchantStatusCode);
        merchant.setMerchantStatus(normalMerchantStatus);

        // set credential
        CredentialStatus submitCredentialStatus = credentialStatusDao.findByCode
                (submittedCredentialStatusCode);
        CredentialType certificateCredentialType = credentialTypeDao.findByCode
                (certificateCredentialTypeCode);
        Credential credential = new Credential();
        credential.setActive(true);
        credential.setCreatedTime(todayDate);
        credential.setExpirationDate(todayDate);
        credential.setContent("xyz");
        credential.setCredentialStatus(submitCredentialStatus);
        credential.setCredentialType(certificateCredentialType);

        merchant.setCredential(credential);

        // set encryption
        EncryptionType md5EncryptionType = encryptionTypeDao.findByCode(md5EncryptionTypeCode);
        Encryption encryption = new Encryption();
        encryption.setActive(true);
        encryption.setKey("xyz");
        encryption.setCreatedTime(todayDate);
        encryption.setEncryptionType(md5EncryptionType);

        merchant.setEncryption(encryption);

        // set charge fee and return fee
        FeeType staticFeeType = feeTypeDao.findByCode(staticFeeTypeCode);
        Fee fee = new Fee();
        fee.setValue(new Float(1.0));
        fee.setCreatedTime(todayDate);
        fee.setActive(true);
        fee.setFeeType(staticFeeType);

        Fee returnFee = new Fee();
        returnFee.setValue(new Float(1.0));
        returnFee.setCreatedTime(todayDate);
        returnFee.setActive(true);
        returnFee.setFeeType(staticFeeType);

        merchant.setCommissionFee(fee);
        merchant.setReturnFee(returnFee);

        merchant = merchantDao.create(merchant);
        assertNotNull(merchant);

        SiteStatus createdSiteStatus = siteStatusDao.findByCode(createdSiteStatusCode);
        Site site = new Site();
        site.setCreatedTime(todayDate);
        site.setActive(true);
        site.setName("xyz");
        site.setMerchant(merchant);
        site.setSiteStatus(createdSiteStatus);
        site.setUrl("www.google.com");
        site = siteDao.create(site);
        assertNotNull(site);

        Long count = siteDao.countByAdHocSearch("yz", true);
        assertEquals(new Long(1), count);
        count = siteDao.countByAdHocSearch("yzx", true);
        assertEquals(new Long(0), count);
        List<Site> sites = siteDao.findByAdHocSearch("xyz", 0, 10, "name", ResourceProperties
                .JpaOrderDir.ASC, true);
        assertNotNull(sites.get(0));
        Site s = new Site();
        s.setName("xyz");
        count = siteDao.countByAdvanceSearch(s);
        assertEquals(new Long(1), count);
        sites = siteDao.findByAdvanceSearch(s);
        assertNotNull(sites.get(0));
    }
}
