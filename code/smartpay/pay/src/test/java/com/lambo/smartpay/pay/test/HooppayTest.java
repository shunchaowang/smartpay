package com.lambo.smartpay.pay.test;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.config.ServiceConfig;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.service.CredentialStatusService;
import com.lambo.smartpay.core.service.CredentialTypeService;
import com.lambo.smartpay.core.service.EncryptionTypeService;
import com.lambo.smartpay.core.service.FeeTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.MerchantStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.pay.config.MvcConfig;
import com.lambo.smartpay.pay.config.PayConfig;
import com.lambo.smartpay.pay.config.WebAppInitializer;
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

import static org.junit.Assert.assertNotNull;

/**
 * Created by swang on 3/31/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PayConfig.class, MvcConfig.class, WebAppInitializer.class,
        ServiceConfig.class, PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class HooppayTest {

    /*
    private static final Logger logger = LoggerFactory.getLogger(HooppayTest.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantStatusService merchantStatusService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteStatusService siteStatusService;
    @Autowired
    private CredentialStatusService credentialStatusService;
    @Autowired
    private CredentialTypeService credentialTypeService;
    @Autowired
    private EncryptionTypeService encryptionTypeService;
    @Autowired
    private FeeTypeService feeTypeService;

    private Date todayDate = Calendar.getInstance().getTime();

    private String normalMerchantStatusCode = "200";
    private String submittedCredentialStatusCode = "400";
    private String certificateCredentialTypeCode = "100";
    private String md5EncryptionTypeCode = "100";
    private String staticFeeTypeCode = "100";
    private String createdSiteStatusCode = "400";

    @Test
    @Transactional
    public void testPay() throws Exception {

        Merchant merchant = new Merchant();

        Credential credential = new Credential();
        credential.setExpirationDate(todayDate);
        credential.setContent("xxxxxx");
        CredentialStatus credentialStatus = credentialStatusService
                .findByCode(submittedCredentialStatusCode);
        credential.setCredentialStatus(credentialStatus);
        CredentialType credentialType = credentialTypeService
                .findByCode(certificateCredentialTypeCode);
        credential.setCredentialType(credentialType);

        merchant.setCredential(credential);

        Encryption encryption = new Encryption();
        encryption.setKey("00000000");
        EncryptionType encryptionType = encryptionTypeService.findByCode(md5EncryptionTypeCode);
        encryption.setEncryptionType(encryptionType);

        merchant.setEncryption(encryption);

        Fee commissionFee = new Fee();
        commissionFee.setValue(new Float(1.0));
        FeeType feeType = feeTypeService.findByCode(staticFeeTypeCode);
        commissionFee.setFeeType(feeType);

        merchant.setCommissionFee(commissionFee);

        Fee returnFee = new Fee();
        returnFee.setValue(new Float(2.0));
        returnFee.setFeeType(feeType);

        merchant.setReturnFee(returnFee);

        merchant.setIdentity("xxxxxxx");
        MerchantStatus merchantStatus = merchantStatusService.findByCode(normalMerchantStatusCode);
        merchant.setMerchantStatus(merchantStatus);
        merchant.setName("xxxxxxx");

        merchant = merchantService.create(merchant);

        assertNotNull(merchant);
    }
    */
}
