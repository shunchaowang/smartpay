package com.lambo.smartpay.manage.web.vo;

import java.util.Date;

/**
 * Created by linly on 3/17/2015.
 */
public class SiteCommand {

    private Long id;

    private String name;

    private String url;

    private Date createdTime;

    private Date updatedTime;

    private String remark;

    private Boolean active;

    // relationships
    private Long siteStatus;
    private String siteStatusName;

    private Long merchant;
    private String merchantName;

    //operation
    private String siteOperation;


    // set & get
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(long siteStatus) {
        this.siteStatus = siteStatus;
    }

    public Long getMerchant() {
        return merchant;
    }

    public void setMerchant(long merchant) {
        this.merchant = merchant;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSiteStatusName() {
        return siteStatusName;
    }

    public void setSiteStatusName(String siteStatusName) {
        this.siteStatusName = siteStatusName;
    }

    public String getSiteOperation() {
        return siteOperation;
    }

    public void setSiteOperation(String siteOperation) {
        this.siteOperation = siteOperation;
    }

}
