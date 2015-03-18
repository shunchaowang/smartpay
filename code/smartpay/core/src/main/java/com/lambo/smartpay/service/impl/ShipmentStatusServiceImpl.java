package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.ShipmentStatusDao;
import com.lambo.smartpay.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.service.ShipmentStatusService;
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

    @Override
    public List<ShipmentStatus> getAll() {
        return shipmentStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return shipmentStatusDao.countAll();
    }
}
