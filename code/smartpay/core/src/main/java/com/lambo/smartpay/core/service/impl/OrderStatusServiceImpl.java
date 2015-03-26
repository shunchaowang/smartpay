package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.OrderStatusDao;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.service.OrderStatusService;
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
@Service("orderStatusService")
public class OrderStatusServiceImpl implements OrderStatusService {

    private static final Logger logger = LoggerFactory.getLogger(OrderStatusServiceImpl.class);
    @Autowired
    private OrderStatusDao orderStatusDao;

    /**
     * Find OrderStatus by name.
     *
     * @param name
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public OrderStatus findByName(String name) throws NoSuchEntityException {
        // check parameters
        if (StringUtils.isBlank(name)) {
            logger.info("Name is blank.");
            return null;
        }
        // call dao
        return orderStatusDao.findByName(name);
    }

    /**
     * Find OrderStatus by code.
     *
     * @param code
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public OrderStatus findByCode(String code) throws NoSuchEntityException {
        if (StringUtils.isBlank(code)) {
            logger.info("Code is blank.");
            return null;
        }
        return orderStatusDao.findByCode(code);
    }

    /**
     * Create a new OrderStatus.
     * Name and Code must not be null and must be unique.
     * The newly created object will be active by default.
     *
     * @param orderStatus
     * @return
     */
    @Transactional
    @Override
    public OrderStatus create(OrderStatus orderStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        if (orderStatus == null) {
            throw new MissingRequiredFieldException("OrderStatus is null.");
        }
        if (StringUtils.isBlank(orderStatus.getName()) ||
                StringUtils.isBlank(orderStatus.getCode()))
            throw new MissingRequiredFieldException("Name or code is blank.");

        // check uniqueness
        if (orderStatusDao.findByName(orderStatus.getName()) != null) {
            throw new NotUniqueException("OrderStatus with name " + orderStatus.getName() +
                    " already exists.");
        }
        if (orderStatusDao.findByCode(orderStatus.getCode()) != null) {
            throw new NotUniqueException("OrderStatus with code " + orderStatus.getName() +
                    " already exists.");
        }
        orderStatus.setActive(true);
        // pass all checks, create the object
        return orderStatusDao.create(orderStatus);
    }

    /**
     * Get OrderStatus by id.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Override
    public OrderStatus get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        if (orderStatusDao.get(id) == null) {
            throw new NoSuchEntityException("OrderStatus with id " + id +
                    " does not exist.");
        }
        return orderStatusDao.get(id);
    }

    /**
     * Update an existing OrderStatus.
     * Must check unique fields if are unique.
     *
     * @param orderStatus
     * @return
     * @throws com.lambo.smartpay.core.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.core.exception.NotUniqueException
     */
    @Transactional
    @Override
    public OrderStatus update(OrderStatus orderStatus) throws MissingRequiredFieldException,
            NotUniqueException {
        // checking missing fields
        if (orderStatus == null) {
            throw new MissingRequiredFieldException("OrderStatus is null.");
        }
        if (orderStatus.getId() == null) {
            throw new MissingRequiredFieldException("OrderStatus id is null.");
        }
        if (StringUtils.isBlank(orderStatus.getName()) ||
                StringUtils.isBlank(orderStatus.getCode())) {
            throw new MissingRequiredFieldException("OrderStatus name or code is blank.");
        }
        // checking uniqueness
        OrderStatus namedOrderStatus = orderStatusDao.findByName(orderStatus.getName());
        if (namedOrderStatus != null &&
                !namedOrderStatus.getId().equals(orderStatus.getId())) {
            throw new NotUniqueException("OrderStatus with name " + orderStatus.getName() +
                    " already exists.");
        }
        OrderStatus codedOrderStatus = orderStatusDao.findByCode(orderStatus.getCode());
        if (codedOrderStatus != null &&
                !codedOrderStatus.getId().equals(orderStatus.getId())) {
            throw new NotUniqueException("OrderStatus with code " + orderStatus.getCode() +
                    " already exists.");
        }
        return orderStatusDao.update(orderStatus);
    }

    /**
     * Delete an existing OrderStatus.
     *
     * @param id
     * @return
     * @throws com.lambo.smartpay.core.exception.NoSuchEntityException
     */
    @Transactional
    @Override
    public OrderStatus delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        OrderStatus orderStatus = orderStatusDao.get(id);
        if (orderStatus == null) {
            throw new NoSuchEntityException("OrderStatus with id " + id +
                    " does not exist.");
        }
        orderStatusDao.delete(id);
        return orderStatus;
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusDao.getAll();
    }

    @Override
    public Long countAll() {
        return orderStatusDao.countAll();
    }
}
