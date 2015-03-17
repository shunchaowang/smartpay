package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.CustomerLoginDao;
import com.lambo.smartpay.persistence.entity.CustomerLogin;
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
public class CustomerLoginDaoImplTest {

    @Autowired
    private CustomerLoginDao customerLoginDao;

    @Test
    public void testQueryByCriteria() {
        CustomerLogin customerLogin = new CustomerLogin();
        customerLogin.setId((long) 3);
        customerLogin.setLastName("zzz");
        customerLogin.setActive(true);
        customerLogin.setFirstName("zzz");
        customerLogin.setLoginEmail("zzz@me.com");
        Long count = customerLoginDao.countByCriteria(customerLogin, null);
        assertEquals(new Long(0), count);
        List<CustomerLogin> customerLogins = customerLoginDao.
                findByCriteria(customerLogin, null, null, null, null, null);
        assertEquals((long) 0, customerLogins.size());
    }
}
