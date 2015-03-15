package com.lambo.smartpay.service;

import com.lambo.smartpay.util.ResourceProperties;

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
                              String order, ResourceProperties.JpaOrderDir orderDir,
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

    //TODO newly methods from here to replace all above searches

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param t      contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    Long countByCriteria(T t, String search);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param t contains all criteria for equals, like name equals xx and active equals
     *          true, etc.
     *          it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    Long countByCriteria(T t);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * <p/>
     * it means no criteria on exact equals if t is null.
     *
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    Long countByCriteria(String search);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param t        contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    List<T> findByCriteria(T t, String search, Integer start, Integer length,
                           String order, ResourceProperties.JpaOrderDir orderDir);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param t        contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    List<T> findByCriteria(T t, Integer start, Integer length,
                           String order, ResourceProperties.JpaOrderDir orderDir);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param t        contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    List<T> findByCriteria(String search, Integer start, Integer length,
                           String order, ResourceProperties.JpaOrderDir orderDir);
}
