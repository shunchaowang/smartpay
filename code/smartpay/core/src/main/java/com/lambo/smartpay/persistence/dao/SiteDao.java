package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Site;

/**
 * Created by swang on 3/6/2015.
 */
public interface SiteDao extends GenericQueryDao<Site, Long> {
    Site findByName(String name);
}
