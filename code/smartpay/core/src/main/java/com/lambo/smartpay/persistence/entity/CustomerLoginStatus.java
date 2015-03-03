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
 * Domain class for customer login status.
 * <p/>
 * There are 2 statuses now:
 * Normal[200]: Customer can log in;
 * Frozen[500]: Customer is temporarily locked down.
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CUSTOMER_LOGIN_STATUSES")
public class CustomerLoginStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CSLS_ID")
    private Long id;

    @Column(name = "CSLS_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CSLS_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CSLS_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CSLS_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "customerLoginStatus", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CustomerLogin> customerLogins;

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

    public Set<CustomerLogin> getCustomerLogins() {
        return customerLogins;
    }

    public void setCustomerLogins(Set<CustomerLogin> customerLogins) {
        this.customerLogins = customerLogins;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
