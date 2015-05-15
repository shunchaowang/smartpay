package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.util.ResourceProperties;

import java.util.List;

/**
 * There are 3 built-in roles in system, admin for the administration of the system, they can do
 * anything;
 * merchant admin can do the administration of his own merchant;
 * merchant operator can only do partial operation on his own merchant.
 * Created by swang on 3/10/2015.
 */
public interface UserService extends GenericQueryService<User, Long> {

    /**
     * Find user by the unique username.
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByEmail(String email);

    /**
     * Archive a user.
     *
     * @param id
     * @return
     */
    User archiveUser(Long id) throws NoSuchEntityException;

    /**
     * Restore a user.
     *
     * @param id
     * @return
     */
    User restoreUser(Long id) throws NoSuchEntityException;

    Long countByCriteriaWithExclusion(User includedUser, User excludedUser, String search);

    Long countByCriteriaWithExclusion(User includedUser, User excludedUser);

    List<User> findByCriteriaWithExclusion(User includedUser, User excludedUser, String search,
                                           Integer start, Integer length,
                                           String order, ResourceProperties.JpaOrderDir orderDir);
}
