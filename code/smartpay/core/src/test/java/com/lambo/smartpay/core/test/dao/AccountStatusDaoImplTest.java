package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.AccountStatusDao;
import com.lambo.smartpay.core.persistence.entity.AccountStatus;
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
 * Integration test for AccountStatusDaoImpl.
 * Created by swang on 2/25/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class AccountStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccountStatusDaoImplTest.class);

    @Autowired
    AccountStatusDao accountStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new AccountStatus");
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setName("name");
        accountStatus.setDescription("Description");
        accountStatus.setCode("001");
        accountStatus.setActive(true);

        accountStatus = accountStatusDao.create(accountStatus);

        assertNotNull(accountStatus);

        accountStatus = accountStatusDao.get(accountStatus.getId());
        assertNotNull(accountStatus);

        accountStatus.setName("updated name");
        accountStatus = accountStatusDao.update(accountStatus);
        assertNotNull(accountStatus);
        assertEquals("updated name", accountStatus.getName());

        accountStatusDao.delete(accountStatus.getId());
        assertNull(accountStatusDao.get(accountStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all AccountStatus.");
        List<AccountStatus> accountStatuses = accountStatusDao.getAll();
        assertNotNull(accountStatuses);
    }
}
