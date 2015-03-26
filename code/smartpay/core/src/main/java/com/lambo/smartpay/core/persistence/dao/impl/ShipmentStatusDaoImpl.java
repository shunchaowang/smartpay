package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.core.persistence.dao.ShipmentStatusDao;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("shipmentStatusDao")
public class ShipmentStatusDaoImpl extends GenericLookupDaoImpl<ShipmentStatus, Long>
        implements ShipmentStatusDao {
}
