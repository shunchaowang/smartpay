package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.Site;

/**
 * Created by swang on 3/6/2015.
 * Modified by linly on 3/14/2015.
 */
public interface SiteDao extends GenericQueryDao<Site, Long> {

    Site findByName(String name);

}
