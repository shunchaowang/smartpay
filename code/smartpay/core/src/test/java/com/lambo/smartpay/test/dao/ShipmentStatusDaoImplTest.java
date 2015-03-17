package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.ShipmentStatusDao;
import com.lambo.smartpay.persistence.entity.ShipmentStatus;
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
public class ShipmentStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentStatusDaoImplTest.class);

    @Autowired
    ShipmentStatusDao shipmentStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new ShipmentStatus");
        ShipmentStatus shipmentStatus = new ShipmentStatus();
        shipmentStatus.setName("name");
        shipmentStatus.setDescription("Description");
        shipmentStatus.setCode("001");
        shipmentStatus.setActive(true);

        shipmentStatus = shipmentStatusDao.create(shipmentStatus);

        assertNotNull(shipmentStatus);

        shipmentStatus = shipmentStatusDao.get(shipmentStatus.getId());
        assertNotNull(shipmentStatus);

        shipmentStatus.setName("updated name");
        shipmentStatus = shipmentStatusDao.update(shipmentStatus);
        assertNotNull(shipmentStatus);
        assertEquals("updated name", shipmentStatus.getName());

        shipmentStatusDao.delete(shipmentStatus.getId());
        assertNull(shipmentStatusDao.get(shipmentStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all ShipmentStatus.");
        List<ShipmentStatus> shipmentStatuses = shipmentStatusDao.getAll();
        assertNotNull(shipmentStatuses);
    }
}
