package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.CustomerDao;
import com.lambo.smartpay.core.persistence.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by swang on 3/7/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class CustomerDaoImplTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testAdvanceSearch() {
        Customer customer = new Customer();
        customer.setLastName("xyz");
        customer.setFirstName("xyz");
        customer.setEmail("aaa@me.com");
        customer.setActive(true);
        customer.setId((long) 3);
        Long count = customerDao.countByCriteria(customer, null);
        assertEquals(new Long(0), count);
        List<Customer> customers = customerDao
                .findByCriteria(customer, null, null, null, null, null);
        assertEquals((long) 0, customers.size());
    }
}
