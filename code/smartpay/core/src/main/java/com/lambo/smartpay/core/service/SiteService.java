package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Site;

/**
 * Created by swang on 3/10/2015.
 * Modified by linly on 3/14/2015
 */
public interface SiteService extends GenericQueryService<Site, Long> {

    Site findByIdentity(String identity);

    Site findByUrl(String url);

    Site findByReturnUrl(String returnUrl);

    Site freezeSite(Long id) throws NoSuchEntityException;

    Site unfreezeSite(Long id) throws NoSuchEntityException;

    Site approveSite(Long id) throws NoSuchEntityException;

    Site declineSite(Long id) throws NoSuchEntityException;

    Boolean canOperate(Long id) throws NoSuchEntityException;
}
