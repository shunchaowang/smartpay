package com.lambo.smartpay.pay.web.vo;

import java.util.List;

/**
 * Created by swang on 3/29/2015.
 */
public class OrderCommand {

    private String orderNo;
    private String merNo;
    private String siteNo;
    private Long orderId;
    private Long siteId;
    private Long orderStatusId;
    private Long currencyId;
    private Long customerId;
    private String returnUrl;
    private String referer;
    private String amount;
    private String currency;
    private String productType;
    private String goodsName;
    private List goodsNameArray;
    private String goodsNumber;
    private List goodsNumberArray;
    private String goodsPrice;
    private List goodsPriceArray;
    private String email;
    private String phone;
    private String shipFirstName;
    private String shipLastName;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipCountry;
    private String shipZipCode;
    private String remark;

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipFirstName() {
        return shipFirstName;
    }

    public void setShipFirstName(String shipFirstName) {
        this.shipFirstName = shipFirstName;
    }

    public String getShipLastName() {
        return shipLastName;
    }

    public void setShipLastName(String shipLastName) {
        this.shipLastName = shipLastName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getShipZipCode() {
        return shipZipCode;
    }

    public void setShipZipCode(String shipZipCode) {
        this.shipZipCode = shipZipCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List getGoodsNameArray() {
        return goodsNameArray;
    }

    public void setGoodsNameArray(List goodsNameArray) {
        this.goodsNameArray = goodsNameArray;
    }

    public List getGoodsNumberArray() {
        return goodsNumberArray;
    }

    public void setGoodsNumberArray(List goodsNumberArray) {
        this.goodsNumberArray = goodsNumberArray;
    }

    public List getGoodsPriceArray() {
        return goodsPriceArray;
    }

    public void setGoodsPriceArray(List goodsPriceArray) {
        this.goodsPriceArray = goodsPriceArray;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
