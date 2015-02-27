package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.OrderStatusDao;
import com.lambo.smartpay.model.OrderStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("orderStatusDao")
public class OrderStatusDaoImpl extends LookupGenericDaoImpl<OrderStatus, Long>
        implements OrderStatusDao {
}
