package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.FeeTypeDao;
import com.lambo.smartpay.model.FeeType;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("feeTypeDao")
public class FeeTypeDaoImpl extends LookupGenericDaoImpl<FeeType, Long>
        implements FeeTypeDao {
}
