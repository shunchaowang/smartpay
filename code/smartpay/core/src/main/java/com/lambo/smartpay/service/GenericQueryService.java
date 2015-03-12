package com.lambo.smartpay.service;

import com.lambo.smartpay.util.ResourceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by swang on 3/10/2015.
 */
public interface GenericQueryService<T extends Serializable, PK>
        extends GenericService<T, PK> {

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    List<T> findByAdHocSearch(String search, Integer start, Integer length,
                              String order, ResourceUtil.JpaOrderDir orderDir,
                              Boolean activeFlag);

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param t contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    Long countByAdvanceSearch(T t);

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param t contains criteria if the field is not null or empty.
     * @return List of the T matching search ordered by id with pagination.
     */
    List<T> findByAdvanceSearch(T t, Integer start, Integer length);
}