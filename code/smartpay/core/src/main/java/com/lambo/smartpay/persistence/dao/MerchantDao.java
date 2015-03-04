package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.util.ResourceUtil;

import java.util.List;

/**
 * Created by swang on 2/19/2015.
 */
public interface MerchantDao extends GenericDao<Merchant, Long> {

    /**
     * Find merchant by name.
     *
     * @param name name of the merchant.
     * @return return the object if found, null if not found.
     */
    Merchant findByName(String name);

    /**
     * Count number of Merchant matching the search. Support ad hoc search on name, contact, tel,
     * email and name of MerchantStatus.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all Merchant matching the search. Support ad hoc search on name, contact, tel, email
     * and name of MerchantStatus.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Merchant.
     */
    List<Merchant> findByAdHocSearch(String search, Integer start, Integer length,
                                     String order, ResourceUtil.JpaOrderDir orderDir,
                                     Boolean activeFlag);

    /**
     * Count Merchant by criteria.
     * Support id, name, active, MerchantStatus code.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @return number of the Merchant matching search.
     */
    Long countByAdvanceSearch(Merchant merchant);

    /**
     * Find Merchant by criteria.
     * Support id, name, active, MerchantStatus code.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @return List of the Merchant matching search ordered by id without pagination.
     */
    List<Merchant> findByAdvanceSearch(Merchant merchant);
}
