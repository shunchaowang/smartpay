package com.lambo.smartpay.dao;

import com.lambo.smartpay.util.ResourceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Generic lookup dao for all lookup domains, including *Status and *Type objects.
 * Is the super interface for all Dao of the lookup objects.
 * <p/>
 * Created by swang on 2/24/2015.
 */
public interface LookupGenericDao<T, PK extends Serializable> extends GenericDao<T, PK> {

    /**
     * Find object by name.
     *
     * @param name is name of the object.
     * @return generic object specified by name.
     */
    T findByName(String name);

    /**
     * Find object by code.
     *
     * @param code is code of the object.
     * @return generic object specified by code.
     */
    T findByCode(String code);

    /**
     * Count the number of all object.
     *
     * @param search     the keyword to search, eg. m.name LIKE *lambo*
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return count of the result
     */
    public Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all objects.
     *
     * @param search     keyword to search eg. m.name LIKE *lambo*
     * @param start      the offset of the result list
     * @param length     total count of the result list
     * @param order      which column to order the result, including the direct relationship
     * @param orderDir   direction of the order, ASC or DESC
     * @param activeFlag indicates all, active or archived,
     *                   null means all, true means active and false means archived.
     * @return List of MerchantStatus matching search, starting from start offest and max of length
     */
    public List<T> findByAdHocSearch(String search, Integer start, Integer length,
                                     String order, ResourceUtil.JpaOrderDir orderDir,
                                     Boolean activeFlag);
}
