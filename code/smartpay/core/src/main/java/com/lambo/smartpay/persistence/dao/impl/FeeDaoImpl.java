package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.FeeDao;
import com.lambo.smartpay.persistence.entity.Fee;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/3/2015.
 */
@Repository("feeDao")
public class FeeDaoImpl extends GenericDaoImpl<Fee, Long>
        implements FeeDao {
}
