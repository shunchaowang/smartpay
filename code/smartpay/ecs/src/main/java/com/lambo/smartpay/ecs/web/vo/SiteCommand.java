package com.lambo.smartpay.ecs.web.vo;

import com.lambo.smartpay.core.persistence.entity.Site;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by linly on 3/17/2015.
 */
public class SiteCommand {

    private Long id;

    private String identity;

    private String name;

    private String url;
    private String returnUrl;

    private String createdTime;

    private String updatedTime;

    private String remark;

    private Boolean active;

    // relationships
    private Long siteStatusId;
    private String siteStatusName;

    private Long merchant;
    private String merchantName;

    public SiteCommand() {
    }

    public SiteCommand(Site site) {
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        id = site.getId();
        identity = site.getIdentity();
        name = site.getName();
        url = site.getUrl();
        returnUrl = site.getReturnUrl();
        if (site.getCreatedTime() != null) {
            createdTime = dateFormat.format(site.getCreatedTime());
        }
        if (site.getUpdatedTime() != null) {
            updatedTime = dateFormat.format(site.getUpdatedTime());
        }
        remark = site.getRemark();
        active = site.getActive();

        if (site.getSiteStatus() != null) {
            siteStatusId = site.getSiteStatus().getId();
            siteStatusName = site.getSiteStatus().getName();
        }
        if (site.getMerchant() != null) {
            merchant = site.getMerchant().getId();
            merchantName = site.getMerchant().getName();
        }
    }

    // set & get
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSiteStatusId() {
        return siteStatusId;
    }

    public void setSiteStatusId(long siteStatusId) {
        this.siteStatusId = siteStatusId;
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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setSiteStatusId(Long siteStatusId) {
        this.siteStatusId = siteStatusId;
    }

    public void setMerchant(Long merchant) {
        this.merchant = merchant;
    }
}
