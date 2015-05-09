package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Payment;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * Created by linly on 3/29/2015.
 */
public class DataTablesPayment {

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

    public DataTablesPayment(Payment payment) {
        //
        Locale locale = LocaleContextHolder.getLocale();
        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        //DateFormat dateFormat = new DateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //
        id = payment.getId();
        amount = payment.getAmount();
        createdTime = dateFormat.format(payment.getCreatedTime());
        bankName = payment.getBankName();
        bankTransactionNumber = payment.getBankTransactionNumber();
        bankReturnCode = payment.getBankReturnCode();
        paymentStatusId = payment.getPaymentStatus().getId();
        paymentStatusName = payment.getPaymentStatus().getName();
        paymentTypeId = payment.getPaymentType().getId();
        paymentTypeName = payment.getPaymentType().getName();

        //
        orderId = payment.getOrder().getId();
        orderNumber = payment.getOrder().getMerchantNumber();

        //
        currencyId = payment.getCurrency().getId();
        currencyName = payment.getCurrency().getName();

        //
        merchantId = payment.getOrder().getSite().getMerchant().getId();
        merchantName = payment.getOrder().getSite().getMerchant().getName();
        merchantNumber = payment.getOrder().getMerchantNumber();
        siteId = payment.getOrder().getSite().getId();
        siteName = payment.getOrder().getSite().getUrl();
    }

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

}
