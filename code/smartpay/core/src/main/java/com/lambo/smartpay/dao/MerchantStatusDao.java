package com.lambo.smartpay.dao;

import com.lambo.smartpay.exception.EntityNotFoundException;
import com.lambo.smartpay.model.MerchantStatus;
import com.lambo.smartpay.util.ResourceUtil;

import java.util.List;

/**
 * Created by swang on 2/17/2015.
 */
public interface MerchantStatusDao extends GenericDao<MerchantStatus, Long> {

    /**
     * Find the named MerchantStatus.
     */
    MerchantStatus findByName(String name);

    /**
     * Count the number of all MerchantStatus.
     * @param search the keyword to search, eg. m.name LIKE *lambo*
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return count of the result
     */
    public Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all MerchantStatus.
     * @param search keyword to search eg. m.name LIKE *lambo*
     * @param start the offset of the result list
     * @param length total count of the result list
     * @param order which column to order the result, including the direct relationship
     * @param orderDir direction of the order, ASC or DESC
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return List of MerchantStatus matching search, starting from start offest and max of length
     */
    public List<MerchantStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceUtil.JpaOrderDir orderDir,
                                                  Boolean activeFlag);

    /**
     * Active toggle of the MerchantStatus, active from true to false and vice versa.
     * @param id identifier of the MerchantStatus
     * @param activeFlag true to active and false to deactivate
     * @ the archived MerchantStatus
     */
    public MerchantStatus switchMerchantStatus(Long id, boolean activeFlag) throws EntityNotFoundException;

}
