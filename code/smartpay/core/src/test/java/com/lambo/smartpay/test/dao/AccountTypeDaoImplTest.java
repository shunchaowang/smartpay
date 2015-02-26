package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.AccountTypeDao;
import com.lambo.smartpay.model.AccountType;
import com.lambo.smartpay.util.ResourceUtil;
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
@ContextConfiguration(classes = {AppConfig.class})
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            AccountType accountType = new AccountType();
            accountType.setName("ad hoc " + i);
            accountType.setCode("00" + i);
            accountType.setActive(true);
            accountTypeDao.create(accountType);
        }

        // create one with active is false
        AccountType accountType = new AccountType();
        accountType.setName("ad hoc " + 3);
        accountType.setCode("00" + 3);
        accountType.setActive(false);
        accountTypeDao.create(accountType);

        Long countByAdHoc = accountTypeDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = accountTypeDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = accountTypeDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = accountTypeDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = accountTypeDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            AccountType accountType = new AccountType();
            accountType.setName("ad hoc " + i);
            accountType.setCode("00" + i);
            accountType.setActive(true);
            accountTypeDao.create(accountType);
        }

        // create one with active is false
        AccountType accountType = new AccountType();
        accountType.setName("ad hoc " + 3);
        accountType.setCode("00" + 3);
        accountType.setActive(false);
        accountTypeDao.create(accountType);

        // testing order asc
        List<AccountType> statuses =
                accountTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        AccountType status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<AccountType> activeStatuses =
                accountTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<AccountType> archivedStatuses =
                accountTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = accountTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = accountTypeDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
