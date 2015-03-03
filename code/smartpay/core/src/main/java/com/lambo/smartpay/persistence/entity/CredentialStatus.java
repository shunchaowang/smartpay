package com.lambo.smartpay.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Domain class for the status of the credential of the merchant.
 * <p/>
 * There are four types of credential: Submitted, Approved, Denied and Expired.
 * Submitted[400]: Merchant has submitted the credential and awaiting the approval of the
 * administrator;
 * Approved[500]: Administrator has approved the credential;
 * Denied[502]: Administrator has denied the credential;
 * Expired[404]: The credential has expired.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CREDENTIAL_STATUSES")
public class CredentialStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CRST_ID")
    private Long id;

    @Column(name = "CRST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CRST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CRST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CRST_CODE", nullable = false)
    private String code;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "credentialStatus")
    private Set<Credential> credentials;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Credential> credentials) {
        this.credentials = credentials;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
