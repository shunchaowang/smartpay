package com.lambo.smartpay.dao;

import com.lambo.smartpay.model.Administrator;
import com.lambo.smartpay.util.ResourceUtil;

import java.util.List;

/**
 * Dao for administrator.
 * Created by swang on 2/27/2015.
 */
public interface AdministratorDao extends GenericDao<Administrator, Long> {

    /**
     * Find Administrator by username.
     *
     * @param username username trying to find
     * @return administrator with the username or null if not existing
     */
    Administrator findByUsername(String username);

    /**
     * Find Administrator by email.
     *
     * @param email email trying to find
     * @return administrator with the email or null if not existing
     */
    Administrator findByEmail(String email);

    /**
     * Count number of Administrator matching the search.
     * Support ad hoc search on username,firstName, lastName, email.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    Long countByAdHocSearch(String search, Boolean activeFlag);

    /**
     * Find all Administrator matching the search.
     * Support ad hoc search on username, firstName, lastName, email.
     * Support order by id, username, firstName, lastName, email, active, createdTime.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the Administrator.
     */
    List<Administrator> findByAdHocSearch(String search, Integer start, Integer length,
                                          String order, ResourceUtil.JpaOrderDir orderDir,
                                          Boolean activeFlag);

    /**
     * Count Administrator by criteria.
     * Support query on id, username, firstName, lastName, email, active.
     *
     * @param administrator contains criteria if the field is not null or empty.
     * @return number of the Administrator matching search.
     */
    Long countByAdvanceSearch(Administrator administrator);

    /**
     * Find Administrator by criteria.
     * Support query on id, username, firstName, lastName, email, active.
     * Support order by id, username, firstName, lastName, email, active, createdTime.
     *
     * @param administrator contains criteria if the field is not null or empty.
     * @return List of the Administrator matching search ordered by id without pagination.
     */
    List<Administrator> findByAdvanceSearch(Administrator administrator);
}
