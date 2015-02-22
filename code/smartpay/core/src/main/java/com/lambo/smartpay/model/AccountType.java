package com.lambo.smartpay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.rmi.Naming;
import java.util.Set;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "ACCOUNT_TYPES")
public class AccountType implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACTP_ID")
    private Long id;

    @Column(name = "ACTP_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "ACTP_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "ACTP_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "ACTP_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "accountType")
    private Set<Account> accounts;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
