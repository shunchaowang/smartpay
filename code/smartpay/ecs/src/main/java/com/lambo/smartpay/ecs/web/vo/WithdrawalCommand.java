package com.lambo.smartpay.ecs.web.vo;

import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;

import java.util.Date;
import java.util.Set;

/**
 * Created by chensf on 8/2/2015.
 */
public class WithdrawalCommand {

    private Long id;
    private Date createdTime;
    private Date updatedTime;
    private String remark;
    private Boolean active;
    private Double balance;
    private Double amount;
    private Date dateStart;
    private Date dateEnd;
    private Long requestedBy;
    private Long auditedBy;
    private Float securityRate;
    private Double securityDeposit;
    private Boolean securityWithdrawn;
    private Merchant merchant;
    private WithdrawalStatus withdrawalStatus;
    private Set<Payment> payments;
    private Float refundAmt;
    private Float totalFee;
    private String dateRange;
    private String requester;
    private String auditer;

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getAuditer() {
        return auditer;
    }

    public void setAuditer(String auditer) {
        this.auditer = auditer;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

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

    public Boolean isActive() {
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

    public Boolean isSecurityWithdrawn() {
        return securityWithdrawn;
    }

    public void setSecurityWithdrawn(Boolean securityWithdrawn) {
        this.securityWithdrawn = securityWithdrawn;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public WithdrawalStatus getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(WithdrawalStatus withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Float getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(Float refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }
}
