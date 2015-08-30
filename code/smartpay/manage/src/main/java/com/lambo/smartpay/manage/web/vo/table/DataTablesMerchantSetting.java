package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.util.ResourceProperties;

/**
 * Created by swang on 3/20/2015.
 */
public class DataTablesMerchantSetting {

    private Long id;
    private String identity;
    private String name;
    private Long encryptionId;
    private String encryptionType;
    private String encryptionKey;

    private Long commissionVisaFeeId;
    private Long commissionVisaFeeTypeId;
    private String commissionVisaFeeTypeName;
    private Float commissionVisaFeeValue;

    private Long commissionMasterFeeId;
    private Long commissionMasterFeeTypeId;
    private String commissionMasterFeeTypeName;
    private Float commissionMasterFeeValue;

    private Long commissionJcbFeeId;
    private Long commissionJcbFeeTypeId;
    private String commissionJcbFeeTypeName;
    private Float commissionJcbFeeValue;

    private Long withdrawalSecurityFeeId;
    private Long withdrawalSecurityFeeTypeId;
    private String withdrawalSecurityFeeTypeName;
    private Float withdrawalSecurityFeeValue;

    private Long withdrawalSettingMinDays;
    private Long withdrawalSettingMaxDays;


    public DataTablesMerchantSetting(Merchant merchant) {
        id = merchant.getId();
        identity = merchant.getIdentity();
        name = merchant.getName();
        encryptionId = merchant.getEncryption().getId();
        encryptionKey = merchant.getEncryption().getKey();
        encryptionType = merchant.getEncryption().getEncryptionType().getName();

        // set all fees
        if (merchant.getFees() != null) {
            for (Fee fee : merchant.getFees()) {
                switch (fee.getFeeCategory().getCode()) {
                    case ResourceProperties.FEE_CATEGORY_VISA_CODE:
                        commissionVisaFeeValue = fee.getValue();
                        commissionVisaFeeId = fee.getId();
                        commissionVisaFeeTypeId = fee.getFeeType().getId();
                        commissionVisaFeeTypeName = fee.getFeeType().getName();
                        break;
                    case ResourceProperties.FEE_CATEGORY_MASTER_CODE:
                        commissionMasterFeeValue = fee.getValue();
                        commissionMasterFeeId = fee.getId();
                        commissionMasterFeeTypeId = fee.getFeeType().getId();
                        commissionMasterFeeTypeName = fee.getFeeType().getName();
                        break;
                    case ResourceProperties.FEE_CATEGORY_JCB_CODE:
                        commissionJcbFeeValue = fee.getValue();
                        commissionJcbFeeId = fee.getId();
                        commissionJcbFeeTypeId = fee.getFeeType().getId();
                        commissionJcbFeeTypeName = fee.getFeeType().getName();
                        break;
                    case ResourceProperties.FEE_CATEGORY_WITHDRAWAL_SECURITY_CODE:
                        withdrawalSecurityFeeValue = fee.getValue();
                        withdrawalSecurityFeeId = fee.getId();
                        withdrawalSecurityFeeTypeId = fee.getFeeType().getId();
                        withdrawalSecurityFeeTypeName = fee.getFeeType().getName();
                        break;
                    default:
                        break;
                }
            }
        }

        if (merchant.getWithdrawalSetting() != null) {
            withdrawalSettingMinDays = merchant.getWithdrawalSetting().getMinDays();
            withdrawalSettingMaxDays = merchant.getWithdrawalSetting().getMaxDays();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEncryptionId() {
        return encryptionId;
    }

    public void setEncryptionId(Long encryptionId) {
        this.encryptionId = encryptionId;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public Long getCommissionVisaFeeId() {
        return commissionVisaFeeId;
    }

    public void setCommissionVisaFeeId(Long commissionVisaFeeId) {
        this.commissionVisaFeeId = commissionVisaFeeId;
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

    public Float getCommissionVisaFeeValue() {
        return commissionVisaFeeValue;
    }

    public void setCommissionVisaFeeValue(Float commissionVisaFeeValue) {
        this.commissionVisaFeeValue = commissionVisaFeeValue;
    }

    public Long getCommissionMasterFeeId() {
        return commissionMasterFeeId;
    }

    public void setCommissionMasterFeeId(Long commissionMasterFeeId) {
        this.commissionMasterFeeId = commissionMasterFeeId;
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

    public Float getCommissionMasterFeeValue() {
        return commissionMasterFeeValue;
    }

    public void setCommissionMasterFeeValue(Float commissionMasterFeeValue) {
        this.commissionMasterFeeValue = commissionMasterFeeValue;
    }

    public Long getCommissionJcbFeeId() {
        return commissionJcbFeeId;
    }

    public void setCommissionJcbFeeId(Long commissionJcbFeeId) {
        this.commissionJcbFeeId = commissionJcbFeeId;
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

    public Float getCommissionJcbFeeValue() {
        return commissionJcbFeeValue;
    }

    public void setCommissionJcbFeeValue(Float commissionJcbFeeValue) {
        this.commissionJcbFeeValue = commissionJcbFeeValue;
    }

    public Long getWithdrawalSecurityFeeId() {
        return withdrawalSecurityFeeId;
    }

    public void setWithdrawalSecurityFeeId(Long withdrawalSecurityFeeId) {
        this.withdrawalSecurityFeeId = withdrawalSecurityFeeId;
    }

    public Long getWithdrawalSecurityFeeTypeId() {
        return withdrawalSecurityFeeTypeId;
    }

    public void setWithdrawalSecurityFeeTypeId(Long withdrawalSecurityFeeTypeId) {
        this.withdrawalSecurityFeeTypeId = withdrawalSecurityFeeTypeId;
    }

    public String getWithdrawalSecurityFeeTypeName() {
        return withdrawalSecurityFeeTypeName;
    }

    public void setWithdrawalSecurityFeeTypeName(String withdrawalSecurityFeeTypeName) {
        this.withdrawalSecurityFeeTypeName = withdrawalSecurityFeeTypeName;
    }

    public Float getWithdrawalSecurityFeeValue() {
        return withdrawalSecurityFeeValue;
    }

    public void setWithdrawalSecurityFeeValue(Float withdrawalSecurityFeeValue) {
        this.withdrawalSecurityFeeValue = withdrawalSecurityFeeValue;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Long getWithdrawalSettingMinDays() {
        return withdrawalSettingMinDays;
    }

    public void setWithdrawalSettingMinDays(Long withdrawalSettingMinDays) {
        this.withdrawalSettingMinDays = withdrawalSettingMinDays;
    }

    public Long getWithdrawalSettingMaxDays() {
        return withdrawalSettingMaxDays;
    }

    public void setWithdrawalSettingMaxDays(Long withdrawalSettingMaxDays) {
        this.withdrawalSettingMaxDays = withdrawalSettingMaxDays;
    }
}
