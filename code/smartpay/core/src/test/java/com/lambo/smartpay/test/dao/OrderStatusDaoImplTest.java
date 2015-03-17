package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.OrderStatusDao;
import com.lambo.smartpay.persistence.entity.OrderStatus;
import com.lambo.smartpay.util.ResourceProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class OrderStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusDaoImplTest.class);

    @Autowired
    OrderStatusDao orderStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new OrderStatus");
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName("name");
        orderStatus.setDescription("Description");
        orderStatus.setCode("001");
        orderStatus.setActive(true);

        orderStatus = orderStatusDao.create(orderStatus);

        assertNotNull(orderStatus);

        orderStatus = orderStatusDao.get(orderStatus.getId());
        assertNotNull(orderStatus);

        orderStatus.setName("updated name");
        orderStatus = orderStatusDao.update(orderStatus);
        assertNotNull(orderStatus);
        assertEquals("updated name", orderStatus.getName());

        orderStatusDao.delete(orderStatus.getId());
        assertNull(orderStatusDao.get(orderStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all OrderStatus.");
        List<OrderStatus> orderStatuses = orderStatusDao.getAll();
        assertNotNull(orderStatuses);
    }
}
