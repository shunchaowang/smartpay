package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.UserStatusDao;
import com.lambo.smartpay.persistence.entity.UserStatus;
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
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class UserStatusDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserStatusDaoImplTest.class);

    @Autowired
    UserStatusDao userStatusDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new UserStatus");
        UserStatus userStatus = new UserStatus();
        userStatus.setName("name");
        userStatus.setDescription("Description");
        userStatus.setCode("001");
        userStatus.setActive(true);

        userStatus = userStatusDao.create(userStatus);

        assertNotNull(userStatus);

        userStatus = userStatusDao.get(userStatus.getId());
        assertNotNull(userStatus);

        userStatus.setName("updated name");
        userStatus = userStatusDao.update(userStatus);
        assertNotNull(userStatus);
        assertEquals("updated name", userStatus.getName());

        userStatusDao.delete(userStatus.getId());
        assertNull(userStatusDao.get(userStatus.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all UserStatus.");
        List<UserStatus> userStatuses = userStatusDao.getAll();
        assertNotNull(userStatuses);
    }
}
