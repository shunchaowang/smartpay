package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.persistence.entity.Shipment;

/**
 * Created by swang on 3/25/2015.
 */
public interface ShipmentService extends GenericQueryService<Shipment, Long> {

    Shipment findByTrackingNumber(String trackingNumber);

}
