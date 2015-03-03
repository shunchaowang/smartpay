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
 * Domain class for currency.
 * <p>
 * USD[100]: US Dollar;
 * RMB[101]: Chinese Yuan.
 * </p>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "CURRENCIES")
public class Currency implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CRCY_ID")
    private Long id;

    @Column(name = "CRCY_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CRCY_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CRCY_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CRCY_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Order> orders;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Payment> payments;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Refund> refunds;

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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(Set<Refund> refunds) {
        this.refunds = refunds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
