package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.CredentialDao;
import com.lambo.smartpay.core.persistence.dao.CredentialStatusDao;
import com.lambo.smartpay.core.persistence.dao.CredentialTypeDao;
import com.lambo.smartpay.core.persistence.dao.EncryptionDao;
import com.lambo.smartpay.core.persistence.dao.EncryptionTypeDao;
import com.lambo.smartpay.core.persistence.dao.FeeDao;
import com.lambo.smartpay.core.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.core.persistence.dao.MerchantDao;
import com.lambo.smartpay.core.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
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

        LOG.info("Testing reading associations");
        encryption = merchant.getEncryption();
        assertNotNull(encryption);
        credential = merchant.getCredential();
        assertNotNull(credential);
        fee = merchant.getCommissionFee();
        assertNotNull(fee);
        returnFee = merchant.getReturnFee();
        assertNotNull(returnFee);

        LOG.info("Testing updating simple attributes");
        merchant.setName("updated xyz");
        merchant = merchantDao.update(merchant);
        assertEquals("updated xyz", merchant.getName());

        LOG.info("Testing updating associations");
        merchant.getCredential().setContent("updated xyz");
        merchant.getCommissionFee().setValue(new Float(2.0));
        merchant.getReturnFee().setValue(new Float(3.0));
        merchant = merchantDao.update(merchant);
        assertEquals("updated xyz", merchant.getCredential().getContent());
        assertEquals(new Float(2.0), merchant.getCommissionFee().getValue());
        assertEquals(new Float(3.0), merchant.getReturnFee().getValue());

        LOG.info("Testing deleting");
        encryption = merchant.getEncryption();
        fee = merchant.getCommissionFee();
        returnFee = merchant.getReturnFee();
        merchantDao.delete(merchant.getId());
        assertNull(merchantDao.get(merchant.getId()));
        assertNull(encryptionDao.get(encryption.getId()));
        assertNull(feeDao.get(fee.getId()));
        assertNull(feeDao.get(returnFee.getId()));
    }
}
