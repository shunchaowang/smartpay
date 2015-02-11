package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MER_SITE_INF")
public class MerchantSite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SIF_ID")
	private String siteNo; // auto increment
	@Column(name = "SIF_NAME")
	private String name; // not null, site name
	@Column(name = "SIF_DOMAIN")
	private String domain; // not null, site url
	@Column(name = "SIF_STATUS")
	private String status; // site status (0: normal, 1: waiting for
								// approval, 2: approved, 3: frozen, 4:
								// disabled by operator)
	@Column(name = "SIF_CREATEDTIME")
	private String createdTime; // not null
	@Column(name = "SIF_CREATEDIP")
	private String createdIP;
	@Column(name = "SIF_REMARK")
	private String remark;
	@Column(name = "SIF_MERID")
	private String merchantAccountNo; // not null, the merchant of the site
	@Column(name = "SIF_CREATEUSERID")
	private String merchantUserNo; // not null, the operator creates the site

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedIP() {
        return createdIP;
    }

    public void setCreatedIP(String createdIP) {
        this.createdIP = createdIP;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMerchantAccountNo() {
        return merchantAccountNo;
    }

    public void setMerchantAccountNo(String merchantAccountNo) {
        this.merchantAccountNo = merchantAccountNo;
    }

    public String getMerchantUserNo() {
        return merchantUserNo;
    }

    public void setMerchantUserNo(String merchantUserNo) {
        this.merchantUserNo = merchantUserNo;
    }
}
