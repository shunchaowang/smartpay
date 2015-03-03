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
 * Domain class for the status of the order.
 * <p/>
 * 500: Confirmed
 * 401: Paid
 * 501: Preparing for Shipment
 * 402: Cancelled
 * 502: Shipped
 * 503: Delivered
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "ORDER_STATUSES")
public class OrderStatus implements Serializable {

    static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORST_ID")
    private Long id;

    @Column(name = "ORST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "ORST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "ORST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "ORST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Order> orders;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
