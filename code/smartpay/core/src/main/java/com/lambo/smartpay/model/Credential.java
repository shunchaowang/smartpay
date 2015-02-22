package com.lambo.smartpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CREDENTIALS")
public class Credential implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CRDT_ID")
    private Long id;

    @Column(name = "CRDT_CONTENT", length = 32, nullable = false)
    private String content;

    @Column(name = "CRDT_EXPIRATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "CRDT_REMARK", length = 255)
    private String remark;

    @Column(name = "CRDT_CRETED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "CRDT_UPDATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "CRDT_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "CRDT_CRST_ID", nullable = false)
    private CredentialStatus credentialStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "CRDT_CRTP_ID", nullable = false)
    private CredentialType credentialType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public CredentialStatus getCredentialStatus() {
        return credentialStatus;
    }

    public void setCredentialStatus(CredentialStatus credentialStatus) {
        this.credentialStatus = credentialStatus;
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
