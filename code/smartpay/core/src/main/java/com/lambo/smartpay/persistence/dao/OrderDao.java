package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Order;

/**
 * Created by swang on 3/7/2015.
 */
public interface OrderDao extends GenericDateQueryDao<Order, Long> {
    Order findByMerchantNumber(String merchantNumber);
}
