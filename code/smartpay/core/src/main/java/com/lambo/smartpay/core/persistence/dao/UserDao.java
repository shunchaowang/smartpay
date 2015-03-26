package com.lambo.smartpay.core.persistence.dao;

import com.lambo.smartpay.core.persistence.entity.User;

/**
 * Created by swang on 3/9/2015.
 */
public interface UserDao extends GenericQueryDao<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
