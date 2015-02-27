package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.ReturnStatusDao;
import com.lambo.smartpay.model.ReturnStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("returnStatusDao")
public class ReturnStatusDaoImpl extends LookupGenericDaoImpl<ReturnStatus, Long>
        implements ReturnStatusDao {
}
