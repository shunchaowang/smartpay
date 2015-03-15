package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.ShipmentStatusDao;
import com.lambo.smartpay.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.service.ShipmentStatusService;
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
@Service("shipmentStatusService")
public class ShipmentStatusServiceImpl implements ShipmentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentStatusServiceImpl.class);
    @Autowired
    private ShipmentStatusDao shipmentStatusDao;

    /**
     * Find ShipmentStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ShipmentStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return shipmentStatusDao.findByName(name);
    }

    /**
     * Find ShipmentStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ShipmentStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return shipmentStatusDao.findByCode(code);
    }

    /**
     * Create a new ShipmentStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param shipmentStatus
     * @return
     */
    @Transactional
    @Override
    public ShipmentStatus create(ShipmentStatus shipmentStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        if (shipmentStatus == null) {
            throw new MissingRequiredFieldException("ShipmentStatus is null.");
        }
        if (StringUtils.isBlank(shipmentStatus.getName()) ||
                StringUtils.isBlank(shipmentStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (shipmentStatusDao.findByName(shipmentStatus.getName()) != null) {
            throw new NotUniqueException("ShipmentStatus with name " + shipmentStatus.getName() +
                    " already exists.");
        }
        if (shipmentStatusDao.findByCode(shipmentStatus.getCode()) != null) {
            throw new NotUniqueException("ShipmentStatus with code " + shipmentStatus.getName() +
                    " already exists.");
        }
        shipmentStatus.setActive(true);
        // pass all checks, create the object
        return shipmentStatusDao.create(shipmentStatus);
    }

    /**
     * Get ShipmentStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Override
    public ShipmentStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (shipmentStatusDao.get(id) == null) {
            throw new NoSuchEntityException("ShipmentStatus with id " + id +
                    " does not exist.");
        }
        return shipmentStatusDao.get(id);
    }

    /**
     * Update an existing ShipmentStatus.
     * Must check unique fields if are unique.
     *
     * @param shipmentStatus
     * @return
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
     */
    @Transactional
    @Override
    public ShipmentStatus update(ShipmentStatus shipmentStatus) throws
            MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (shipmentStatus == null) {
            throw new MissingRequiredFieldException("ShipmentStatus is null.");
        }
        if (shipmentStatus.getId() == null) {
            throw new MissingRequiredFieldException("ShipmentStatus id is null.");
        }
        if (StringUtils.isBlank(shipmentStatus.getName()) ||
                StringUtils.isBlank(shipmentStatus.getCode())) {
            throw new MissingRequiredFieldException("ShipmentStatus name or code is blank.");
        }
        // checking uniqueness
        ShipmentStatus namedShipmentStatus = shipmentStatusDao.findByName(shipmentStatus.getName());
        if (namedShipmentStatus != null &&
                !namedShipmentStatus.getId().equals(shipmentStatus.getId())) {
            throw new NotUniqueException("ShipmentStatus with name " + shipmentStatus.getName() +
                    " already exists.");
        }
        ShipmentStatus codedShipmentStatus = shipmentStatusDao.findByCode(shipmentStatus.getCode());
        if (codedShipmentStatus != null &&
                !codedShipmentStatus.getId().equals(shipmentStatus.getId())) {
            throw new NotUniqueException("ShipmentStatus with code " + shipmentStatus.getCode() +
                    " already exists.");
        }
        return shipmentStatusDao.update(shipmentStatus);
    }

    /**
     * Delete an existing ShipmentStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public ShipmentStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        ShipmentStatus shipmentStatus = shipmentStatusDao.get(id);
        if (shipmentStatus == null) {
            throw new NoSuchEntityException("ShipmentStatus with id " + id +
                    " does not exist.");
        }
        shipmentStatusDao.delete(id);
        return shipmentStatus;
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
    public List<ShipmentStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                                  String order, ResourceProperties.JpaOrderDir
            orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param shipmentStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(ShipmentStatus shipmentStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param shipmentStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length         @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<ShipmentStatus> findByAdvanceSearch(ShipmentStatus shipmentStatus, Integer start,
                                                    Integer length) {
        return null;
    }

    //TODO newly added methods

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param shipmentStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param search         instance wildcard search keyword, like name likes %xx%, etc.
     *                       it means no criteria with wildcard search if search is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(ShipmentStatus shipmentStatus, String search) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param shipmentStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(ShipmentStatus shipmentStatus) {
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
     * @param shipmentStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param search         instance wildcard search keyword, like name likes %xx%, etc.
     *                       it means no criteria with wildcard search if search is null.
     * @param start          first position of the result.
     * @param length         max record of the result.
     * @param order          order by field, default is id.
     * @param orderDir       order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<ShipmentStatus> findByCriteria(ShipmentStatus shipmentStatus, String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param shipmentStatus contains all criteria for equals, like name equals xx and active equals
     *                       true, etc.
     *                       it means no criteria on exact equals if t is null.
     * @param start          first position of the result.
     * @param length         max record of the result.
     * @param order          order by field, default is id.
     * @param orderDir       order direction on the order field. default is DESC.
     * @return
     */
    @Override
    public List<ShipmentStatus> findByCriteria(ShipmentStatus shipmentStatus, Integer start,
                                               Integer length, String order, ResourceProperties
            .JpaOrderDir orderDir) {
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
    public List<ShipmentStatus> findByCriteria(String search, Integer start, Integer length, String order, ResourceProperties.JpaOrderDir orderDir) {
        return null;
    }
}
