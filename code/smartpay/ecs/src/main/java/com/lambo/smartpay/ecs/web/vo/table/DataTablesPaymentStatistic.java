package com.lambo.smartpay.ecs.web.vo.table;

/**
 * Created by chensf on 8/29/2015.
 */
public class DataTablesPaymentStatistic {

    private String statisticsDate;
    private Long paidCount = Long.parseLong("0");
    private Double paidAmount = Double.parseDouble("0.00");
    private Long refundCount = Long.parseLong("0");
    private Double refundAmount = Double.parseDouble("0.00");
    private Long refuseCount = Long.parseLong("0");
    private Double refuseAmount = Double.parseDouble("0.00");

    public String getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
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
