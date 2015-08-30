package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.persistence.entity.WithdrawalStatus;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created by chensf on 8/2/2015.
 */
public class WithdrawalCommand {

    private Long id;
    private String createdTime;
    private String updatedTime;
    private String remark;
    private Boolean active;
    private Double balance;
    private Double amount;
    private String dateStart;
    private String dateEnd;
    private Long requestedBy;
    private Long auditedBy;
    private Float securityRate;
    private Double securityDeposit;
    private Boolean securityWithdrawn;
    private Float refundAmt;
    private Float totalFee;
    private String dateRange;
    private String requester;
    private String auditer;
    private Double amountApproved;
    private Double refundAfterWithdrawn;
    private Double chargebaceAfterWithdrawn;
    private Double amountAdjusted;

    public WithdrawalCommand(Withdrawal withdrawal){
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        id = withdrawal.getId();
        if (withdrawal.getCreatedTime() != null) {
            createdTime = dateFormat.format(withdrawal.getCreatedTime());
        }
        if (withdrawal.getUpdatedTime() != null) {
            updatedTime = dateFormat.format(withdrawal.getUpdatedTime());
        }
        remark = withdrawal.getRemark();
        active = withdrawal.getActive();
        balance = withdrawal.getBalance();
        amount = withdrawal.getAmount();
        if (withdrawal.getDateStart() != null) {
            dateStart = dateFormat.format(withdrawal.getDateStart());
        }
        if (withdrawal.getDateEnd() != null) {
            dateEnd = dateFormat.format(withdrawal.getDateEnd());
        }

        requestedBy = withdrawal.getRequestedBy();
        auditedBy = withdrawal.getAuditedBy();
        securityRate = withdrawal.getSecurityRate();
        securityDeposit = withdrawal.getSecurityDeposit();
        securityWithdrawn = withdrawal.getSecurityWithdrawn();
        amountApproved = withdrawal.getAmountApproved();
        refundAfterWithdrawn = withdrawal.getRefundAfterWithdrawn();
        chargebaceAfterWithdrawn = withdrawal.getChargebackAfterWithdrawn();
        amountAdjusted = withdrawal.getAmountAdjust();
    }

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
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

    public Double getAmountApproved() {
        return amountApproved;
    }

    public void setAmountApproved(Double amountApproved) {
        this.amountApproved = amountApproved;
    }

    public Double getRefundAfterWithdrawn() {
        return refundAfterWithdrawn;
    }

    public void setRefundAfterWithdrawn(Double refundAfterWithdrawn) {
        this.refundAfterWithdrawn = refundAfterWithdrawn;
    }

    public Double getChargebaceAfterWithdrawn() {
        return chargebaceAfterWithdrawn;
    }

    public void setChargebaceAfterWithdrawn(Double chargebaceAfterWithdrawn) {
        this.chargebaceAfterWithdrawn = chargebaceAfterWithdrawn;
    }

    public Double getAmountAdjusted() {
        return amountAdjusted;
    }

    public void setAmountAdjusted(Double amountAdjusted) {
        this.amountAdjusted = amountAdjusted;
    }
}
