package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.AccountTypeDao;
import com.lambo.smartpay.persistence.entity.AccountType;
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
 * Integration test fo AccountTypeDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class AccountTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccountTypeDaoImplTest.class);

    @Autowired
    AccountTypeDao accountTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new AccountType");
        AccountType accountType = new AccountType();
        accountType.setName("name");
        accountType.setDescription("Description");
        accountType.setCode("001");
        accountType.setActive(true);

        accountType = accountTypeDao.create(accountType);

        assertNotNull(accountType);

        accountType = accountTypeDao.get(accountType.getId());
        assertNotNull(accountType);

        accountType.setName("updated name");
        accountType = accountTypeDao.update(accountType);
        assertNotNull(accountType);
        assertEquals("updated name", accountType.getName());

        accountTypeDao.delete(accountType.getId());
        assertNull(accountTypeDao.get(accountType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all AccountType.");
        List<AccountType> accountTypees = accountTypeDao.getAll();
        assertNotNull(accountTypees);
    }
}
