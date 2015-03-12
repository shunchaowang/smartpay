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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            UserStatus userStatus = new UserStatus();
            userStatus.setName("ad hoc " + i);
            userStatus.setCode("00" + i);
            userStatus.setActive(true);
            userStatusDao.create(userStatus);
        }

        // create one with active is false
        UserStatus userStatus = new UserStatus();
        userStatus.setName("ad hoc " + 3);
        userStatus.setCode("00" + 3);
        userStatus.setActive(false);
        userStatusDao.create(userStatus);

        Long countByAdHoc = userStatusDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = userStatusDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = userStatusDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = userStatusDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = userStatusDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            UserStatus userStatus = new UserStatus();
            userStatus.setName("ad hoc " + i);
            userStatus.setCode("00" + i);
            userStatus.setActive(true);
            userStatusDao.create(userStatus);
        }

        // create one with active is false
        UserStatus userStatus = new UserStatus();
        userStatus.setName("ad hoc " + 3);
        userStatus.setCode("00" + 3);
        userStatus.setActive(false);
        userStatusDao.create(userStatus);

        // testing order asc
        List<UserStatus> statuses =
                userStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, statuses.size());

        UserStatus status = statuses.get(0);
        assertNotNull(status);
        assertEquals("000", status.getCode());

        List<UserStatus> activeStatuses =
                userStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeStatuses.size());

        List<UserStatus> archivedStatuses =
                userStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedStatuses.size());


        // testing order desc
        statuses = userStatusDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, statuses.size());

        status = statuses.get(0);
        assertNotNull(status);
        assertEquals("003", status.getCode());

        statuses = userStatusDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceProperties
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(statuses);
        assertEquals(0, statuses.size());
    }
}
