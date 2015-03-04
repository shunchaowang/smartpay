package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CredentialDao;
import com.lambo.smartpay.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.persistence.dao.EncryptionDao;
import com.lambo.smartpay.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.persistence.dao.FeeDao;
import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.dao.MerchantDao;
import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
import com.lambo.smartpay.persistence.entity.CredentialType;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.FeeType;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
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
 * Created by swang on 3/3/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class MerchantDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantDaoImplTest.class);

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantStatusDao merchantStatusDao;
    @Autowired
    private CredentialDao credentialDao;
    @Autowired
    private CredentialStatusDao credentialStatusDao;
    @Autowired
    private CredentialTypeDao credentialTypeDao;
    @Autowired
    private EncryptionDao encryptionDao;
    @Autowired
    private EncryptionTypeDao encryptionTypeDao;
    @Autowired
    private FeeDao feeDao;
    @Autowired
    private FeeTypeDao feeTypeDao;

    private Date todayDate = Calendar.getInstance().getTime();

    private String normalMerchantStatusCode = "200";
    private String submittedCredentialStatusCode = "400";
    private String certificateCredentialTypeCode = "100";
    private String md5EncryptionTypeCode = "100";
    private String staticFeeTypeCode = "100";

    @Test
    @Transactional
    public void testCreate() {
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
    }

}
