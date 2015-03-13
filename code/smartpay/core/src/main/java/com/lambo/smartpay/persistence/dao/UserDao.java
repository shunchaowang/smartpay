package com.lambo.smartpay.persistence.dao;

import com.lambo.smartpay.persistence.entity.User;

import java.util.List;

/**
 * Created by swang on 3/9/2015.
 */
public interface UserDao extends GenericQueryDao<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
