package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Merchant;

import java.text.SimpleDateFormat;

/**
 * Created by swang on 3/20/2015.
 */
public class DataTablesCredential {

    private Long id;
    private Long credentialId;
    private String name;
    private String content;
    private String expirationTime;
    private String type;
    private String status;

    public DataTablesCredential(Merchant merchant) {
        id = merchant.getId();
        credentialId = merchant.getCredential().getId();
        name = merchant.getName();
        content = merchant.getCredential().getContent();
        SimpleDateFormat format = new SimpleDateFormat("MMM/dd/yyyy");
        expirationTime = format.format(merchant.getCredential().getExpirationDate());
        type = merchant.getCredential().getCredentialType().getName();
        status = merchant.getCredential().getCredentialStatus().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
