package com.lambo.smartpay.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Domain class for the status of the site.
 * <p/>
 * 400: Created by merchant admin and await the approval of the admin
 * 500: Approved by admin
 * 401: Frozen by admin or merchant admin
 * 501: Declined by admin
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "SITE_STATUSES")
public class SiteStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SIST_ID")
    private Long id;

    @Column(name = "SIST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "SIST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "SIST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "SIST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "siteStatus")
    private Set<Site> sites;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }
}
