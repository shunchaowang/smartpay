package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CustomerStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerStatus;
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
 * Integration test for CustomerStatusDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CustomerStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerStatusDaoImplTest.class);

    @Autowired
    CustomerStatusDao customerStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new CustomerStatus");
        CustomerStatus customerStatus = new CustomerStatus();
        customerStatus.setName("name");
        customerStatus.setDescription("Description");
        customerStatus.setCode("001");
        customerStatus.setActive(true);

        customerStatus = customerStatusDao.create(customerStatus);

        assertNotNull(customerStatus);

        customerStatus = customerStatusDao.get(customerStatus.getId());
        assertNotNull(customerStatus);

        customerStatus.setName("updated name");
        customerStatus = customerStatusDao.update(customerStatus);
        assertNotNull(customerStatus);
        assertEquals("updated name", customerStatus.getName());

        customerStatusDao.delete(customerStatus.getId());
        assertNull(customerStatusDao.get(customerStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all CustomerStatus.");
        List<CustomerStatus> customerStatuses = customerStatusDao.getAll();
        assertNotNull(customerStatuses);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CustomerStatus customerStatus = new CustomerStatus();
            customerStatus.setName("ad hoc " + i);
            customerStatus.setCode("00" + i);
            customerStatus.setActive(true);
            customerStatusDao.create(customerStatus);
        }

        // create one with active is false
        CustomerStatus customerStatus = new CustomerStatus();
        customerStatus.setName("ad hoc " + 3);
        customerStatus.setCode("00" + 3);
        customerStatus.setActive(false);
        customerStatusDao.create(customerStatus);

        Long countByAdHoc = customerStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = customerStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = customerStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = customerStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = customerStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CustomerStatus customerStatus = new CustomerStatus();
            customerStatus.setName("ad hoc " + i);
            customerStatus.setCode("00" + i);
            customerStatus.setActive(true);
            customerStatusDao.create(customerStatus);
        }

        // create one with active is false
        CustomerStatus customerStatus = new CustomerStatus();
        customerStatus.setName("ad hoc " + 3);
        customerStatus.setCode("00" + 3);
        customerStatus.setActive(false);
        customerStatusDao.create(customerStatus);

        // testing order asc
        List<CustomerStatus> statuses =
                customerStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        CustomerStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<CustomerStatus> activeStatuses =
                customerStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<CustomerStatus> archivedStatuses =
                customerStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = customerStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = customerStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
