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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            ShipmentStatus shipmentStatus = new ShipmentStatus();
            shipmentStatus.setName("ad hoc " + i);
            shipmentStatus.setCode("00" + i);
            shipmentStatus.setActive(true);
            shipmentStatusDao.create(shipmentStatus);
        }

        // create one with active is false
        ShipmentStatus shipmentStatus = new ShipmentStatus();
        shipmentStatus.setName("ad hoc " + 3);
        shipmentStatus.setCode("00" + 3);
        shipmentStatus.setActive(false);
        shipmentStatusDao.create(shipmentStatus);

        Long countByAdHoc = shipmentStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = shipmentStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = shipmentStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = shipmentStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = shipmentStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            ShipmentStatus shipmentStatus = new ShipmentStatus();
            shipmentStatus.setName("ad hoc " + i);
            shipmentStatus.setCode("00" + i);
            shipmentStatus.setActive(true);
            shipmentStatusDao.create(shipmentStatus);
        }

        // create one with active is false
        ShipmentStatus shipmentStatus = new ShipmentStatus();
        shipmentStatus.setName("ad hoc " + 3);
        shipmentStatus.setCode("00" + 3);
        shipmentStatus.setActive(false);
        shipmentStatusDao.create(shipmentStatus);

        // testing order asc
        List<ShipmentStatus> statuses =
                shipmentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        ShipmentStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<ShipmentStatus> activeStatuses =
                shipmentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<ShipmentStatus> archivedStatuses =
                shipmentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = shipmentStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = shipmentStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
