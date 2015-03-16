package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.persistence.entity.Site;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linly on 3/16/2015.
 */
public class DataTablesSite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String siteName;
    private String siteUrl;
    private Date createdTime;
    private String siteStatus;


    public DataTablesSite(Site site) {
        id = site.getId();
        siteName = site.getName();
        siteUrl = site.getUrl();
        createdTime = site.getCreatedTime();
        siteStatus = site.getSiteStatus().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return siteName;
    }

    public void setName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return siteUrl;
    }

    public void setUrl(String siteUrl) {
        this.siteUrl = siteUrl;
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
}
