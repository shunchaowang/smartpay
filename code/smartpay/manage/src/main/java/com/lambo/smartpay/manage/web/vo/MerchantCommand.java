package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 3/16/2015.
 */
public class MerchantCommand {

    private Long id;
    private String identity;
    private String name;
    private String address;
    private String contact;
    private String tel;
    private String email;
    private String createdTime;
    private String updatedTime;
    private String remark;
    private Boolean active;

    // many to one relationship
    private String merchantStatusName;
    private Long merchantStatusId;

    // owning relationships
    // Credential
    private String credentialContent;
    private String credentialExpirationTime;
    private String credentialRemark;
    private Long credentialTypeId;
    private String credentialTypeName;
    private Long credentialStatusId;
    private String credentialStatusName;

    // Encryption
    private String encryptionKey;
    private String encryptionRemark;
    private Long encryptionTypeId;
    private String encryptionTypeName;


    // fee for different cards
    private Float commissionVisaFeeValue;
    private String commissionVisaFeeRemark;
    private Long commissionVisaFeeTypeId;
    private String commissionVisaFeeTypeName;
    private Float commissionMasterFeeValue;
    private String commissionMasterFeeRemark;
    private Long commissionMasterFeeTypeId;
    private String commissionMasterFeeTypeName;
    private Float commissionJcbFeeValue;
    private String commissionJcbFeeRemark;
    private Long commissionJcbFeeTypeId;
    private String commissionJcbFeeTypeName;

    // withdraw settings
    private Float withdrawFeeValue;
    private String withdrawFeeRemark;
    private Long withdrawFeeTypeId;
    private String withdrawFeeTypeName;

    private String withdrawSettingRemark;
    private String withdrawSettingCreatedTime;
    private String withdrawSettingUpdatedTime;
    private Long withdrawSettingMinDays;
    private Long withdrawSettingMaxDays;

    public MerchantCommand() {
    }

    public MerchantCommand(Merchant merchant) {

        id = merchant.getId();
        identity = merchant.getIdentity();
        merchantStatusId = merchant.getMerchantStatus().getId();
        merchantStatusName = merchant.getMerchantStatus().getName();
        name = merchant.getName();
        active = merchant.getActive();
        address = merchant.getAddress();
        contact = merchant.getContact();
        tel = merchant.getTel();
        email = merchant.getEmail();
        remark = merchant.getRemark();

        // Credential info
        credentialContent = merchant.getCredential().getContent();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        credentialExpirationTime =
                dateFormat.format(merchant.getCredential().getExpirationDate());
        credentialRemark = merchant.getCredential().getRemark();
        credentialStatusId = merchant.getCredential().getCredentialStatus()
                .getId();
        credentialStatusName = merchant.getCredential().getCredentialStatus().getName();
        credentialTypeId = merchant.getCredential().getCredentialType().getId();
        credentialTypeName = merchant.getCredential().getCredentialType().getName();

        // Encryption info
        encryptionKey = merchant.getEncryption().getKey();
        encryptionRemark = merchant.getEncryption().getRemark();
        encryptionTypeId = merchant.getEncryption().getEncryptionType().getId();
        encryptionTypeName = merchant.getEncryption().getEncryptionType().getName();


        // set all fees
        if (merchant.getFees() != null) {
            for (Fee fee : merchant.getFees()) {
                switch (fee.getFeeCategory().getCode()) {
                    case ResourceProperties.FEE_CATEGORY_VISA_CODE:
                        commissionVisaFeeValue = fee.getValue();
                        commissionVisaFeeRemark = fee.getRemark();
                        commissionVisaFeeTypeId = fee.getFeeType().getId();
                        commissionVisaFeeTypeName = fee.getFeeType().getName();
                        break;
                    case ResourceProperties.FEE_CATEGORY_MASTER_CODE:
                        commissionMasterFeeValue = fee.getValue();
                        commissionMasterFeeRemark = fee.getRemark();
                        commissionMasterFeeTypeId = fee.getFeeType().getId();
                        commissionMasterFeeTypeName = fee.getFeeType().getName();
                        break;
                    case ResourceProperties.FEE_CATEGORY_JCB_CODE:
                        commissionJcbFeeValue = fee.getValue();
                        commissionJcbFeeRemark = fee.getRemark();
                        commissionJcbFeeTypeId = fee.getFeeType().getId();
                        commissionJcbFeeTypeName = fee.getFeeType().getName();
                        break;
                    default:
                        break;
                }
            }
        }

        // set withdrawal settings
        if (merchant.getWithdrawalSetting() != null) {
            withdrawSettingCreatedTime = dateFormat.format(merchant.getWithdrawalSetting()
                    .getCreatedTime());
            if (merchant.getWithdrawalSetting().getUpdatedTime() != null) {
                withdrawSettingUpdatedTime = dateFormat.format(merchant.getWithdrawalSetting()
                        .getUpdatedTime());
            }
            withdrawSettingRemark = merchant.getWithdrawalSetting().getRemark();
            withdrawSettingMinDays = merchant.getWithdrawalSetting().getMinDays();
            withdrawSettingMaxDays = merchant.getWithdrawalSetting().getMaxDays();
            Fee fee = merchant.getWithdrawalSetting().getSecurityFee();
            withdrawFeeValue = fee.getValue();
            withdrawFeeRemark = fee.getRemark();
            withdrawFeeTypeId = fee.getFeeType().getId();
            withdrawFeeTypeName = fee.getFeeType().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getMerchantStatusName() {
        return merchantStatusName;
    }

    public void setMerchantStatusName(String merchantStatusName) {
        this.merchantStatusName = merchantStatusName;
    }

    public Long getMerchantStatusId() {
        return merchantStatusId;
    }

    public void setMerchantStatusId(Long merchantStatusId) {
        this.merchantStatusId = merchantStatusId;
    }

    public String getCredentialContent() {
        return credentialContent;
    }

    public void setCredentialContent(String credentialContent) {
        this.credentialContent = credentialContent;
    }

    public String getCredentialExpirationTime() {
        return credentialExpirationTime;
    }

    public void setCredentialExpirationTime(String credentialExpirationTime) {
        this.credentialExpirationTime = credentialExpirationTime;
    }

    public String getCredentialRemark() {
        return credentialRemark;
    }

    public void setCredentialRemark(String credentialRemark) {
        this.credentialRemark = credentialRemark;
    }

    public Long getCredentialTypeId() {
        return credentialTypeId;
    }

    public void setCredentialTypeId(Long credentialTypeId) {
        this.credentialTypeId = credentialTypeId;
    }

    public String getCredentialTypeName() {
        return credentialTypeName;
    }

    public void setCredentialTypeName(String credentialTypeName) {
        this.credentialTypeName = credentialTypeName;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getEncryptionRemark() {
        return encryptionRemark;
    }

    public void setEncryptionRemark(String encryptionRemark) {
        this.encryptionRemark = encryptionRemark;
    }

    public Long getEncryptionTypeId() {
        return encryptionTypeId;
    }

    public void setEncryptionTypeId(Long encryptionTypeId) {
        this.encryptionTypeId = encryptionTypeId;
    }

    public String getEncryptionTypeName() {
        return encryptionTypeName;
    }

    public void setEncryptionTypeName(String encryptionTypeName) {
        this.encryptionTypeName = encryptionTypeName;
    }

    public Float getCommissionVisaFeeValue() {
        return commissionVisaFeeValue;
    }

    public void setCommissionVisaFeeValue(Float commissionVisaFeeValue) {
        this.commissionVisaFeeValue = commissionVisaFeeValue;
    }

    public String getCommissionVisaFeeRemark() {
        return commissionVisaFeeRemark;
    }

    public void setCommissionVisaFeeRemark(String commissionVisaFeeRemark) {
        this.commissionVisaFeeRemark = commissionVisaFeeRemark;
    }

    public Long getCommissionVisaFeeTypeId() {
        return commissionVisaFeeTypeId;
    }

    public void setCommissionVisaFeeTypeId(Long commissionVisaFeeTypeId) {
        this.commissionVisaFeeTypeId = commissionVisaFeeTypeId;
    }

    public String getCommissionVisaFeeTypeName() {
        return commissionVisaFeeTypeName;
    }

    public void setCommissionVisaFeeTypeName(String commissionVisaFeeTypeName) {
        this.commissionVisaFeeTypeName = commissionVisaFeeTypeName;
    }

    public Float getCommissionMasterFeeValue() {
        return commissionMasterFeeValue;
    }

    public void setCommissionMasterFeeValue(Float commissionMasterFeeValue) {
        this.commissionMasterFeeValue = commissionMasterFeeValue;
    }

    public String getCommissionMasterFeeRemark() {
        return commissionMasterFeeRemark;
    }

    public void setCommissionMasterFeeRemark(String commissionMasterFeeRemark) {
        this.commissionMasterFeeRemark = commissionMasterFeeRemark;
    }

    public Long getCommissionMasterFeeTypeId() {
        return commissionMasterFeeTypeId;
    }

    public void setCommissionMasterFeeTypeId(Long commissionMasterFeeTypeId) {
        this.commissionMasterFeeTypeId = commissionMasterFeeTypeId;
    }

    public String getCommissionMasterFeeTypeName() {
        return commissionMasterFeeTypeName;
    }

    public void setCommissionMasterFeeTypeName(String commissionMasterFeeTypeName) {
        this.commissionMasterFeeTypeName = commissionMasterFeeTypeName;
    }

    public Float getCommissionJcbFeeValue() {
        return commissionJcbFeeValue;
    }

    public void setCommissionJcbFeeValue(Float commissionJcbFeeValue) {
        this.commissionJcbFeeValue = commissionJcbFeeValue;
    }

    public String getCommissionJcbFeeRemark() {
        return commissionJcbFeeRemark;
    }

    public void setCommissionJcbFeeRemark(String commissionJcbFeeRemark) {
        this.commissionJcbFeeRemark = commissionJcbFeeRemark;
    }

    public Long getCommissionJcbFeeTypeId() {
        return commissionJcbFeeTypeId;
    }

    public void setCommissionJcbFeeTypeId(Long commissionJcbFeeTypeId) {
        this.commissionJcbFeeTypeId = commissionJcbFeeTypeId;
    }

    public String getCommissionJcbFeeTypeName() {
        return commissionJcbFeeTypeName;
    }

    public void setCommissionJcbFeeTypeName(String commissionJcbFeeTypeName) {
        this.commissionJcbFeeTypeName = commissionJcbFeeTypeName;
    }

    public Float getWithdrawFeeValue() {
        return withdrawFeeValue;
    }

    public void setWithdrawFeeValue(Float withdrawFeeValue) {
        this.withdrawFeeValue = withdrawFeeValue;
    }

    public String getWithdrawFeeRemark() {
        return withdrawFeeRemark;
    }

    public void setWithdrawFeeRemark(String withdrawFeeRemark) {
        this.withdrawFeeRemark = withdrawFeeRemark;
    }

    public Long getWithdrawFeeTypeId() {
        return withdrawFeeTypeId;
    }

    public void setWithdrawFeeTypeId(Long withdrawFeeTypeId) {
        this.withdrawFeeTypeId = withdrawFeeTypeId;
    }

    public String getWithdrawFeeTypeName() {
        return withdrawFeeTypeName;
    }

    public void setWithdrawFeeTypeName(String withdrawFeeTypeName) {
        this.withdrawFeeTypeName = withdrawFeeTypeName;
    }

    public String getWithdrawSettingRemark() {
        return withdrawSettingRemark;
    }

    public void setWithdrawSettingRemark(String withdrawSettingRemark) {
        this.withdrawSettingRemark = withdrawSettingRemark;
    }

    public String getWithdrawSettingCreatedTime() {
        return withdrawSettingCreatedTime;
    }

    public void setWithdrawSettingCreatedTime(String withdrawSettingCreatedTime) {
        this.withdrawSettingCreatedTime = withdrawSettingCreatedTime;
    }

    public String getWithdrawSettingUpdatedTime() {
        return withdrawSettingUpdatedTime;
    }

    public void setWithdrawSettingUpdatedTime(String withdrawSettingUpdatedTime) {
        this.withdrawSettingUpdatedTime = withdrawSettingUpdatedTime;
    }

    public Long getWithdrawSettingMinDays() {
        return withdrawSettingMinDays;
    }

    public void setWithdrawSettingMinDays(Long withdrawSettingMinDays) {
        this.withdrawSettingMinDays = withdrawSettingMinDays;
    }

    public Long getWithdrawSettingMaxDays() {
        return withdrawSettingMaxDays;
    }

    public void setWithdrawSettingMaxDays(Long withdrawSettingMaxDays) {
        this.withdrawSettingMaxDays = withdrawSettingMaxDays;
    }

    public Long getCredentialStatusId() {
        return credentialStatusId;
    }

    public void setCredentialStatusId(Long credentialStatusId) {
        this.credentialStatusId = credentialStatusId;
    }

    public String getCredentialStatusName() {
        return credentialStatusName;
    }

    public void setCredentialStatusName(String credentialStatusName) {
        this.credentialStatusName = credentialStatusName;
    }
}
