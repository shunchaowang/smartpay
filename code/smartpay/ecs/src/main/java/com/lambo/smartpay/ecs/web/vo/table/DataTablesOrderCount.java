package com.lambo.smartpay.ecs.web.vo.table;

/**
 * Created by swang on 4/6/2015.
 */
public class DataTablesOrderCount {

    private Long siteId;
    private String siteName;
    private String siteIdentity;
    private Long orderCount = Long.parseLong("0");
    private Double orderAmount = Double.parseDouble("0.00");
    private Long paidCount = Long.parseLong("0");
    private Double paidAmount = Double.parseDouble("0.00");
    private Long refundCount = Long.parseLong("0");
    private Double refundAmount = Double.parseDouble("0.00");
    private Long refuseCount = Long.parseLong("0");
    private Double refuseAmount = Double.parseDouble("0.00");

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

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(Long paidCount) {
        this.paidCount = paidCount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Long getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Long refundCount) {
        this.refundCount = refundCount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Long getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Long refuseCount) {
        this.refuseCount = refuseCount;
    }

    public Double getRefuseAmount() {
        return refuseAmount;
    }

    public void setRefuseAmount(Double refuseAmount) {
        this.refuseAmount = refuseAmount;
    }
}
