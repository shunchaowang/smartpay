package com.lambo.smartpay.dao;

import com.lambo.smartpay.model.Merchant;
import com.lambo.smartpay.util.ResourceUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by swang on 2/19/2015.
 */
public interface MerchantDao extends GenericDao<Merchant, Long> {

    /**
     * Count number of Merchant matching the search. Support ad hoc search on name, contact, tel, email and name
     * of MerchantStatus.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    public Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all Merchant matching the search. Support ad hoc search on name, contact, tel, email and name
     * of MerchantStatus.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Merchant.
     */
    public List<Merchant> findByAdHocSearch(String search, Integer start, Integer length,
                                            String order, ResourceUtil.JpaOrderDir orderDir,
                                            Boolean activeFlag);

    /**
     * Count Merchant by criteria, created time range.
     * Support id, name, MerchantStatus code and createdTime range.
     *
     * @param merchant         contains criteria if the field is not null or empty.
     * @param createdTimeBegin is the beginning of the created time range if not null.
     * @param createdTimeEnd   is the ending of the created time range if not null.
     * @return number of the Merchant matching search.
     */
    public Long countByAdvanceSearch(Merchant merchant, Date createdTimeBegin, Date createdTimeEnd);

    /**
     * Find Merchant by criteria, created time range.
     * Support id, name, active, MerchantStatus code and createdTime range.
     *
     * @param merchant         contains criteria if the field is not null or empty.
     * @param createdTimeBegin is the beginning of the created time range if not null.
     * @param createdTimeEnd   is the ending of the created time range if not null.
     * @return List of the Merchant matching search ordered by id without pagination.
     */
    public List<Merchant> findByAdvanceSearch(Merchant merchant, Date createdTimeBegin, Date createdTimeEnd);
}
