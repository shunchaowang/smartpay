package com.lambo.smartpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "MERCHANT_STATUSES")
public class MerchantStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MCST_ID")
    private Long id;

    @Column(name = "MCST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "MCST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "MCST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "MCST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "merchantStatus", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Merchant> merchants;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<Merchant> merchants) {
        this.merchants = merchants;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
