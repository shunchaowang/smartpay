package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.Merchant;

/**
 * Created by swang on 3/10/2015.
 */
public interface MerchantService extends GenericQueryService<Merchant, Long> {

    Merchant findByName(String name);

    /**
     * Freeze a Merchant by updating the MerchantStatus from Normal to Frozen.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    Merchant freezeMerchant(Long id) throws NoSuchEntityException;

    /**
     * Unfreeze a Merchant by updating the MerchantStatus from Frozen to Normal.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    Merchant unfreezeMerchant(Long id) throws NoSuchEntityException;

    /**
     * Update the credential/certificate of the Merchant.
     *
     * @param credential
     * @return
     */
    Credential updateCredential(Credential credential) throws MissingRequiredFieldException;

    /**
     * Approve the credential of the Merchant. The credential must be approved before
     * the Merchant can start the business.
     *
     * @param credential
     * @return
     */
    Credential approveCredential(Credential credential) throws MissingRequiredFieldException;

    /**
     * Deny the Credential of the merchant.
     *
     * @param credential
     * @return
     */
    Credential denyCredential(Credential credential) throws MissingRequiredFieldException;

    /**
     * Update merchant's encryption, key of md5/sha.
     *
     * @param encryption
     * @return
     */
    Encryption updateEncryption(Encryption encryption) throws MissingRequiredFieldException;

    /**
     * Update the commission fee of the merchant.
     *
     * @param fee
     * @return
     */
    Fee updateCommissionFee(Fee fee) throws MissingRequiredFieldException;

    /**
     * Update the return fee of the merchant.
     *
     * @param fee
     * @return
     */
    Fee updateReturnFee(Fee fee) throws MissingRequiredFieldException;

    Boolean canOperate(Long id) throws NoSuchEntityException;
}
