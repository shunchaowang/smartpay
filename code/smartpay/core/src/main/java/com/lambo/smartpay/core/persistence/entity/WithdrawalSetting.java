package com.lambo.smartpay.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "WITHDRAWAL_SETTINGS")
public class WithdrawalSetting implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WDSE_ID")
    private Long id;

    @Column(name = "WDSE_CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "WDSE_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "WDSE_REMARK", nullable = true)
    private String remark;

    @Column(name = "WDSE_MIN_DAYS", nullable = false)
    private Long minDays;

    @Column(name = "WDSE_MAX_DAYS", nullable = false)
    private Long maxDays;

//    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, optional = false)
//    @JoinColumn(name = "WDSE_SECURITY_FEE_ID", nullable = false)
//    private Fee securityFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getMinDays() {
        return minDays;
    }

    public void setMinDays(Long minDays) {
        this.minDays = minDays;
    }

    public Long getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Long maxDays) {
        this.maxDays = maxDays;
    }

//    public Fee getSecurityFee() {
//        return securityFee;
//    }
//
//    public void setSecurityFee(Fee securityFee) {
//        this.securityFee = securityFee;
//    }
}
