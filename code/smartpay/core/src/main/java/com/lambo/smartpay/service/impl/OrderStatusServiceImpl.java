package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.OrderStatusDao;
import com.lambo.smartpay.persistence.entity.OrderStatus;
import com.lambo.smartpay.service.OrderStatusService;
import com.lambo.smartpay.util.ResourceUtil;
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
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
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
     * @throws com.lambo.smartpay.exception.MissingRequiredFieldException
     * @throws com.lambo.smartpay.exception.NotUniqueException
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
     * @throws com.lambo.smartpay.exception.NoSuchEntityException
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
    public List<OrderStatus> findByAdHocSearch(String search, Integer start, Integer length,
                                               String order, ResourceUtil.JpaOrderDir orderDir,
                                               Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param orderStatus contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(OrderStatus orderStatus) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param orderStatus contains criteria if the field is not null or empty.
     * @param start
     * @param length      @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<OrderStatus> findByAdvanceSearch(OrderStatus orderStatus, Integer start, Integer
            length) {
        return null;
    }
}
