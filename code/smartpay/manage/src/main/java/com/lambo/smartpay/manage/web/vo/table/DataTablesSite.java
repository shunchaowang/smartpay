package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Site;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linly on 3/16/2015.
 */
public class DataTablesSite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String identity;
    private String name;
    private String url;
    private Date createdTime;
    private String siteStatus;
    private String merchant;


    public DataTablesSite(Site site) {
        id = site.getId();
        identity = site.getIdentity();
        name = site.getName();
        url = site.getUrl();
        createdTime = site.getCreatedTime();
        siteStatus = site.getSiteStatus().getName();
        merchant = site.getMerchant().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String id) {
        this.identity = identity;
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

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }



}
