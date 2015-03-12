package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.UserDao;
import com.lambo.smartpay.persistence.entity.User;
import com.lambo.smartpay.service.RoleService;
import com.lambo.smartpay.service.UserService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swang on 3/12/2015.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;

    /**
     * Find user by the unique username.
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            logger.debug("Username is blank.");
            return null;
        }
        return userDao.findByUsername(username);
    }

    /**
     * Create a merchant admin for the merchant.
     *
     * @param user
     * @return
     */
    @Override
    public User createMerchantAdmin(User user) {
        return null;
    }

    /**
     * Create a merchant operator for the merchant.
     *
     * @param user
     * @return
     */
    @Override
    public User createMerchantOperator(User user) {
        return null;
    }

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        return null;
    }

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
    @Override
    public List<User> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param user contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(User user) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param user   contains criteria if the field is not null or empty.
     * @param start
     * @param length @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<User> findByAdvanceSearch(User user, Integer start, Integer length) {
        return null;
    }

    @Override
    public User create(User user) throws MissingRequiredFieldException, NotUniqueException {
        return null;
    }

    @Override
    public User get(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public User update(User user) throws MissingRequiredFieldException, NotUniqueException {
        return null;
    }

    @Override
    public User delete(Long id) throws NoSuchEntityException {
        return null;
    }
}
