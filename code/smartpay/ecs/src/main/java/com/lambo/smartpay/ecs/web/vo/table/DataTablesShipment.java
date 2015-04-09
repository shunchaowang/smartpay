package com.lambo.smartpay.ecs.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 4/8/2015.
 */
public class DataTablesShipment {

    private Long id;
    private String carrier;
    private String trackingNumber;
    private Long orderId;
    private String orderNumber;
    private String createdTime;
    private Long orderStatusId;
    private String orderStatusName;
    private Long shipmentStatusId;
    private String shipmentStatusName;
    private String customerName;
    private String customerAddress;

    public DataTablesShipment() {}
    public DataTablesShipment(Order order) {
        orderId = order.getId();
        orderNumber = order.getMerchantNumber();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        createdTime = dateFormat.format(order.getCreatedTime());
        orderStatusId = order.getOrderStatus().getId();
        orderStatusName = order.getOrderStatus().getName();
        customerName = StringUtils.join(
                new String[]{order.getCustomer().getFirstName(),
                        order.getCustomer().getLastName()}, " ");
        customerAddress = StringUtils.join(
                new String[] {order.getCustomer().getAddress1(), order.getCustomer().getCity(),
                        order.getCustomer().getState(), order.getCustomer().getZipCode(),
                        order.getCustomer().getCountry()}, " ");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Long getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(Long shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
    }

    public String getShipmentStatusName() {
        return shipmentStatusName;
    }

    public void setShipmentStatusName(String shipmentStatusName) {
        this.shipmentStatusName = shipmentStatusName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
