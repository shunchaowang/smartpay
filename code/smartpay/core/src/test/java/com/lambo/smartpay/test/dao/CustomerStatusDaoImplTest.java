package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CustomerStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerStatus;
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
}
