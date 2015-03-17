package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CustomerLoginStatusDao;
import com.lambo.smartpay.persistence.entity.CustomerLoginStatus;
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
}
