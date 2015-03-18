package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.ShipmentStatusDao;
import com.lambo.smartpay.persistence.entity.ShipmentStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("shipmentStatusDao")
public class ShipmentStatusDaoImpl extends GenericLookupDaoImpl<ShipmentStatus, Long>
        implements ShipmentStatusDao {
}
