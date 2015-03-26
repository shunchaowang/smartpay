package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("siteStatusDao")
public class SiteStatusDaoImpl extends GenericLookupDaoImpl<SiteStatus, Long>
        implements SiteStatusDao {
}
