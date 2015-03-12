package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.CredentialDao;
import com.lambo.smartpay.persistence.dao.EncryptionDao;
import com.lambo.smartpay.persistence.dao.FeeDao;
import com.lambo.smartpay.persistence.dao.MerchantDao;
import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
import com.lambo.smartpay.service.GenericQueryService;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.util.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/10/2015.
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService, GenericQueryService<Merchant, Long> {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantStatusDao merchantStatusDao;
    @Autowired
    private CredentialDao credentialDao;
    @Autowired
    private FeeDao feeDao;
    @Autowired
    private EncryptionDao encryptionDao;

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        return merchantDao.countByAdHocSearch(search, activeFlag);
    }

    /**
     * Find all T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    @Override
    public List<Merchant> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        if (start == null) {
            logger.info("Start is null.");
            return null;
        }
        if (length == null) {
            logger.info("Length is null.");
            return null;
        }

        if (order == null) {
            logger.info("Order is null.");
            return null;
        }
        if (orderDir == null) {
            logger.info("OrderDir is null.");
            return null;
        }
        return merchantDao.findByAdHocSearch(search, start, length, order, orderDir, activeFlag);
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Merchant merchant) {
        if (merchant == null) {
            logger.info("Merchant is null.");
        }
        return merchantDao.countByAdvanceSearch(merchant);
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @param start
     * @param length   @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Merchant> findByAdvanceSearch(Merchant merchant, Integer start, Integer length) {
        if (merchant == null) {
            logger.info("Merchant is null.");
        }
        if (start == null) {
            logger.info("Start is null.");
        }
        if (length == null) {
            logger.info("Length is null.");
        }
        return merchantDao.findByAdvanceSearch(merchant, start, length);
    }

    /**
     * Create a new merchant.
     *
     * @param merchant should have unique name, new credential attached,
     *                 new commission fee attached, new return fee attached.
     * @return
     * @throws MissingRequiredFieldException
     * @throws NotUniqueException
     */
    @Transactional
    @Override
    public Merchant create(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        Date date = Calendar.getInstance().getTime();
        if (merchant == null) {
            throw new MissingRequiredFieldException("Merchant is null.");
        }
        if (StringUtils.isBlank(merchant.getName())) {
            throw new MissingRequiredFieldException("Merchant name is null.");
        }
        if (merchantDao.findByName(merchant.getName()) != null) {
            throw new NotUniqueException("Merchant with name " + merchant.getName() +
                    " already exists.");
        }
        // check credential, commission fee and return fee
        if (merchant.getCredential() == null) {
            throw new MissingRequiredFieldException("Merchant credential is null.");
        }
        // check credential required fields
        if (StringUtils.isBlank(merchant.getCredential().getContent())) {
            throw new MissingRequiredFieldException("Merchant credential content is null.");
        }
        if (merchant.getCredential().getExpirationDate() == null) {
            throw new MissingRequiredFieldException("Merchant credential expiration date is null.");
        }
        if (merchant.getCredential().getCredentialStatus() == null) {
            throw new MissingRequiredFieldException("Merchant credential status is null.");
        }
        if (merchant.getCredential().getCredentialType() == null) {
            throw new MissingRequiredFieldException("Merchant credential type is null.");
        }
        // set active default to be active and created time for credential
        merchant.getCredential().setCreatedTime(date);
        merchant.getCredential().setActive(true);

        // check commission fee
        if (merchant.getCommissionFee() == null) {
            throw new MissingRequiredFieldException("Merchant commission fee is null.");
        }
        if (merchant.getCommissionFee().getValue() == null) {
            throw new MissingRequiredFieldException("Merchant commission fee value is null.");
        }
        if (merchant.getCommissionFee().getFeeType() == null) {
            throw new MissingRequiredFieldException("Merchant commission fee type is null.");
        }
        // set active default to be active and created time
        merchant.getCommissionFee().setActive(true);
        merchant.getCommissionFee().setCreatedTime(date);

        // check return fee
        if (merchant.getReturnFee() == null) {
            throw new MissingRequiredFieldException("Merchant return fee is null.");
        }
        if (merchant.getReturnFee().getValue() == null) {
            throw new MissingRequiredFieldException("Merchant return fee value is null.");
        }
        if (merchant.getReturnFee().getFeeType() == null) {
            throw new MissingRequiredFieldException("Merchant return fee type is null.");
        }
        // set active default to be active and created time
        merchant.getReturnFee().setActive(true);
        merchant.getReturnFee().setCreatedTime(date);

        // set createdTime
        merchant.setCreatedTime(date);
        // set merchant status to be normal when creating
        merchant.setActive(true);

        // generate encryption key for the merchant
        if (merchant.getEncryption() == null) {
            throw new MissingRequiredFieldException("Merchant encryption is null.");
        }
        if (merchant.getEncryption().getEncryptionType() == null) {
            throw new MissingRequiredFieldException("Merchant encryption type is null.");
        }
        if (StringUtils.isBlank(merchant.getEncryption().getKey())) {
            throw new MissingRequiredFieldException("Merchant encryption key is null.");
        }
        merchant.getEncryption().setCreatedTime(date);
        merchant.getEncryption().setActive(true);
        // key generation should be done by web
//        String key = RandomStringUtils.randomNumeric(ResourceUtil.ENCRYPTION_KEY_LENGTH);
//        merchant.getEncryption().setKey(key);
        return merchantDao.create(merchant);
    }

    @Override
    public Merchant get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (merchantDao.get(id) == null) {
            throw new NoSuchEntityException("Merchant with id " + id + " does not exist.");
        }
        return merchantDao.get(id);
    }

    @Transactional
    @Override
    public Merchant update(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        return null;
    }

    @Transactional
    @Override
    public Merchant delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id + " does not exist.");
        }
        merchantDao.get(id);
        return merchant;
    }

    @Override
    public Merchant findByName(String name) {
        if (StringUtils.isBlank(name)) {
            logger.info("Name is null.");
            return null;
        }
        return merchantDao.findByName(name);
    }

    /**
     * Freeze a Merchant by updating the MerchantStatus from Normal to Frozen.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean freezeMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.findByCode(ResourceUtil
                .MERCHANT_STATUS_FROZEN_CODE);
        merchant.setMerchantStatus(merchantStatus);
        return true;
    }

    /**
     * Unfreeze a Merchant by updating the MerchantStatus from Frozen to Normal.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean unfreezeMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.findByCode(ResourceUtil
                .MERCHANT_STATUS_NORMAL_CODE);
        merchant.setMerchantStatus(merchantStatus);
        return true;
    }

    /**
     * Update the credential/certificate of the Merchant.
     *
     * @param credential
     * @return
     */
    @Transactional
    @Override
    public Credential updateCredential(Credential credential) throws MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (credential == null) {
            throw new MissingRequiredFieldException("Credential is null.");
        }
        if (credential.getId() == null) {
            throw new MissingRequiredFieldException("Credential id is null.");
        }
        if (credential.getCredentialStatus() == null) {
            throw new MissingRequiredFieldException("Credential status is null.");
        }
        if (credential.getCredentialType() == null) {
            throw new MissingRequiredFieldException("Credential type is null.");
        }
        if (credential.getExpirationDate() == null) {
            throw new MissingRequiredFieldException("Credential expiration date is null.");
        }
        if (StringUtils.isBlank(credential.getContent())) {
            throw new MissingRequiredFieldException("Credential content is null.");
        }
        credential.setUpdatedTime(date);
        return credentialDao.update(credential);
    }

    /**
     * Approve the credential of the Merchant. The credential must be approved before
     * the Merchant can start the business.
     *
     * @param credential
     * @return
     */
    @Transactional
    @Override
    public Credential approveCredential(Credential credential) throws
            MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (credential == null) {
            throw new MissingRequiredFieldException("Credential is null.");
        }
        if (credential.getId() == null) {
            throw new MissingRequiredFieldException("Credential id is null.");
        }
        if (credential.getCredentialStatus() == null) {
            throw new MissingRequiredFieldException("Credential status is null.");
        }
        if (credential.getCredentialType() == null) {
            throw new MissingRequiredFieldException("Credential type is null.");
        }
        if (credential.getExpirationDate() == null) {
            throw new MissingRequiredFieldException("Credential expiration date is null.");
        }
        if (StringUtils.isBlank(credential.getContent())) {
            throw new MissingRequiredFieldException("Credential content is null.");
        }
        credential.setUpdatedTime(date);
        return credentialDao.update(credential);
    }

    /**
     * Deny the Credential of the merchant.
     *
     * @param credential
     * @return
     */
    @Transactional
    @Override
    public Credential denyCredential(Credential credential) throws MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (credential == null) {
            throw new MissingRequiredFieldException("Credential is null.");
        }
        if (credential.getId() == null) {
            throw new MissingRequiredFieldException("Credential id is null.");
        }
        if (credential.getCredentialStatus() == null) {
            throw new MissingRequiredFieldException("Credential status is null.");
        }
        if (credential.getCredentialType() == null) {
            throw new MissingRequiredFieldException("Credential type is null.");
        }
        if (credential.getExpirationDate() == null) {
            throw new MissingRequiredFieldException("Credential expiration date is null.");
        }
        if (StringUtils.isBlank(credential.getContent())) {
            throw new MissingRequiredFieldException("Credential content is null.");
        }
        credential.setUpdatedTime(date);
        return credentialDao.update(credential);
    }

    /**
     * Update merchant's encryption, key of md5/sha.
     *
     * @param encryption
     * @return
     */
    @Transactional
    @Override
    public Encryption updateEncryption(Encryption encryption) throws MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (encryption == null) {
            throw new MissingRequiredFieldException("Encryption is null.");
        }
        if (encryption.getId() == null) {
            throw new MissingRequiredFieldException("Encryption id is null.");
        }
        if (encryption.getEncryptionType() == null) {
            throw new MissingRequiredFieldException("Encryption type is null.");
        }
        if (StringUtils.isBlank(encryption.getKey())) {
            throw new MissingRequiredFieldException("Encryption key is null.");
        }
        encryption.setUpdatedTime(date);
        return encryptionDao.update(encryption);
    }

    /**
     * Update the commission fee of the merchant.
     *
     * @param fee
     * @return
     */
    @Transactional
    @Override
    public Fee updateCommissionFee(Fee fee) throws MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (fee == null) {
            throw new MissingRequiredFieldException("Fee is null.");
        }
        if (fee.getId() == null) {
            throw new MissingRequiredFieldException("Fee id is null.");
        }
        if (fee.getFeeType() == null) {
            throw new MissingRequiredFieldException("Fee type is null.");
        }
        if (fee.getValue() == null) {
            throw new MissingRequiredFieldException("Fee value is null.");
        }
        fee.setUpdatedTime(date);
        return feeDao.update(fee);
    }

    /**
     * Update the return fee of the merchant.
     *
     * @param fee
     * @return
     */
    @Transactional
    @Override
    public Fee updateReturnFee(Fee fee) throws MissingRequiredFieldException {
        Date date = Calendar.getInstance().getTime();
        if (fee == null) {
            throw new MissingRequiredFieldException("Fee is null.");
        }
        if (fee.getId() == null) {
            throw new MissingRequiredFieldException("Fee id is null.");
        }
        if (fee.getFeeType() == null) {
            throw new MissingRequiredFieldException("Fee type is null.");
        }
        if (fee.getValue() == null) {
            throw new MissingRequiredFieldException("Fee value is null.");
        }
        fee.setUpdatedTime(date);
        return feeDao.update(fee);
    }
}