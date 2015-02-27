package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.OrderStatusDao;
import com.lambo.smartpay.model.OrderStatus;
import com.lambo.smartpay.util.ResourceUtil;
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
@ContextConfiguration(classes = {AppConfig.class})
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setName("ad hoc " + i);
            orderStatus.setCode("00" + i);
            orderStatus.setActive(true);
            orderStatusDao.create(orderStatus);
        }

        // create one with active is false
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName("ad hoc " + 3);
        orderStatus.setCode("00" + 3);
        orderStatus.setActive(false);
        orderStatusDao.create(orderStatus);

        Long countByAdHoc = orderStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = orderStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = orderStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = orderStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = orderStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setName("ad hoc " + i);
            orderStatus.setCode("00" + i);
            orderStatus.setActive(true);
            orderStatusDao.create(orderStatus);
        }

        // create one with active is false
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName("ad hoc " + 3);
        orderStatus.setCode("00" + 3);
        orderStatus.setActive(false);
        orderStatusDao.create(orderStatus);

        // testing order asc
        List<OrderStatus> statuses =
                orderStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        OrderStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<OrderStatus> activeStatuses =
                orderStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<OrderStatus> archivedStatuses =
                orderStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = orderStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = orderStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
