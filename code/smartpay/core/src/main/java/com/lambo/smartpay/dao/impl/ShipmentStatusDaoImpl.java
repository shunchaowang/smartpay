package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.ShipmentStatusDao;
import com.lambo.smartpay.model.ShipmentStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("shipmentStatusDao")
public class ShipmentStatusDaoImpl extends LookupGenericDaoImpl<ShipmentStatus, Long>
        implements ShipmentStatusDao {
}
