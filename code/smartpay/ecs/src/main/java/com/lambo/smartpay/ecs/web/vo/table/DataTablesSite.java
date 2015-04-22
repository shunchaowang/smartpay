package com.lambo.smartpay.ecs.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Site;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by linly on 3/16/2015.
 */
public class DataTablesSite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String identity;
    private String name;
    private String url;
    private String createdTime;
    private String siteStatus;


    public DataTablesSite(Site site) {
        id = site.getId();
        identity = site.getIdentity();
        name = site.getName();
        url = site.getUrl();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        createdTime = dateFormat.format(site.getCreatedTime());
        siteStatus = site.getSiteStatus().getName();
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }
}
