package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("feeTypeDao")
public class FeeTypeDaoImpl extends GenericLookupDaoImpl<FeeType, Long>
        implements FeeTypeDao {
}
