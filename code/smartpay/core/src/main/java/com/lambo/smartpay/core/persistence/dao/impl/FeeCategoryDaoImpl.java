package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.FeeCategoryDao;
import com.lambo.smartpay.core.persistence.entity.FeeCategory;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("feeCategoryDao")
public class FeeCategoryDaoImpl extends GenericLookupDaoImpl<FeeCategory, Long>
        implements FeeCategoryDao {
}
