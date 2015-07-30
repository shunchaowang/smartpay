package com.lambo.smartpay.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "WITHDRAWALS")
public class Withdrawal implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WDRL_ID")
    private Long id;

    @Column(name = "WDRL_CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "WDRL_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "WDRL_REMARK", nullable = true)
    private String remark;

    @Column(name = "WDRL_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "WDRL_BALANCE", nullable = false)
    private Double balance;

    @Column(name = "WDRL_AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "WDRL_DATE_START", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;

    @Column(name = "WDRL_DATE_END", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @Column(name = "WDRL_REQUESTED_BY", nullable = false)
    private Long requestedBy;

    @Column(name = "WDRL_AUDITED_BY", nullable = true)
    private Long auditedBy;

    @Column(name = "WDRL_SECURITY_RATE", nullable = true)
    private Float securityRate;

    @Column(name = "WDRL_SECURITY_DEPOSIT", nullable = true)
    private Double securityDeposit;

    @Column(name = "WDRL_SECURITY_WITHDRAWN", nullable = true)
    private Boolean securityWithdrawn;

    // association
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "WDRL_MCHT_ID", nullable = false)
    private Merchant merchant;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "WDRL_WDST_ID", nullable = false)
    private WithdrawalStatus withdrawalStatus;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY,
            mappedBy = "withdrawal", orphanRemoval = false)
    private Set<Payment> payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Long getAuditedBy() {
        return auditedBy;
    }

    public void setAuditedBy(Long auditedBy) {
        this.auditedBy = auditedBy;
    }

    public Float getSecurityRate() {
        return securityRate;
    }

    public void setSecurityRate(Float securityRate) {
        this.securityRate = securityRate;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Boolean getSecurityWithdrawn() {
        return securityWithdrawn;
    }

    public void setSecurityWithdrawn(Boolean securityWithdrawn) {
        this.securityWithdrawn = securityWithdrawn;
    }

    public WithdrawalStatus getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(
            WithdrawalStatus withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(
            Set<Payment> payments) {
        this.payments = payments;
    }
}
