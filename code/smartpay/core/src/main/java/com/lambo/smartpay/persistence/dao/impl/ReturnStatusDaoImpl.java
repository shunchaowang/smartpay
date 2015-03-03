package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.ReturnStatusDao;
import com.lambo.smartpay.persistence.entity.ReturnStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("returnStatusDao")
public class ReturnStatusDaoImpl extends LookupGenericDaoImpl<ReturnStatus, Long>
        implements ReturnStatusDao {
}
