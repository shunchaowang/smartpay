package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.Administrator;

/**
 * Dao for administrator.
 * Created by swang on 2/27/2015.
 */
public interface AdministratorDao extends GenericQueryDao<Administrator, Long> {

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
}
