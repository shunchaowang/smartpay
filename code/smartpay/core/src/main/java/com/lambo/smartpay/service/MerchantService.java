package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.Merchant;

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
    Boolean freezeMerchant(Long id);

    /**
     * Unfreeze a Merchant by updating the MerchantStatus from Frozen to Normal.
     * The Credential should have already been approved prior the call.
     *
     * @param id
     * @return
     */
    Boolean unfreezeMerchant(Long id);

    /**
     * Update the credential/certificate of the Merchant.
     *
     * @param credential
     * @return
     */
    Credential updateCredential(Credential credential);

    /**
     * Approve the credential of the Merchant. The credential must be approved before
     * the Merchant can start the business.
     *
     * @param credential
     * @return
     */
    Credential approveCredential(Credential credential);

    /**
     * Deny the Credential of the merchant.
     *
     * @param credential
     * @return
     */
    Credential denyCredential(Credential credential);

    /**
     * Update merchant's encryption, key of md5/sha.
     *
     * @param encryption
     * @return
     */
    Encryption updateEncryption(Encryption encryption);

    /**
     * Update the commission fee of the merchant.
     *
     * @param fee
     * @return
     */
    Fee updateCommissionFee(Fee fee);

    /**
     * Update the return fee of the merchant.
     *
     * @param fee
     * @return
     */
    Fee updateReturnFee(Fee fee);
}
