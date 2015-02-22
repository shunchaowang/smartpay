package com.lambo.smartpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "SHIPMENTS")
public class Shipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHMT_ID")
    private Long id;

    @Column(name = "SHMT_CARRIER", nullable = false, length = 32)
    private String carrier;

    @Column(name = "SHMT_TRACKING_NUMBER", length = 64, nullable = false)
    private String trackingNumber;

    @Column(name = "SHMT_FIRST_NAME", length = 32, nullable = false)
    private String firstName;

    @Column(name = "SHMT_LAST_NAME", length = 32, nullable = false)
    private String lastName;

    @Column(name = "SHMT_ADDRESS_1", length = 128, nullable = false)
    private String address1;

    @Column(name = "SHMT_ADDRESS_2", length = 128, nullable = false)
    private String address2;

    @Column(name = "SHMT_CITY", length = 32, nullable = false)
    private String city;

    @Column(name = "SHMT_STATE", length = 32, nullable = false)
    private String state;

    @Column(name = "SHMT_ZIP_CODE", length = 16, nullable = false)
    private String zipCode;

    @Column(name = "SHMT_COUNTRY", length = 32, nullable = false)
    private String country;

    @Column(name = "SHMT_CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "SHMT_UPDATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "SHMT_REMARK")
    private String remark;

    @Column(name = "SHMT_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "SHMT_SHST_ID")
    private ShipmentStatus shipmentStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "SHMT_ORDR_ID")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
