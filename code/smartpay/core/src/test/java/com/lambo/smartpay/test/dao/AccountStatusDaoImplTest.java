package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.AccountStatusDao;
import com.lambo.smartpay.persistence.entity.AccountStatus;
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            AccountStatus accountStatus = new AccountStatus();
            accountStatus.setName("ad hoc " + i);
            accountStatus.setCode("00" + i);
            accountStatus.setActive(true);
            accountStatusDao.create(accountStatus);
        }

        // create one with active is false
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setName("ad hoc " + 3);
        accountStatus.setCode("00" + 3);
        accountStatus.setActive(false);
        accountStatusDao.create(accountStatus);

        Long countByAdHoc = accountStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = accountStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = accountStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = accountStatusDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = accountStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            AccountStatus accountStatus = new AccountStatus();
            accountStatus.setName("ad hoc " + i);
            accountStatus.setCode("00" + i);
            accountStatus.setActive(true);
            accountStatusDao.create(accountStatus);
        }

        // create one with active is false
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setName("ad hoc " + 3);
        accountStatus.setCode("00" + 3);
        accountStatus.setActive(false);
        accountStatusDao.create(accountStatus);

        // testing order asc
        List<AccountStatus> statuses =
                accountStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        AccountStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<AccountStatus> activeStatuses =
                accountStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<AccountStatus> archivedStatuses =
                accountStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = accountStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = accountStatusDao.findByAdHocSearch("X", 0, 10, "id", ResourceProperties
                        .JpaOrderDir
                        .ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
