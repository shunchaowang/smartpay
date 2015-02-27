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
 * There are two types of fee.
 * Static[100]: flat rate;
 * Percentage[101]: based on the percent of the payment.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "FEE_TYPES")
public class FeeType implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FETP_ID")
    private Long id;

    @Column(name = "FETP_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "FETP_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "FETP_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "FETP_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "feeType", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Fee> fees;

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

    public Set<Fee> getFees() {
        return fees;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
