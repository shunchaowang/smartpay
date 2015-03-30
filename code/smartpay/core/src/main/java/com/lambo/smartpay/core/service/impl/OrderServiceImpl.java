package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.OrderDao;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/25/2015.
 */
@Service("orderService")
public class OrderServiceImpl extends GenericDateQueryServiceImpl<Order, Long>
        implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findByMerchantNumber(String merchantNumber) {
        if (StringUtils.isBlank(merchantNumber)) {
            logger.debug("Merchant number is blank.");
            return null;
        }
        return orderDao.findByMerchantNumber(merchantNumber);
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param order            contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     *                         it means no criteria on exact equals if t is null.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Order order, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return orderDao.countByCriteria(order, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param order            order by field, default is id.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     * @param start            first position of the result.
     * @param length           max record of the result.
     * @param orderField       order by field, default is id.
     * @param orderDir         order direction on the order field. default is DESC.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return
     */
    @Override
    public List<Order> findByCriteria(Order order, String search, Integer start, Integer length,
                                      String orderField, ResourceProperties.JpaOrderDir orderDir,
                                      Date createdTimeStart, Date createdTimeEnd) {
        return orderDao.findByCriteria(order, search, start, length, orderField, orderDir,
                createdTimeStart, createdTimeEnd);
    }

    @Transactional
    @Override
    public Order create(Order order) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (order == null) {
            throw new MissingRequiredFieldException("Order is null.");
        }
        if (StringUtils.isBlank(order.getMerchantNumber())) {
            throw new MissingRequiredFieldException("Merchant number is blank.");
        }
        if (order.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (StringUtils.isBlank(order.getGoodsName())) {
            throw new MissingRequiredFieldException("Goods name is blank.");
        }
        if (StringUtils.isBlank(order.getGoodsAmount())) {
            throw new MissingRequiredFieldException("Goods amount is blank.");
        }
        if (order.getSite() == null) {
            throw new MissingRequiredFieldException("Site is null.");
        }
        if (order.getOrderStatus() == null) {
            throw new MissingRequiredFieldException("Order status is null.");
        }
        if (order.getCurrency() == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }
        if (order.getCustomer() == null) {
            throw new MissingRequiredFieldException("Customer is null.");
        }

        order.setActive(true);
        order.setCreatedTime(date);

        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) throws NoSuchEntityException {

        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Order order = orderDao.get(id);
        if (order == null) {
            throw new NoSuchEntityException("Order is null.");
        }
        return order;
    }

    @Transactional
    @Override
    public Order update(Order order) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (order == null) {
            throw new MissingRequiredFieldException("Order is null.");
        }
        if (order.getId() == null) {
            throw new MissingRequiredFieldException("Order id is null.");
        }
        if (StringUtils.isBlank(order.getMerchantNumber())) {
            throw new MissingRequiredFieldException("Merchant number is blank.");
        }
        if (order.getAmount() == null) {
            throw new MissingRequiredFieldException("Amount is null.");
        }
        if (StringUtils.isBlank(order.getGoodsName())) {
            throw new MissingRequiredFieldException("Goods name is blank.");
        }
        if (StringUtils.isBlank(order.getGoodsAmount())) {
            throw new MissingRequiredFieldException("Goods amount is blank.");
        }
        if (order.getSite() == null) {
            throw new MissingRequiredFieldException("Site is null.");
        }
        if (order.getOrderStatus() == null) {
            throw new MissingRequiredFieldException("Order status is null.");
        }
        if (order.getCurrency() == null) {
            throw new MissingRequiredFieldException("Currency is null.");
        }
        if (order.getCustomer() == null) {
            throw new MissingRequiredFieldException("Customer is null.");
        }

        order.setActive(true);
        order.setUpdatedTime(date);

        return orderDao.update(order);
    }

    @Transactional
    @Override
    public Order delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Order order = orderDao.get(id);
        if (order == null) {
            throw new NoSuchEntityException("Order is null.");
        }
        orderDao.delete(id);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Long countAll() {
        return orderDao.countAll();
    }
}
