package com.lambo.smartpay.ecs.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Order;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by swang on 3/27/2015.
 * modified by chensf 18/5/2015
 */
public class DataTablesOrder {

    private Long id;
    private String merchantNumber;
    private Float amount;
    private String goodsName;
    private String goodsAmount;
    private Long currencyId;
    private String currencyName;
    private String remark;
    private Long siteId;
    private String siteName;
    private String siteUrl;
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String createdTime;
    private Long orderStatusId;
    private String orderStatusName;
    private String orderStatusCode;
    private String merchantId;
    private String merchantName;
    private String customerEmail;

    private String paymentId;
    private String bankName;
    private String bankTransactionNumber;
    private String bankReturnCode;
    private Long paymentStatusId;
    private String paymentStatusName;
    private Long paymentTypeId;
    private String paymentTypeName;

    private String successTime;
    private String billFirstName;
    private String billLastName;
    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZipCode;
    private String billCountry;

    public DataTablesOrder(Order order) {
        id = order.getId();
        merchantNumber = order.getMerchantNumber();
        amount = order.getAmount();
        goodsName = order.getGoodsName();
        goodsAmount = order.getGoodsAmount();
        currencyId = order.getCurrency().getId();
        currencyName = order.getCurrency().getName();
        remark = order.getRemark();
        siteId = order.getSite().getId();
        siteName = order.getSite().getName();
        siteUrl = order.getSite().getUrl();
        customerId = order.getCustomer().getId();
        customerName = order.getCustomer().getLastName() + ", " + order.getCustomer()
                .getFirstName();
        customerEmail = order.getCustomer().getEmail();
        customerAddress = StringUtils.join(
                new String[]{order.getCustomer().getAddress1(), order.getCustomer().getCity(),
                        order.getCustomer().getState(), order.getCustomer().getZipCode(),
                        order.getCustomer().getCountry()}, " ");
        //Locale locale = LocaleContextHolder.getLocale();
        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        createdTime = dateFormat.format(order.getCreatedTime());
        orderStatusId = order.getOrderStatus().getId();
        orderStatusName = order.getOrderStatus().getName();
        orderStatusCode = order.getOrderStatus().getCode();
        merchantId = order.getSite().getMerchant().getIdentity();
        merchantName = order.getSite().getMerchant().getName();
    }

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSiteUrl() { return siteUrl;}

    public void setSiteUrl(String siteUrl) { this.siteUrl = siteUrl; }
}
