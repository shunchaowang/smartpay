package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.Site;

/**
 * Created by swang on 3/10/2015.
 * Modified by linly on 3/14/2015
 */
public interface SiteService extends GenericQueryService<Site, Long> {

    /**
     * Find site by the unique sitename.
     *
     * @param name
     * @return
     */
    Site findByName(String name);

}
