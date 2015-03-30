package com.lambo.smartpay.manage.web.vo;

/**
 * Created by swang on 3/16/2015.
 */
public class OrderCommand {

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
    private String createdTime;
    private Long orderStatusId;
    private String orderStatusName;

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
    //
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
    //
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    //
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
}
