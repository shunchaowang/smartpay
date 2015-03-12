package com.lambo.smartpay.service;

import com.lambo.smartpay.persistence.entity.Site;

/**
 * Created by swang on 3/10/2015.
 */
public interface SiteService extends GenericQueryService<Site, Long> {

    /**
     * Create a site for the merchant.
     *
     * @param site
     * @return
     */
    Site createMerchantSite(Site site);
}
