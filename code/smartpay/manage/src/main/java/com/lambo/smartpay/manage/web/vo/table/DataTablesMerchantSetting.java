package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Merchant;

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
    private Long commissionFeeId;
    private String commissionFeeType;
    private Float commissionFeeValue;
    private Long returnFeeId;
    private String returnFeeType;
    private Float returnFeeValue;

    public DataTablesMerchantSetting(Merchant merchant) {
        id = merchant.getId();
        identity = merchant.getIdentity();
        name = merchant.getName();
        encryptionId = merchant.getEncryption().getId();
        encryptionKey = merchant.getEncryption().getKey();
        encryptionType = merchant.getEncryption().getEncryptionType().getName();

        commissionFeeId = merchant.getCommissionFee().getId();
        commissionFeeValue = merchant.getCommissionFee().getValue();
        commissionFeeType = merchant.getCommissionFee().getFeeType().getName();

        returnFeeId = merchant.getReturnFee().getId();
        returnFeeValue = merchant.getReturnFee().getValue();
        returnFeeType = merchant.getReturnFee().getFeeType().getName();
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

    public Long getCommissionFeeId() {
        return commissionFeeId;
    }

    public void setCommissionFeeId(Long commissionFeeId) {
        this.commissionFeeId = commissionFeeId;
    }

    public String getCommissionFeeType() {
        return commissionFeeType;
    }

    public void setCommissionFeeType(String commissionFeeType) {
        this.commissionFeeType = commissionFeeType;
    }

    public Float getCommissionFeeValue() {
        return commissionFeeValue;
    }

    public void setCommissionFeeValue(Float commissionFeeValue) {
        this.commissionFeeValue = commissionFeeValue;
    }

    public Long getReturnFeeId() {
        return returnFeeId;
    }

    public void setReturnFeeId(Long returnFeeId) {
        this.returnFeeId = returnFeeId;
    }

    public String getReturnFeeType() {
        return returnFeeType;
    }

    public void setReturnFeeType(String returnFeeType) {
        this.returnFeeType = returnFeeType;
    }

    public Float getReturnFeeValue() {
        return returnFeeValue;
    }

    public void setReturnFeeValue(Float returnFeeValue) {
        this.returnFeeValue = returnFeeValue;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
