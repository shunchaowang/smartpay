package com.lambo.smartpay.persistence.entity;

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
 * Domain class for the status of the shipment.
 * <p/>
 * 500: Shipped
 * 501: Delivered
 * 400: Lost
 * 401: Damaged
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "SHIPMENT_STATUSES")
public class ShipmentStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHST_ID")
    private Long id;

    @Column(name = "SHST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "SHST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "SHST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "SHST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "shipmentStatus")
    private Set<Shipment> shipments;

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

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Set<Shipment> shipments) {
        this.shipments = shipments;
    }
}
