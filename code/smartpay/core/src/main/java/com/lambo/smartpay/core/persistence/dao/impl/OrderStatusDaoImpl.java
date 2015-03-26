package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.OrderStatusDao;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("orderStatusDao")
public class OrderStatusDaoImpl extends GenericLookupDaoImpl<OrderStatus, Long>
        implements OrderStatusDao {
}
