package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.ShipmentDao;
import com.lambo.smartpay.core.persistence.entity.Shipment;
import com.lambo.smartpay.core.service.ShipmentService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/25/2015.
 */
@Service("shipmentService")
public class ShipmentServiceImpl extends GenericQueryServiceImpl<Shipment, Long>
        implements ShipmentService {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);
    @Autowired
    private ShipmentDao shipmentDao;

    @Override
    public Shipment findByTrackingNumber(String trackingNumber) {
        return shipmentDao.findByTrackingNumber(trackingNumber);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param shipment contains all criteria for equals, like name equals xx and active
     *                 equals
     *                 true, etc.
     * @param search   instance wildcard search keyword, like name likes %xx%, etc.
     *                 it means no criteria with wildcard search if search is null.
     *                 it means no criteria on exact equals if t is null.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Shipment shipment, String search) {
        return shipmentDao.countByCriteria(shipment, search);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param shipment contains all criteria for equals, like name equals xx and active
     *                 equals
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
    public List<Shipment> findByCriteria(Shipment shipment, String search, Integer start,
                                         Integer length, String order,
                                         ResourceProperties.JpaOrderDir orderDir) {
        return shipmentDao.findByCriteria(shipment, search, start, length,
                order, orderDir);
    }

    @Override
    public Shipment create(Shipment shipment)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (shipment == null) {
            throw new MissingRequiredFieldException("Shipment is null.");
        }
        if (StringUtils.isBlank(shipment.getCarrier())) {
            throw new MissingRequiredFieldException("Carrier is blank.");
        }
        if (StringUtils.isBlank(shipment.getTrackingNumber())) {
            throw new MissingRequiredFieldException("Tracking number is blank.");
        }
        if (StringUtils.isBlank(shipment.getFirstName())) {
            throw new MissingRequiredFieldException("Shipping first name is blank.");
        }
        if (StringUtils.isBlank(shipment.getLastName())) {
            throw new MissingRequiredFieldException("Shipping last name is blank.");
        }
        if (StringUtils.isBlank(shipment.getAddress1())) {
            throw new MissingRequiredFieldException("Shipping address is blank.");
        }
        if (StringUtils.isBlank(shipment.getCity())) {
            throw new MissingRequiredFieldException("Shipping city is blank.");
        }
        if (StringUtils.isBlank(shipment.getState())) {
            throw new MissingRequiredFieldException("Shipping state is blank.");
        }
        if (StringUtils.isBlank(shipment.getCountry())) {
            throw new MissingRequiredFieldException("Shipping country is blank.");
        }
        if (StringUtils.isBlank(shipment.getZipCode())) {
            throw new MissingRequiredFieldException("Shipping zip code is blank.");
        }
        if (shipment.getShipmentStatus() == null) {
            throw new MissingRequiredFieldException("Shipment status is null.");
        }
        if (shipment.getOrder() == null) {
            throw new MissingRequiredFieldException("Shipment order is null.");
        }

        shipment.setActive(true);
        shipment.setCreatedTime(date);

        return shipmentDao.create(shipment);
    }

    @Override
    public Shipment get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Shipment shipment = shipmentDao.get(id);
        if (shipment == null) {
            throw new NoSuchEntityException("Shipment is null.");
        }
        return shipment;
    }

    @Override
    public Shipment update(Shipment shipment)
            throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (shipment == null) {
            throw new MissingRequiredFieldException("Shipment is null.");
        }
        if (shipment.getId() == null) {
            throw new MissingRequiredFieldException("Shipment id is null.");
        }
        if (StringUtils.isBlank(shipment.getCarrier())) {
            throw new MissingRequiredFieldException("Carrier is blank.");
        }
        if (StringUtils.isBlank(shipment.getTrackingNumber())) {
            throw new MissingRequiredFieldException("Tracking number is blank.");
        }
        if (StringUtils.isBlank(shipment.getFirstName())) {
            throw new MissingRequiredFieldException("Shipping first name is blank.");
        }
        if (StringUtils.isBlank(shipment.getLastName())) {
            throw new MissingRequiredFieldException("Shipping last name is blank.");
        }
        if (StringUtils.isBlank(shipment.getAddress1())) {
            throw new MissingRequiredFieldException("Shipping address is blank.");
        }
        if (StringUtils.isBlank(shipment.getCity())) {
            throw new MissingRequiredFieldException("Shipping city is blank.");
        }
        if (StringUtils.isBlank(shipment.getState())) {
            throw new MissingRequiredFieldException("Shipping state is blank.");
        }
        if (StringUtils.isBlank(shipment.getCountry())) {
            throw new MissingRequiredFieldException("Shipping country is blank.");
        }
        if (StringUtils.isBlank(shipment.getZipCode())) {
            throw new MissingRequiredFieldException("Shipping zip code is blank.");
        }
        if (shipment.getShipmentStatus() == null) {
            throw new MissingRequiredFieldException("Shipment status is null.");
        }
        if (shipment.getOrder() == null) {
            throw new MissingRequiredFieldException("Shipment order is null.");
        }

        shipment.setUpdatedTime(date);

        return shipmentDao.update(shipment);
    }

    @Override
    public Shipment delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Shipment shipment = shipmentDao.get(id);
        if (shipment == null) {
            throw new NoSuchEntityException("Shipment is null.");
        }
        shipmentDao.delete(id);
        return shipment;
    }

    @Override
    public List<Shipment> getAll() {
        return shipmentDao.getAll();
    }

    @Override
    public Long countAll() {
        return shipmentDao.countAll();
    }
}
