package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.RoleDao;
import com.lambo.smartpay.persistence.entity.Role;
import com.lambo.smartpay.service.RoleService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service needs to check if the parameters passed in are null or empty.
 * Service also takes care of Transactional management.
 * Created by swang on 3/9/2015.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDao roleDao;

    /**
     * Find Role by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Role findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return roleDao.findByName(name);
    }

    /**
     * Find Role by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Role findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return roleDao.findByCode(code);
    }

    /**
     * Create a new Role.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param role
     * @return
     */
    @Transactional
    @Override
    public Role create(Role role) throws MissingRequiredFieldException,
            NotUniqueException {
        if (role == null) {
            throw new MissingRequiredFieldException("Role is null.");
        }
        if (StringUtils.isBlank(role.getName()) ||
                StringUtils.isBlank(role.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (roleDao.findByName(role.getName()) != null) {
            throw new NotUniqueException("Role with name " + role.getName() +
                    " already exists.");
        }
        if (roleDao.findByCode(role.getCode()) != null) {
            throw new NotUniqueException("Role with code " + role.getName() +
                    " already exists.");
        }
        role.setActive(true);
        // pass all checks, create the object
        return roleDao.create(role);
    }

    /**
     * Get Role by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Role get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (roleDao.get(id) == null) {
            throw new NoSuchEntityException("Role with id " + id +
                    " does not exist.");
        }
        return roleDao.get(id);
    }

    /**
     * Update an existing Role.
     * Must check unique fields if are unique.
     *
     * @param role
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public Role update(Role role) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (role == null) {
            throw new MissingRequiredFieldException("Role is null.");
        }
        if (role.getId() == null) {
            throw new MissingRequiredFieldException("Role id is null.");
        }
        if (StringUtils.isBlank(role.getName()) ||
                StringUtils.isBlank(role.getCode())) {
            throw new MissingRequiredFieldException("Role name or code is blank.");
        }
        // checking uniqueness
        Role namedRole = roleDao.findByName(role.getName());
        if (namedRole != null &&
                !namedRole.getId().equals(role.getId())) {
            throw new NotUniqueException("Role with name " + role.getName() +
                    " already exists.");
        }
        Role codedRole = roleDao.findByCode(role.getCode());
        if (codedRole != null &&
                !codedRole.getId().equals(role.getId())) {
            throw new NotUniqueException("Role with code " + role.getCode() +
                    " already exists.");
        }
        return roleDao.update(role);
    }

    /**
     * Delete an existing Role.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public Role delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Role role = roleDao.get(id);
        if (role == null) {
            throw new NoSuchEntityException("Role with id " + id +
                    " does not exist.");
        }
        roleDao.delete(id);
        return role;
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
    public List<Role> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceProperties.JpaOrderDir orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param role contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Role role) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param role   contains criteria if the field is not null or empty.
     * @param start
     * @param length @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Role> findByAdvanceSearch(Role role, Integer start, Integer length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param role   contains all criteria for equals, like name equals xx and active equals
     *               true, etc.
     *               it means no criteria on exact equals if t is null.
     * @param search instance wildcard search keyword, like name likes %xx%, etc.
     *               it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Role role, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param role contains all criteria for equals, like name equals xx and active equals
     *             true, etc.
     *             it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Role role) {
        return null;
    }

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
    @Override
    public Long countByCriteria(String search) {
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
     * @param role     contains all criteria for equals, like name equals xx and active equals
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
    public List<Role> findByCriteria(Role role, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param role     contains all criteria for equals, like name equals xx and active equals
     *                 true, etc.
     *                 it means no criteria on exact equals if t is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Role> findByCriteria(Role role, Integer start, Integer length, String order,
                                     ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     * @param start    first position of the result.
     * @param length   max record of the result.
     * @param order    order by field, default is id.
     * @param orderDir order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Role> findByCriteria(String search, Integer start, Integer length, String order,
                                     ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }
}
