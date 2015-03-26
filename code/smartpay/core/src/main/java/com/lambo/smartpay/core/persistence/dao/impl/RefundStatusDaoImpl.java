package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.RefundStatusDao;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("refundStatusDao")
public class RefundStatusDaoImpl extends GenericLookupDaoImpl<RefundStatus, Long>
        implements RefundStatusDao {
}
