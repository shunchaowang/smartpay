package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.MerchantDao;
import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.service.GenericQueryService;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.util.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by swang on 3/10/2015.
 */
@Repository("merchantService")
public class MerchantServiceImpl implements MerchantService, GenericQueryService<Merchant, Long> {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private MerchantDao merchantDao;

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
        return null;
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
        return null;
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
        return null;
    }

    @Override
    public Merchant create(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        return null;
    }

    @Override
    public Merchant get(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public Merchant update(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        return null;
    }

    @Override
    public Merchant delete(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public Merchant findByName(String name) {
        return null;
    }

    /**
     * Freeze a Merchant by updating the MerchantStatus from Normal to Frozen.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    @Override
    public Boolean freezeMerchant(Long id) {
        return null;
    }

    /**
     * Unfreeze a Merchant by updating the MerchantStatus from Frozen to Normal.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    @Override
    public Boolean unfreezeMerchant(Long id) {
        return null;
    }

    /**
     * Update the credential/certificate of the Merchant.
     *
     * @param credential
     * @return
     */
    @Override
    public Credential updateCredential(Credential credential) {
        return null;
    }

    /**
     * Approve the credential of the Merchant. The credential must be approved before
     * the Merchant can start the business.
     *
     * @param credential
     * @return
     */
    @Override
    public Credential approveCredential(Credential credential) {
        return null;
    }

    /**
     * Deny the Credential of the merchant.
     *
     * @param credential
     * @return
     */
    @Override
    public Credential denyCredential(Credential credential) {
        return null;
    }

    /**
     * Update merchant's encryption, key of md5/sha.
     *
     * @param encryption
     * @return
     */
    @Override
    public Encryption updateEncryption(Encryption encryption) {
        return null;
    }

    /**
     * Update the commission fee of the merchant.
     *
     * @param fee
     * @return
     */
    @Override
    public Fee updateCommissionFee(Fee fee) {
        return null;
    }

    /**
     * Update the return fee of the merchant.
     *
     * @param fee
     * @return
     */
    @Override
    public Fee updateReturnFee(Fee fee) {
        return null;
    }
}
