package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.CredentialDao;
import com.lambo.smartpay.core.persistence.dao.EncryptionDao;
import com.lambo.smartpay.core.persistence.dao.FeeDao;
import com.lambo.smartpay.core.persistence.dao.MerchantDao;
import com.lambo.smartpay.core.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.core.util.TransactionDebugUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/10/2015.
 * Modified by linly on 3/15/2015.
 */
@Service("merchantService")
public class MerchantServiceImpl extends GenericQueryServiceImpl<Merchant, Long>
        implements MerchantService {
    //TODO WE WANT TO MAKE CONTROLLER FILE ALL REQUIRED FIELDS, AND SERVICE CHECK ONLY
    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Resource
    private MerchantDao merchantDao;
    @Resource
    private MerchantStatusDao merchantStatusDao;
    @Resource
    private CredentialDao credentialDao;
    @Resource
    private FeeDao feeDao;
    @Resource
    private EncryptionDao encryptionDao;

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

        TransactionDebugUtil.transactionRequired("MerchantServiceImpl.create");
        Date date = Calendar.getInstance().getTime();
        if (merchant == null) {
            throw new MissingRequiredFieldException("Merchant is null.");
        }
        if (StringUtils.isBlank(merchant.getName())) {
            throw new MissingRequiredFieldException("Merchant name is null.");
        }
        if (StringUtils.isBlank(merchant.getIdentity())) {
            throw new MissingRequiredFieldException("Merchant identity is null.");
        }
        if (merchantDao.findByIdentity(merchant.getIdentity()) != null) {
            throw new NotUniqueException("Merchant with identity " + merchant.getIdentity() +
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
//        String key = RandomStringUtils.randomNumeric(ResourceProperties.ENCRYPTION_KEY_LENGTH);
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
        Date date = Calendar.getInstance().getTime();
        if (merchant == null) {
            throw new MissingRequiredFieldException("Merchant is null.");
        }
        if (StringUtils.isBlank(merchant.getName())) {
            throw new MissingRequiredFieldException("Merchant name is null.");
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
        merchant.getCredential().setUpdatedTime(date);

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
        merchant.getCommissionFee().setUpdatedTime(date);

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
        merchant.getReturnFee().setUpdatedTime(date);

        // set createdTime
        merchant.setUpdatedTime(date);

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
        merchant.getEncryption().setUpdatedTime(date);

        return merchantDao.update(merchant);
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
        merchantDao.delete(id);
        return merchant;
    }

    @Override
    public Merchant findByIdentity(String identity) {
        if (StringUtils.isBlank(identity)) {
            logger.info("Identity is null.");
            return null;
        }
        return merchantDao.findByIdentity(identity);
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
    public Merchant freezeMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.findByCode(ResourceProperties
                .MERCHANT_STATUS_FROZEN_CODE);
        merchant.setMerchantStatus(merchantStatus);
        merchant = merchantDao.update(merchant);
        return merchant;
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
    public Merchant unfreezeMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.findByCode(ResourceProperties
                .MERCHANT_STATUS_NORMAL_CODE);
        merchant.setMerchantStatus(merchantStatus);
        merchant = merchantDao.update(merchant);
        return merchant;
    }

    /**
     * Archive a Merchant by updating the Merchant to be deactivated.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Merchant archiveMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        merchant.setActive(false);
        merchant = merchantDao.update(merchant);
        return merchant;
    }

    /**
     * Restore a Merchant by updating the Merchant to be active.
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Merchant restoreMerchant(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        merchant.setActive(true);
        merchant = merchantDao.update(merchant);
        return merchant;
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

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param merchant contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Merchant merchant, String search) {

        return merchantDao.countByCriteria(merchant, search);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param merchant contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Merchant> findByCriteria(Merchant merchant, String search,
                                         Integer start, Integer length,
                                         String order, ResourceProperties.JpaOrderDir orderDir) {

        return merchantDao.findByCriteria(merchant, search, start, length, order, orderDir);
    }

    @Override
    public List<Merchant> getAll() {
        return merchantDao.getAll();
    }

    @Override
    public Long countAll() {
        return merchantDao.countAll();
    }

    @Override
    public Boolean canOperate(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Merchant merchant = merchantDao.get(id);
        if (merchant == null) {
            throw new NoSuchEntityException("Merchant with id " + id +
                    " does not exist.");
        }
        MerchantStatus merchantStatus = merchantStatusDao.findByCode(ResourceProperties
                .MERCHANT_STATUS_NORMAL_CODE);
        return merchant.getMerchantStatus().equals(merchantStatus);
    }
}
