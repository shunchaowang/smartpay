package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Withdrawal;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by chensf on 7/31/2015.
 */
public class DataTablesWithdrawal {
    private Long id;
    private String createdTime;
    private String updatedTime;
    private String remark;
    private Double balance;
    private Double amount;
    private String dateStart;
    private String dateEnd;
    private String requester;
    private String auditer;
    private Float securityRate;
    private Double securityDeposit;
    private Boolean securityWithdrawn;
    private String merchantName;
    private String withdrawalStatusName;
    private String dateRange;
    private String dueToSecurityWithdrawn = "false";

    public DataTablesWithdrawal(Withdrawal withdrawal) {

        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        id = withdrawal.getId();
        createdTime = dateFormat.format(withdrawal.getCreatedTime());
        if(withdrawal.getUpdatedTime() !=null)
            updatedTime = dateFormat.format(withdrawal.getUpdatedTime());
        remark = withdrawal.getRemark();
        balance = withdrawal.getBalance();
        amount = withdrawal.getAmount();
        dateStart = dateFormat.format(withdrawal.getDateStart());
        dateEnd = dateFormat.format(withdrawal.getDateEnd());
        securityRate = withdrawal.getSecurityRate();
        securityDeposit = withdrawal.getSecurityDeposit();
        merchantName = withdrawal.getMerchant().getName();
        if(withdrawal.getWithdrawalStatus() !=null)
            withdrawalStatusName = withdrawal.getWithdrawalStatus().getName();
        if(withdrawal.getWithdrawalStatus() !=null
                && withdrawal.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_APPROVED_CODE)
                && !securityWithdrawn){
            Calendar calendar   =   new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE, -180);
            Date date = calendar.getTime();
            if(withdrawal.getUpdatedTime().compareTo(date) >= 0) dueToSecurityWithdrawn ="true";
        }
        dateRange = dateStart + " - " + dateEnd;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getWithdrawalStatusName() {
        return withdrawalStatusName;
    }

    public void setWithdrawalStatusName(String withdrawalStatusName) {
        this.withdrawalStatusName = withdrawalStatusName;
    }

    public String getDueToSecurityWithdrawn() {
        return dueToSecurityWithdrawn;
    }

    public void setDueToSecurityWithdrawn(String dueToSecurityWithdrawn) {
        this.dueToSecurityWithdrawn = dueToSecurityWithdrawn;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

}
