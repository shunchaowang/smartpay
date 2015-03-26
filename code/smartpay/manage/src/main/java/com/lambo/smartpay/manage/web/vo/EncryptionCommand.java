package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Encryption;

/**
 * Created by swang on 3/23/2015.
 */
public class EncryptionCommand {

    private Long id;
    // Encryption
    private String encryptionKey;
    private String encryptionRemark;
    private Long encryptionTypeId;
    private String encryptionTypeName;

    public EncryptionCommand() {
    }

    public EncryptionCommand(Encryption encryption) {
        id = encryption.getId();
        encryptionKey = encryption.getKey();
        encryptionTypeId = encryption.getEncryptionType().getId();
        encryptionTypeName = encryption.getEncryptionType().getName();
        encryptionRemark = encryption.getRemark();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
