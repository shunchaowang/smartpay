package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by swang on 3/27/2015.
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
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String createdTime;
    private Long orderStatusId;
    private String orderStatusName;
    private String merchantName;

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
        siteName = order.getSite().getUrl();
        customerId = order.getCustomer().getId();
        customerName = order.getCustomer().getLastName() + ", " + order.getCustomer()
                .getFirstName();
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
        merchantName = order.getSite().getMerchant().getName();
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
}
