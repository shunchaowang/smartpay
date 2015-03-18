package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.SiteStatusDao;
import com.lambo.smartpay.persistence.entity.SiteStatus;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 2/26/2015.
 */
@Repository("siteStatusDao")
public class SiteStatusDaoImpl extends GenericLookupDaoImpl<SiteStatus, Long>
        implements SiteStatusDao {
}
