package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Merchant;
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

//    // Commission fee
//    private Float commissionFeeValue;
//    private String commissionFeeRemark;
//    private Long commissionFeeTypeId;
//    private String commissionFeeTypeName;
//
//    // Return fee
//    private Float returnFeeValue;
//    private String returnFeeRemark;
//    private Long returnFeeTypeId;
//    private String returnFeeTypeName;

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

//        // Commission Fee
//        commissionFeeRemark = merchant.getCommissionFee().getRemark();
//        commissionFeeTypeId = merchant.getCommissionFee().getFeeType().getId();
//        commissionFeeTypeName = merchant.getCommissionFee().getFeeType().getName();
//        commissionFeeValue = merchant.getCommissionFee().getValue();
//
//        // Return Fee
//        returnFeeRemark = merchant.getReturnFee().getRemark();
//        returnFeeTypeId = merchant.getReturnFee().getFeeType().getId();
//        returnFeeTypeName = merchant.getReturnFee().getFeeType().getName();
//        returnFeeValue = merchant.getReturnFee().getValue();

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

//    public Float getCommissionFeeValue() {
//        return commissionFeeValue;
//    }
//
//    public void setCommissionFeeValue(Float commissionFeeValue) {
//        this.commissionFeeValue = commissionFeeValue;
//    }
//
//    public String getCommissionFeeRemark() {
//        return commissionFeeRemark;
//    }
//
//    public void setCommissionFeeRemark(String commissionFeeRemark) {
//        this.commissionFeeRemark = commissionFeeRemark;
//    }
//
//    public Long getCommissionFeeTypeId() {
//        return commissionFeeTypeId;
//    }
//
//    public void setCommissionFeeTypeId(Long commissionFeeTypeId) {
//        this.commissionFeeTypeId = commissionFeeTypeId;
//    }
//
//    public String getCommissionFeeTypeName() {
//        return commissionFeeTypeName;
//    }
//
//    public void setCommissionFeeTypeName(String commissionFeeTypeName) {
//        this.commissionFeeTypeName = commissionFeeTypeName;
//    }
//
//    public Float getReturnFeeValue() {
//        return returnFeeValue;
//    }
//
//    public void setReturnFeeValue(Float returnFeeValue) {
//        this.returnFeeValue = returnFeeValue;
//    }
//
//    public String getReturnFeeRemark() {
//        return returnFeeRemark;
//    }
//
//    public void setReturnFeeRemark(String returnFeeRemark) {
//        this.returnFeeRemark = returnFeeRemark;
//    }
//
//    public Long getReturnFeeTypeId() {
//        return returnFeeTypeId;
//    }
//
//    public void setReturnFeeTypeId(Long returnFeeTypeId) {
//        this.returnFeeTypeId = returnFeeTypeId;
//    }
//
//    public String getReturnFeeTypeName() {
//        return returnFeeTypeName;
//    }
//
//    public void setReturnFeeTypeName(String returnFeeTypeName) {
//        this.returnFeeTypeName = returnFeeTypeName;
//    }

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
