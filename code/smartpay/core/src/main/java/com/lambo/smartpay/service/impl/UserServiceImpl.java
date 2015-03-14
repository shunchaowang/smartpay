package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.UserDao;
import com.lambo.smartpay.persistence.entity.User;
import com.lambo.smartpay.service.UserService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by swang on 3/12/2015.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

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
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        return userDao.countByAdHocSearch(search, activeFlag);
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
        if (StringUtils.isBlank(search)) {
            logger.info("Search keyword is blank.");
            return null;
        }
        if (start == null) {
            logger.info("Start is null.");
            return null;
        }
        if (length == null) {
            logger.info("Length is null.");
            return null;
        }

        if (order == null) {
            logger.info("Order is null.");
            return null;
        }
        if (orderDir == null) {
            logger.info("OrderDir is null.");
            return null;
        }
        return userDao.findByAdHocSearch(search, start, length, order, orderDir, activeFlag);
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
        if (user == null) {
            logger.info("Merchant is null.");
        }
        return userDao.countByAdvanceSearch(user);
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
        if (user == null) {
            logger.info("Merchant is null.");
        }
        if (start == null) {
            logger.info("Start is null.");
        }
        if (length == null) {
            logger.info("Length is null.");
        }
        return userDao.findByAdvanceSearch(user, start, length);
    }

    @Transactional
    @Override
    public User create(User user) throws MissingRequiredFieldException, NotUniqueException {
        if (user == null) {
            throw new MissingRequiredFieldException("User is null.");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            throw new MissingRequiredFieldException("User username is blank.");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new MissingRequiredFieldException("User password is blank.");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            throw new MissingRequiredFieldException("User email is blank.");
        }
        if (StringUtils.isBlank(user.getFirstName())) {
            throw new MissingRequiredFieldException("User first name is blank.");
        }
        if (StringUtils.isBlank(user.getLastName())) {
            throw new MissingRequiredFieldException("User last name is blank.");
        }
        if (user.getActive() == null) {
            throw new MissingRequiredFieldException("User active is null.");
        }
        if (user.getUserStatus() == null) {
            throw new MissingRequiredFieldException("User status is null.");
        }

        // check uniqueness on username and email
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new NotUniqueException("User with username " + user.getUsername()
                    + " already exists.");
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new NotUniqueException("User with email " + user.getEmail()
                    + " already exists.");
        }

        user.setCreatedTime(Calendar.getInstance().getTime());
        return userDao.create(user);
    }

    @Override
    public User get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (userDao.get(id) == null) {
            throw new NoSuchEntityException("User with id " + id + " does not exist.");
        }
        return userDao.get(id);
    }

    /**
     * Update a user.
     * Username is not allowed to change.
     *
     * @param user
     * @return
     * @throws MissingRequiredFieldException
     * @throws NotUniqueException
     */
    @Transactional
    @Override
    public User update(User user) throws MissingRequiredFieldException, NotUniqueException {
        if (user == null) {
            throw new MissingRequiredFieldException("User is null.");
        }
        if (user.getId() == null) {
            throw new MissingRequiredFieldException("User id is null.");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            throw new MissingRequiredFieldException("User username is blank.");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new MissingRequiredFieldException("User password is blank.");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            throw new MissingRequiredFieldException("User email is blank.");
        }
        if (StringUtils.isBlank(user.getFirstName())) {
            throw new MissingRequiredFieldException("User first name is blank.");
        }
        if (StringUtils.isBlank(user.getLastName())) {
            throw new MissingRequiredFieldException("User last name is blank.");
        }
        if (user.getActive() == null) {
            throw new MissingRequiredFieldException("User active is null.");
        }
        if (user.getUserStatus() == null) {
            throw new MissingRequiredFieldException("User status is null.");
        }
        if (user.getCreatedTime() == null) {
            throw new MissingRequiredFieldException("User created time is null.");
        }

        // check uniqueness on username and email
        User currentUser = userDao.get(user.getId());
        if (!user.getUsername().equals(currentUser.getUsername())) {
            throw new MissingRequiredFieldException("User username cannot be changed.");
        }
        // if the email already in the system
        if ((!user.getEmail().equals(currentUser.getEmail()))
                && (userDao.findByEmail(user.getEmail()) != null)) {
            throw new NotUniqueException("User with email " + user.getEmail() + " already exists.");

        }
        // set updated time if not set
        user.setUpdatedTime(Calendar.getInstance().getTime());
        return userDao.update(user);
    }

    @Transactional
    @Override
    public User delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        User user = userDao.get(id);
        if (user == null) {
            throw new NoSuchEntityException("User with id " + id + " does not exist.");
        }
        userDao.delete(id);
        return user;
    }

    //TODO newly added methods


    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param user   contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(User user, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param user     contains all criteria for equals, like name equals xx and active equals
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
    @Override
    public List<User> findByCriteria(User user, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }
}
