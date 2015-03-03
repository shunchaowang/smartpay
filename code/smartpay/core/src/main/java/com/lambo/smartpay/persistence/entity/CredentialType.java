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
 * Domain class for the type of the credential. There is one type.
 * <p/>
 * Certificate[100]: stands for merchant certificate.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CREDENTIAL_TYPES")
public class CredentialType implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CRTP_ID")
    private Long id;

    @Column(name = "CRTP_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CRTP_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CRTP_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CRTP_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "credentialType", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
