package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.Refund;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 4/8/2015.
 */
public class DataTablesRefund {

    private Long id;
    private Float amount;
    private String currency;
    private String createdTime;
    private String bankName;
    private String bankTransactionNumber;
    private String bankAccountNumber;
    private String bankReturnCode;
    private String refundStatusName;
    private String siteIdentity;
    private String merchantIdentity;

    // order info
    private Long orderId;
    private String orderNumber;
    private float orderAmount;
    private String orderCurrency;

    // customer info
    private String customerName;

    public DataTablesRefund(Refund refund) {

        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        id = refund.getId();
        amount = refund.getAmount();
        currency = refund.getCurrency().getName();
        createdTime = dateFormat.format(refund.getCreatedTime());
        bankName = refund.getBankName();
        bankTransactionNumber = refund.getBankTransactionNumber();
        bankAccountNumber = refund.getBankAccountNumber();
        bankReturnCode = refund.getBankReturnCode();
        refundStatusName = refund.getRefundStatus().getName();

        // order info
        Order order = refund.getOrder();
        orderId = order.getId();
        orderNumber = order.getMerchantNumber();
        orderAmount = order.getAmount();
        orderCurrency = order.getCurrency().getName();
        customerName = StringUtils.join(
                new String[]{order.getCustomer().getFirstName(),
                        order.getCustomer().getLastName()}, " ");
        siteIdentity = order.getSite().getIdentity();
        merchantIdentity = order.getSite().getMerchant().getIdentity();
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankReturnCode() {
        return bankReturnCode;
    }

    public void setBankReturnCode(String bankReturnCode) {
        this.bankReturnCode = bankReturnCode;
    }

    public String getRefundStatusName() {
        return refundStatusName;
    }

    public void setRefundStatusName(String refundStatusName) {
        this.refundStatusName = refundStatusName;
    }

    public String getSiteIdentity() {
        return siteIdentity;
    }

    public void setSiteIdentity(String siteIdentity) {
        this.siteIdentity = siteIdentity;
    }

    public String getMerchantIdentity() {
        return merchantIdentity;
    }

    public void setMerchantIdentity(String merchantIdentity) {
        this.merchantIdentity = merchantIdentity;
    }

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

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
