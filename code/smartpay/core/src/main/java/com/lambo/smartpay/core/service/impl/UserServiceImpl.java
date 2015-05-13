package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.UserDao;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;

/**
 * Created by swang on 3/12/2015.
 * Modified by Linly on 3/15/2015.
 */
@Service("userService")
public class UserServiceImpl extends GenericQueryServiceImpl<User, Long> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    /**
     * Find user by the unique username for manage, or merchant identity/username for ecs.
     * Username is stored at merchantIdentity/username, on manage it's username without merchant.
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

        CriteriaBuilder builder = userDao.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Path<String> path = root.get("username");
        Predicate predicate = builder.equal(path, username);
        query.where(predicate);
        TypedQuery<User> typedQuery = userDao.createQuery(query);

        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            logger.debug("Email is blank.");
            return null;
        }
        return userDao.findByEmail(email);
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

        return userDao.countByCriteria(user, search);
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
    public List<User> findByCriteria(User user, String search, Integer start, Integer length,
                                     String order, ResourceProperties.JpaOrderDir orderDir) {

        return userDao.findByCriteria(user, search, start, length, order, orderDir);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Long countAll() {
        return userDao.countAll();
    }
}
