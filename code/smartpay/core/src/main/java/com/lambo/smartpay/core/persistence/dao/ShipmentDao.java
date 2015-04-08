package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.Shipment;

/**
 * Created by swang on 3/9/2015.
 */
public interface ShipmentDao extends GenericQueryDao<Shipment, Long> {

    Shipment findByTrackingNumber(String trackingNumber);
}
