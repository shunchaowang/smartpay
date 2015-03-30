package com.lambo.smartpay.manage.web.vo;

import com.lambo.smartpay.core.persistence.entity.Payment;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 3/16/2015.
 */
public class PaymentCommand {


    private Long id;
    private Float amount;
    private String createdTime;
    private String bankName;
    private String bankTransactionNumber;
    private String bankReturnCode;
    private Long paymentStatusId;
    private String paymentStatusName;
    private Long paymentTypeId;
    private String paymentTypeName;
    //order
    private Long orderId;
    private String orderNumber;
    //currency
    private Long currencyId;
    private String currencyName;
    //merchant
    private Long merchantId;
    private String merchantName;
    private String merchantNumber;
    private Long siteId;
    private String siteName;
    //
    private String successTime;
    private String remark;
    private String billFirstName;
    private String billLastName;
    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZipCode;
    private String billCountry;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    //
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankTransactionNumber() {
        return bankTransactionNumber;
    }
    public void setBankTransactionNumber(String bankTransactionNumber) {
        this.bankTransactionNumber = bankTransactionNumber;
    }

    public String getBankReturnCode() {
        return bankReturnCode;
    }
    public void setBankReturnCode(String bankReturnCode) {
        this.bankReturnCode = bankReturnCode;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }
    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }
    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }
    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }
    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    //
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    //
    public Long getCurrencyId() {
        return currencyId;
    }
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    //
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long currencyId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }
    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }


    //
    public Long getSiteId() {
        return siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    //
    public String getSuccessTime() {
        return successTime;
    }
    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    //
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getBillFirstName() {
        return billFirstName;
    }
    public void setBillFirstName(String billFirstName) {
        this.billFirstName = billFirstName;
    }

    public String getBillLastName() {
        return billLastName;
    }
    public void setBillLastName(String billLastName) {
        this.billLastName = billLastName;
    }

    public String getBillAddress1() {
        return billAddress1;
    }
    public void setBillAddress1(String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return billAddress2;
    }
    public void setBillAddress2(String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public String getBillCity() {
        return billCity;
    }
    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillState() {
        return billState;
    }
    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillZipCode() {
        return billZipCode;
    }
    public void setBillZipCode(String billZipCode) {
        this.billZipCode = billZipCode;
    }

    public String getBillCountry() {
        return billCountry;
    }
    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }
}
