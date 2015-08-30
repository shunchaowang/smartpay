package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.core.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.core.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.core.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.core.persistence.dao.MerchantDao;
import com.lambo.smartpay.core.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.core.persistence.dao.SiteDao;
import com.lambo.smartpay.core.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.util.ResourceProperties;
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
        merchant.setIdentity("xyz");
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

//        merchant.setCommissionFee(fee);
//        merchant.setReturnFee(returnFee);

        merchant = merchantDao.create(merchant);
        assertNotNull(merchant);

        SiteStatus createdSiteStatus = siteStatusDao.findByCode(createdSiteStatusCode);
        Site site = new Site();
        site.setCreatedTime(todayDate);
        site.setActive(true);
        site.setName("xyz");
        site.setIdentity("xyz");
        site.setMerchant(merchant);
        site.setSiteStatus(createdSiteStatus);
        site.setUrl("www.yahoo.com.cn");
        site = siteDao.create(site);
        assertNotNull(site);

        Site siteSearch = new Site();
        siteSearch.setActive(true);
        Long count = siteDao.countByCriteria(siteSearch, "yz");
        assertEquals(new Long(1), count);
        count = siteDao.countByCriteria(siteSearch, "yzx");
        assertEquals(new Long(0), count);
        List<Site> sites = siteDao.findByCriteria(null, "xyz", 0, 10, "name", ResourceProperties
                .JpaOrderDir.ASC);
        assertNotNull(sites.get(0));
        Site s = new Site();
        s.setName("xyz");
        count = siteDao.countByCriteria(s, null);
        assertEquals(new Long(1), count);
        sites = siteDao.findByCriteria(s, null, null, null, null, null);
        assertNotNull(sites.get(0));
    }
}
