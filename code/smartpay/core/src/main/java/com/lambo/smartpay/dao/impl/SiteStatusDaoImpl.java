package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.SiteStatusDao;
import com.lambo.smartpay.model.SiteStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("siteStatusDao")
public class SiteStatusDaoImpl extends LookupGenericDaoImpl<SiteStatus, Long>
        implements SiteStatusDao {
}
