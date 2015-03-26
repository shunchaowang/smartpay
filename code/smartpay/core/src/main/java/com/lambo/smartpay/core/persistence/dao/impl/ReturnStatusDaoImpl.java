package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.ReturnStatusDao;
import com.lambo.smartpay.core.persistence.entity.ReturnStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("returnStatusDao")
public class ReturnStatusDaoImpl extends GenericLookupDaoImpl<ReturnStatus, Long>
        implements ReturnStatusDao {
}
