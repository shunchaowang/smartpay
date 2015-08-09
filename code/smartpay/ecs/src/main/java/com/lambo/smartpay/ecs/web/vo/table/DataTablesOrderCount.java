package com.lambo.smartpay.ecs.web.vo.table;

/**
 * Created by swang on 4/6/2015.
 */
public class DataTablesOrderCount {

    private Long siteId;
    private String siteName;
    private String siteIdentity;

    private Long USDCnt = Long.parseLong("0");
    private Double USDAmt = Double.parseDouble("0.00");
    private Long USDPaidCnt = Long.parseLong("0");
    private Double USDPaidAmt = Double.parseDouble("0.00");
    private Long USDRefundCnt = Long.parseLong("0");
    private Double USDRefundAmt = Double.parseDouble("0.00");
    private Long USDClaimCnt = Long.parseLong("0");
    private Double USDClaimAmt = Double.parseDouble("0.00");
    private Long USDInitiatedCnt = Long.parseLong("0");
    private Double USDInitiatedAmt = Double.parseDouble("0.00");

    private Long RMBCnt = Long.parseLong("0");
    private Double RMBAmt = Double.parseDouble("0.00");
    private Long RMBPaidCnt = Long.parseLong("0");
    private Double RMBPaidAmt = Double.parseDouble("0.00");
    private Long RMBRefundCnt = Long.parseLong("0");
    private Double RMBRefundAmt = Double.parseDouble("0.00");
    private Long RMBClaimCnt = Long.parseLong("0");
    private Double RMBClaimAmt = Double.parseDouble("0.00");
    private Long RMBInitiatedCnt = Long.parseLong("0");
    private Double RMBInitiatedAmt = Double.parseDouble("0.00");

    private Long GBPCnt = Long.parseLong("0");
    private Double GBPAmt = Double.parseDouble("0.00");
    private Long GBPPaidCnt = Long.parseLong("0");
    private Double GBPPaidAmt = Double.parseDouble("0.00");
    private Long GBPRefundCnt = Long.parseLong("0");
    private Double GBPRefundAmt = Double.parseDouble("0.00");
    private Long GBPClaimCnt = Long.parseLong("0");
    private Double GBPClaimAmt = Double.parseDouble("0.00");
    private Long GBPInitiatedCnt = Long.parseLong("0");
    private Double GBPInitiatedAmt = Double.parseDouble("0.00");

    private Long EURCnt = Long.parseLong("0");
    private Double EURAmt = Double.parseDouble("0.00");
    private Long EURPaidCnt = Long.parseLong("0");
    private Double EURPaidAmt = Double.parseDouble("0.00");
    private Long EURRefundCnt = Long.parseLong("0");
    private Double EURRefundAmt = Double.parseDouble("0.00");
    private Long EURClaimCnt = Long.parseLong("0");
    private Double EURClaimAmt = Double.parseDouble("0.00");
    private Long EURInitiatedCnt = Long.parseLong("0");
    private Double EURInitiatedAmt = Double.parseDouble("0.00");

    private Long JPYCnt = Long.parseLong("0");
    private Double JPYAmt = Double.parseDouble("0.00");
    private Long JPYPaidCnt = Long.parseLong("0");
    private Double JPYPaidAmt = Double.parseDouble("0.00");
    private Long JPYRefundCnt = Long.parseLong("0");
    private Double JPYRefundAmt = Double.parseDouble("0.00");
    private Long JPYClaimCnt = Long.parseLong("0");
    private Double JPYClaimAmt = Double.parseDouble("0.00");
    private Long JPYInitiatedCnt = Long.parseLong("0");
    private Double JPYInitiatedAmt = Double.parseDouble("0.00");

    private Long CADCnt = Long.parseLong("0");
    private Double CADAmt = Double.parseDouble("0.00");
    private Long CADPaidCnt = Long.parseLong("0");
    private Double CADPaidAmt = Double.parseDouble("0.00");
    private Long CADRefundCnt = Long.parseLong("0");
    private Double CADRefundAmt = Double.parseDouble("0.00");
    private Long CADClaimCnt = Long.parseLong("0");
    private Double CADClaimAmt = Double.parseDouble("0.00");
    private Long CADInitiatedCnt = Long.parseLong("0");
    private Double CADInitiatedAmt = Double.parseDouble("0.00");

    private Long AUDCnt = Long.parseLong("0");
    private Double AUDAmt = Double.parseDouble("0.00");
    private Long AUDPaidCnt = Long.parseLong("0");
    private Double AUDPaidAmt = Double.parseDouble("0.00");
    private Long AUDRefundCnt = Long.parseLong("0");
    private Double AUDRefundAmt = Double.parseDouble("0.00");
    private Long AUDClaimCnt = Long.parseLong("0");
    private Double AUDClaimAmt = Double.parseDouble("0.00");
    private Long AUDInitiatedCnt = Long.parseLong("0");
    private Double AUDInitiatedAmt = Double.parseDouble("0.00");

    private Long orderTotalCnt = Long.parseLong("0");

    private Long paymentCnt = Long.parseLong("0");
    private Double paymentAmt = Double.parseDouble("0.00");
    private Double feeAmt = Double.parseDouble("0.00");
    private Long claimCnt = Long.parseLong("0");
    private Double claimAmt = Double.parseDouble("0.00");

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

    public Long getUSDCnt() {
        return USDCnt;
    }

    public void setUSDCnt(Long USDCnt) {
        this.USDCnt = USDCnt;
    }

    public Double getUSDAmt() {
        return USDAmt;
    }

    public void setUSDAmt(Double USDAmt) {
        this.USDAmt = USDAmt;
    }

    public Long getUSDPaidCnt() {
        return USDPaidCnt;
    }

    public void setUSDPaidCnt(Long USDPaidCnt) {
        this.USDPaidCnt = USDPaidCnt;
    }

    public Double getUSDPaidAmt() {
        return USDPaidAmt;
    }

    public void setUSDPaidAmt(Double USDPaidAmt) {
        this.USDPaidAmt = USDPaidAmt;
    }

    public Long getUSDRefundCnt() {
        return USDRefundCnt;
    }

    public void setUSDRefundCnt(Long USDRefundCnt) {
        this.USDRefundCnt = USDRefundCnt;
    }

    public Double getUSDRefundAmt() {
        return USDRefundAmt;
    }

    public void setUSDRefundAmt(Double USDRefundAmt) {
        this.USDRefundAmt = USDRefundAmt;
    }

    public Long getUSDClaimCnt() {
        return USDClaimCnt;
    }

    public void setUSDClaimCnt(Long USDClaimCnt) {
        this.USDClaimCnt = USDClaimCnt;
    }

    public Double getUSDClaimAmt() {
        return USDClaimAmt;
    }

    public void setUSDClaimAmt(Double USDClaimAmt) {
        this.USDClaimAmt = USDClaimAmt;
    }

    public Long getUSDInitiatedCnt() {
        return USDInitiatedCnt;
    }

    public void setUSDInitiatedCnt(Long USDInitiatedCnt) {
        this.USDInitiatedCnt = USDInitiatedCnt;
    }

    public Double getUSDInitiatedAmt() {
        return USDInitiatedAmt;
    }

    public void setUSDInitiatedAmt(Double USDInitiatedAmt) {
        this.USDInitiatedAmt = USDInitiatedAmt;
    }

    public Long getRMBCnt() {
        return RMBCnt;
    }

    public void setRMBCnt(Long RMBCnt) {
        this.RMBCnt = RMBCnt;
    }

    public Double getRMBAmt() {
        return RMBAmt;
    }

    public void setRMBAmt(Double RMBAmt) {
        this.RMBAmt = RMBAmt;
    }

    public Long getRMBPaidCnt() {
        return RMBPaidCnt;
    }

    public void setRMBPaidCnt(Long RMBPaidCnt) {
        this.RMBPaidCnt = RMBPaidCnt;
    }

    public Double getRMBPaidAmt() {
        return RMBPaidAmt;
    }

    public void setRMBPaidAmt(Double RMBPaidAmt) {
        this.RMBPaidAmt = RMBPaidAmt;
    }

    public Long getRMBRefundCnt() {
        return RMBRefundCnt;
    }

    public void setRMBRefundCnt(Long RMBRefundCnt) {
        this.RMBRefundCnt = RMBRefundCnt;
    }

    public Double getRMBRefundAmt() {
        return RMBRefundAmt;
    }

    public void setRMBRefundAmt(Double RMBRefundAmt) {
        this.RMBRefundAmt = RMBRefundAmt;
    }

    public Long getRMBClaimCnt() {
        return RMBClaimCnt;
    }

    public void setRMBClaimCnt(Long RMBClaimCnt) {
        this.RMBClaimCnt = RMBClaimCnt;
    }

    public Double getRMBClaimAmt() {
        return RMBClaimAmt;
    }

    public void setRMBClaimAmt(Double RMBClaimAmt) {
        this.RMBClaimAmt = RMBClaimAmt;
    }

    public Long getRMBInitiatedCnt() {
        return RMBInitiatedCnt;
    }

    public void setRMBInitiatedCnt(Long RMBInitiatedCnt) {
        this.RMBInitiatedCnt = RMBInitiatedCnt;
    }

    public Double getRMBInitiatedAmt() {
        return RMBInitiatedAmt;
    }

    public void setRMBInitiatedAmt(Double RMBInitiatedAmt) {
        this.RMBInitiatedAmt = RMBInitiatedAmt;
    }

    public Long getGBPCnt() {
        return GBPCnt;
    }

    public void setGBPCnt(Long GBPCnt) {
        this.GBPCnt = GBPCnt;
    }

    public Double getGBPAmt() {
        return GBPAmt;
    }

    public void setGBPAmt(Double GBPAmt) {
        this.GBPAmt = GBPAmt;
    }

    public Long getGBPPaidCnt() {
        return GBPPaidCnt;
    }

    public void setGBPPaidCnt(Long GBPPaidCnt) {
        this.GBPPaidCnt = GBPPaidCnt;
    }

    public Double getGBPPaidAmt() {
        return GBPPaidAmt;
    }

    public void setGBPPaidAmt(Double GBPPaidAmt) {
        this.GBPPaidAmt = GBPPaidAmt;
    }

    public Long getGBPRefundCnt() {
        return GBPRefundCnt;
    }

    public void setGBPRefundCnt(Long GBPRefundCnt) {
        this.GBPRefundCnt = GBPRefundCnt;
    }

    public Double getGBPRefundAmt() {
        return GBPRefundAmt;
    }

    public void setGBPRefundAmt(Double GBPRefundAmt) {
        this.GBPRefundAmt = GBPRefundAmt;
    }

    public Long getGBPClaimCnt() {
        return GBPClaimCnt;
    }

    public void setGBPClaimCnt(Long GBPClaimCnt) {
        this.GBPClaimCnt = GBPClaimCnt;
    }

    public Double getGBPClaimAmt() {
        return GBPClaimAmt;
    }

    public void setGBPClaimAmt(Double GBPClaimAmt) {
        this.GBPClaimAmt = GBPClaimAmt;
    }

    public Long getGBPInitiatedCnt() {
        return GBPInitiatedCnt;
    }

    public void setGBPInitiatedCnt(Long GBPInitiatedCnt) {
        this.GBPInitiatedCnt = GBPInitiatedCnt;
    }

    public Double getGBPInitiatedAmt() {
        return GBPInitiatedAmt;
    }

    public void setGBPInitiatedAmt(Double GBPInitiatedAmt) {
        this.GBPInitiatedAmt = GBPInitiatedAmt;
    }

    public Long getEURCnt() {
        return EURCnt;
    }

    public void setEURCnt(Long EURCnt) {
        this.EURCnt = EURCnt;
    }

    public Double getEURAmt() {
        return EURAmt;
    }

    public void setEURAmt(Double EURAmt) {
        this.EURAmt = EURAmt;
    }

    public Long getEURPaidCnt() {
        return EURPaidCnt;
    }

    public void setEURPaidCnt(Long EURPaidCnt) {
        this.EURPaidCnt = EURPaidCnt;
    }

    public Double getEURPaidAmt() {
        return EURPaidAmt;
    }

    public void setEURPaidAmt(Double EURPaidAmt) {
        this.EURPaidAmt = EURPaidAmt;
    }

    public Long getEURRefundCnt() {
        return EURRefundCnt;
    }

    public void setEURRefundCnt(Long EURRefundCnt) {
        this.EURRefundCnt = EURRefundCnt;
    }

    public Double getEURRefundAmt() {
        return EURRefundAmt;
    }

    public void setEURRefundAmt(Double EURRefundAmt) {
        this.EURRefundAmt = EURRefundAmt;
    }

    public Long getEURClaimCnt() {
        return EURClaimCnt;
    }

    public void setEURClaimCnt(Long EURClaimCnt) {
        this.EURClaimCnt = EURClaimCnt;
    }

    public Double getEURClaimAmt() {
        return EURClaimAmt;
    }

    public void setEURClaimAmt(Double EURClaimAmt) {
        this.EURClaimAmt = EURClaimAmt;
    }

    public Long getEURInitiatedCnt() {
        return EURInitiatedCnt;
    }

    public void setEURInitiatedCnt(Long EURInitiatedCnt) {
        this.EURInitiatedCnt = EURInitiatedCnt;
    }

    public Double getEURInitiatedAmt() {
        return EURInitiatedAmt;
    }

    public void setEURInitiatedAmt(Double EURInitiatedAmt) {
        this.EURInitiatedAmt = EURInitiatedAmt;
    }

    public Long getJPYCnt() {
        return JPYCnt;
    }

    public void setJPYCnt(Long JPYCnt) {
        this.JPYCnt = JPYCnt;
    }

    public Double getJPYAmt() {
        return JPYAmt;
    }

    public void setJPYAmt(Double JPYAmt) {
        this.JPYAmt = JPYAmt;
    }

    public Long getJPYPaidCnt() {
        return JPYPaidCnt;
    }

    public void setJPYPaidCnt(Long JPYPaidCnt) {
        this.JPYPaidCnt = JPYPaidCnt;
    }

    public Double getJPYPaidAmt() {
        return JPYPaidAmt;
    }

    public void setJPYPaidAmt(Double JPYPaidAmt) {
        this.JPYPaidAmt = JPYPaidAmt;
    }

    public Long getJPYRefundCnt() {
        return JPYRefundCnt;
    }

    public void setJPYRefundCnt(Long JPYRefundCnt) {
        this.JPYRefundCnt = JPYRefundCnt;
    }

    public Double getJPYRefundAmt() {
        return JPYRefundAmt;
    }

    public void setJPYRefundAmt(Double JPYRefundAmt) {
        this.JPYRefundAmt = JPYRefundAmt;
    }

    public Long getJPYClaimCnt() {
        return JPYClaimCnt;
    }

    public void setJPYClaimCnt(Long JPYClaimCnt) {
        this.JPYClaimCnt = JPYClaimCnt;
    }

    public Double getJPYClaimAmt() {
        return JPYClaimAmt;
    }

    public void setJPYClaimAmt(Double JPYClaimAmt) {
        this.JPYClaimAmt = JPYClaimAmt;
    }

    public Long getJPYInitiatedCnt() {
        return JPYInitiatedCnt;
    }

    public void setJPYInitiatedCnt(Long JPYInitiatedCnt) {
        this.JPYInitiatedCnt = JPYInitiatedCnt;
    }

    public Double getJPYInitiatedAmt() {
        return JPYInitiatedAmt;
    }

    public void setJPYInitiatedAmt(Double JPYInitiatedAmt) {
        this.JPYInitiatedAmt = JPYInitiatedAmt;
    }

    public Long getCADCnt() {
        return CADCnt;
    }

    public void setCADCnt(Long CADCnt) {
        this.CADCnt = CADCnt;
    }

    public Double getCADAmt() {
        return CADAmt;
    }

    public void setCADAmt(Double CADAmt) {
        this.CADAmt = CADAmt;
    }

    public Long getCADPaidCnt() {
        return CADPaidCnt;
    }

    public void setCADPaidCnt(Long CADPaidCnt) {
        this.CADPaidCnt = CADPaidCnt;
    }

    public Double getCADPaidAmt() {
        return CADPaidAmt;
    }

    public void setCADPaidAmt(Double CADPaidAmt) {
        this.CADPaidAmt = CADPaidAmt;
    }

    public Long getCADRefundCnt() {
        return CADRefundCnt;
    }

    public void setCADRefundCnt(Long CADRefundCnt) {
        this.CADRefundCnt = CADRefundCnt;
    }

    public Double getCADRefundAmt() {
        return CADRefundAmt;
    }

    public void setCADRefundAmt(Double CADRefundAmt) {
        this.CADRefundAmt = CADRefundAmt;
    }

    public Long getCADClaimCnt() {
        return CADClaimCnt;
    }

    public void setCADClaimCnt(Long CADClaimCnt) {
        this.CADClaimCnt = CADClaimCnt;
    }

    public Double getCADClaimAmt() {
        return CADClaimAmt;
    }

    public void setCADClaimAmt(Double CADClaimAmt) {
        this.CADClaimAmt = CADClaimAmt;
    }

    public Long getCADInitiatedCnt() {
        return CADInitiatedCnt;
    }

    public void setCADInitiatedCnt(Long CADInitiatedCnt) {
        this.CADInitiatedCnt = CADInitiatedCnt;
    }

    public Double getCADInitiatedAmt() {
        return CADInitiatedAmt;
    }

    public void setCADInitiatedAmt(Double CADInitiatedAmt) {
        this.CADInitiatedAmt = CADInitiatedAmt;
    }

    public Long getAUDCnt() {
        return AUDCnt;
    }

    public void setAUDCnt(Long AUDCnt) {
        this.AUDCnt = AUDCnt;
    }

    public Double getAUDAmt() {
        return AUDAmt;
    }

    public void setAUDAmt(Double AUDAmt) {
        this.AUDAmt = AUDAmt;
    }

    public Long getAUDPaidCnt() {
        return AUDPaidCnt;
    }

    public void setAUDPaidCnt(Long AUDPaidCnt) {
        this.AUDPaidCnt = AUDPaidCnt;
    }

    public Double getAUDPaidAmt() {
        return AUDPaidAmt;
    }

    public void setAUDPaidAmt(Double AUDPaidAmt) {
        this.AUDPaidAmt = AUDPaidAmt;
    }

    public Long getAUDRefundCnt() {
        return AUDRefundCnt;
    }

    public void setAUDRefundCnt(Long AUDRefundCnt) {
        this.AUDRefundCnt = AUDRefundCnt;
    }

    public Double getAUDRefundAmt() {
        return AUDRefundAmt;
    }

    public void setAUDRefundAmt(Double AUDRefundAmt) {
        this.AUDRefundAmt = AUDRefundAmt;
    }

    public Long getAUDClaimCnt() {
        return AUDClaimCnt;
    }

    public void setAUDClaimCnt(Long AUDClaimCnt) {
        this.AUDClaimCnt = AUDClaimCnt;
    }

    public Double getAUDClaimAmt() {
        return AUDClaimAmt;
    }

    public void setAUDClaimAmt(Double AUDClaimAmt) {
        this.AUDClaimAmt = AUDClaimAmt;
    }

    public Long getAUDInitiatedCnt() {
        return AUDInitiatedCnt;
    }

    public void setAUDInitiatedCnt(Long AUDInitiatedCnt) {
        this.AUDInitiatedCnt = AUDInitiatedCnt;
    }

    public Double getAUDInitiatedAmt() {
        return AUDInitiatedAmt;
    }

    public void setAUDInitiatedAmt(Double AUDInitiatedAmt) {
        this.AUDInitiatedAmt = AUDInitiatedAmt;
    }

    public Long getOrderTotalCnt() {
        return orderTotalCnt;
    }

    public void setOrderTotalCnt(Long orderTotalCnt) {
        this.orderTotalCnt = orderTotalCnt;
    }

    public Long getPaymentCnt() {
        return paymentCnt;
    }

    public void setPaymentCnt(Long paymentCnt) {
        this.paymentCnt = paymentCnt;
    }

    public Double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(Double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public Double getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(Double feeAmt) {
        this.feeAmt = feeAmt;
    }

    public Long getClaimCnt() {
        return claimCnt;
    }

    public void setClaimCnt(Long claimCnt) {
        this.claimCnt = claimCnt;
    }

    public Double getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(Double claimAmt) {
        this.claimAmt = claimAmt;
    }
}
