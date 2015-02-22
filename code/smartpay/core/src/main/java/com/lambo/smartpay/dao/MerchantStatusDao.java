package com.lambo.smartpay.dao;

import com.lambo.smartpay.model.MerchantStatus;
import com.lambo.smartpay.util.ResourceUtil;

import java.util.List;

/**
 * Created by swang on 2/17/2015.
 */
public interface MerchantStatusDao extends GenericDao<MerchantStatus, Long> {

    /**
     * Find the named MerchantStatus.
     * */
    MerchantStatus findByName(String name);

    /**
     * Count the number of the record using search keyword against all attributes of MerchantStatus,
     * including direct many to one relationships..
     * @param search the keyword to search, eg. m.name LIKE *lambo*
     * @return count of the result
     * */
    Long countByAdHocSearch(String search);

    /**
     * Find all records using search keyword against all attributes of MerchantStatus,
     * including direct many to one relationships.
     * @param search keyword to search eg. m.name LIKE *lambo*
     * @param start the offset of the result list
     * @param length total count of the result list
     * @param order which column to order the result, including the direct relationship
     * @param orderDir direction of the order, ASC or DESC
     * @return List of MerchantStatus matching search, starting from start offest and max of length
     * */
    List<MerchantStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                           String order, ResourceUtil.JpaOrderDir orderDir);
}
