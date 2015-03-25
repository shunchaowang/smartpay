package com.lambo.smartpay.service;

import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.persistence.entity.Site;

/**
 * Created by swang on 3/10/2015.
 * Modified by linly on 3/14/2015
 */
public interface SiteService extends GenericQueryService<Site, Long> {

    /**
     * Find site by the unique site name.
     *
     * @param name
     * @return
     */
    Site findByName(String name);

    Site freezeSite(Long id) throws NoSuchEntityException;

    Site unfreezeSite(Long id) throws NoSuchEntityException;

    Site approveSite(Long id) throws NoSuchEntityException;

    Site declineSite(Long id) throws NoSuchEntityException;

}
