package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.PermissionDao;
import com.lambo.smartpay.persistence.entity.Permission;
import com.lambo.smartpay.service.PermissionService;
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
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Autowired
    private PermissionDao permissionDao;

    /**
     * Find Permission by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Permission findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return permissionDao.findByName(name);
    }

    /**
     * Find Permission by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Permission findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return permissionDao.findByCode(code);
    }

    /**
     * Create a new Permission.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param permission
     * @return
     */
    @Transactional
    @Override
    public Permission create(Permission permission) throws MissingRequiredFieldException,
            NotUniqueException {
        if (permission == null) {
            throw new MissingRequiredFieldException("Permission is null.");
        }
        if (StringUtils.isBlank(permission.getName()) ||
                StringUtils.isBlank(permission.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (permissionDao.findByName(permission.getName()) != null) {
            throw new NotUniqueException("Permission with name " + permission.getName() +
                    " already exists.");
        }
        if (permissionDao.findByCode(permission.getCode()) != null) {
            throw new NotUniqueException("Permission with code " + permission.getName() +
                    " already exists.");
        }
        permission.setActive(true);
        // pass all checks, create the object
        return permissionDao.create(permission);
    }

    /**
     * Get Permission by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public Permission get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (permissionDao.get(id) == null) {
            throw new NoSuchEntityException("Permission with id " + id +
                    " does not exist.");
        }
        return permissionDao.get(id);
    }

    /**
     * Update an existing Permission.
     * Must check unique fields if are unique.
     *
     * @param permission
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public Permission update(Permission permission) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (permission == null) {
            throw new MissingRequiredFieldException("Permission is null.");
        }
        if (permission.getId() == null) {
            throw new MissingRequiredFieldException("Permission id is null.");
        }
        if (StringUtils.isBlank(permission.getName()) ||
                StringUtils.isBlank(permission.getCode())) {
            throw new MissingRequiredFieldException("Permission name or code is blank.");
        }
        // checking uniqueness
        Permission namedPermission = permissionDao.findByName(permission.getName());
        if (namedPermission != null &&
                !namedPermission.getId().equals(permission.getId())) {
            throw new NotUniqueException("Permission with name " + permission.getName() +
                    " already exists.");
        }
        Permission codedPermission = permissionDao.findByCode(permission.getCode());
        if (codedPermission != null &&
                !codedPermission.getId().equals(permission.getId())) {
            throw new NotUniqueException("Permission with code " + permission.getCode() +
                    " already exists.");
        }
        return permissionDao.update(permission);
    }

    /**
     * Delete an existing Permission.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public Permission delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Permission permission = permissionDao.get(id);
        if (permission == null) {
            throw new NoSuchEntityException("Permission with id " + id +
                    " does not exist.");
        }
        permissionDao.delete(id);
        return permission;
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
    public List<Permission> findByAdHocSearch(String search, Integer start, Integer length,
                                              String order, ResourceProperties.JpaOrderDir orderDir,
                                              Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param permission contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Permission permission) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param permission contains criteria if the field is not null or empty.
     * @param start
     * @param length     @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Permission> findByAdvanceSearch(Permission permission, Integer start, Integer
            length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param permission contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Permission permission, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param permission contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Permission permission) {
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
     * @param permission contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param start      first position of the result.
     * @param length     max record of the result.
     * @param order      order by field, default is id.
     * @param orderDir   order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Permission> findByCriteria(Permission permission, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param permission contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param start      first position of the result.
     * @param length     max record of the result.
     * @param order      order by field, default is id.
     * @param orderDir   order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<Permission> findByCriteria(Permission permission, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
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
    public List<Permission> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }
}
