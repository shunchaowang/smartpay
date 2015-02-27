package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.RefundStatusDao;
import com.lambo.smartpay.model.RefundStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("refundStatusDao")
public class RefundStatusDaoImpl extends LookupGenericDaoImpl<RefundStatus, Long>
        implements RefundStatusDao {
}
