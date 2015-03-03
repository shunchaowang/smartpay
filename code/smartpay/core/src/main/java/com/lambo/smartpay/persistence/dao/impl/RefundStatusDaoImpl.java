package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.persistence.entity.RefundStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("refundStatusDao")
public class RefundStatusDaoImpl extends LookupGenericDaoImpl<RefundStatus, Long>
        implements RefundStatusDao {
}
