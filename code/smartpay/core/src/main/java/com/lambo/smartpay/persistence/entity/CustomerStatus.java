package com.lambo.smartpay.persistence.entity;

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
 * Domain class for customer status.
 * <p/>
 * There are 2 statuses now:
 * Normal[200]: Customer is normal;
 * Frozen[500]: Customer is temporarily locked down.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CUSTOMER_STATUSES")
public class CustomerStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CSST_ID")
    private Long id;

    @Column(name = "CSST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CSST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CSST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CSST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "customerStatus", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers;

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

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
