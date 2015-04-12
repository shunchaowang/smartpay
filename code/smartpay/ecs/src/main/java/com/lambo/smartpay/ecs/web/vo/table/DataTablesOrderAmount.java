package com.lambo.smartpay.ecs.web.vo.table;

/**
 * Created by swang on 4/6/2015.
 */
public class DataTablesOrderAmount {

    private Long siteId;
    private String siteName;
    private String siteIdentity;
    private Double orderAmount;
    private int orderCount;

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

    public String getSiteIdentity() {
        return siteIdentity;
    }

    public void setSiteIdentity(String siteIdentity) {
        this.siteIdentity = siteIdentity;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
