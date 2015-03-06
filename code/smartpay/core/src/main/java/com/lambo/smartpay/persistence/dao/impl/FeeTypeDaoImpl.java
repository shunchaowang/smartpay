package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.entity.FeeType;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("feeTypeDao")
public class FeeTypeDaoImpl extends LookupGenericDaoImpl<FeeType, Long>
        implements FeeTypeDao {
}