package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerLoginStatus;
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
 * Integration test for CustomerLoginStatusDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CustomerLoginStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerLoginStatusDaoImplTest.class);

    @Autowired
    CustomerLoginStatusDao customerLoginStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new CustomerLoginStatus");
        CustomerLoginStatus customerLoginStatus = new CustomerLoginStatus();
        customerLoginStatus.setName("name");
        customerLoginStatus.setDescription("Description");
        customerLoginStatus.setCode("001");
        customerLoginStatus.setActive(true);

        customerLoginStatus = customerLoginStatusDao.create(customerLoginStatus);

        assertNotNull(customerLoginStatus);

        customerLoginStatus = customerLoginStatusDao.get(customerLoginStatus.getId());
        assertNotNull(customerLoginStatus);

        customerLoginStatus.setName("updated name");
        customerLoginStatus = customerLoginStatusDao.update(customerLoginStatus);
        assertNotNull(customerLoginStatus);
        assertEquals("updated name", customerLoginStatus.getName());

        customerLoginStatusDao.delete(customerLoginStatus.getId());
        assertNull(customerLoginStatusDao.get(customerLoginStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all CustomerLoginStatus.");
        List<CustomerLoginStatus> customerLoginStatuses = customerLoginStatusDao.getAll();
        assertNotNull(customerLoginStatuses);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CustomerLoginStatus customerLoginStatus = new CustomerLoginStatus();
            customerLoginStatus.setName("ad hoc " + i);
            customerLoginStatus.setCode("00" + i);
            customerLoginStatus.setActive(true);
            customerLoginStatusDao.create(customerLoginStatus);
        }

        // create one with active is false
        CustomerLoginStatus customerLoginStatus = new CustomerLoginStatus();
        customerLoginStatus.setName("ad hoc " + 3);
        customerLoginStatus.setCode("00" + 3);
        customerLoginStatus.setActive(false);
        customerLoginStatusDao.create(customerLoginStatus);

        Long countByAdHoc = customerLoginStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = customerLoginStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = customerLoginStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = customerLoginStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = customerLoginStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CustomerLoginStatus customerLoginStatus = new CustomerLoginStatus();
            customerLoginStatus.setName("ad hoc " + i);
            customerLoginStatus.setCode("00" + i);
            customerLoginStatus.setActive(true);
            customerLoginStatusDao.create(customerLoginStatus);
        }

        // create one with active is false
        CustomerLoginStatus customerLoginStatus = new CustomerLoginStatus();
        customerLoginStatus.setName("ad hoc " + 3);
        customerLoginStatus.setCode("00" + 3);
        customerLoginStatus.setActive(false);
        customerLoginStatusDao.create(customerLoginStatus);

        // testing order asc
        List<CustomerLoginStatus> statuses =
                customerLoginStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        CustomerLoginStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<CustomerLoginStatus> activeStatuses =
                customerLoginStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<CustomerLoginStatus> archivedStatuses =
                customerLoginStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = customerLoginStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = customerLoginStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
