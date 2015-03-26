package com.lambo.smartpay.core.persistence.entity;

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
 * Domain class for the status of the account for the merchant, like bank account for payment or
 * fee.
 * Account has two statuses, normal means good to use, and frozen means cannot use now.
 * AccountStatus has a unique code individually:
 * Code 200 means normal;
 * Code 400 means frozen.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "ACCOUNT_STATUSES")
public class AccountStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACST_ID")
    private Long id;

    @Column(name = "ACST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "ACST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "ACST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "ACST_CODE", nullable = false)
    private String code;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY,
            mappedBy = "accountStatus", orphanRemoval = false)
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
