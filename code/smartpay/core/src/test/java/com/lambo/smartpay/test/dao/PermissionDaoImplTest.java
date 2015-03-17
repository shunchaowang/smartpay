package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.PermissionDao;
import com.lambo.smartpay.persistence.entity.Permission;
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
public class PermissionDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionDaoImplTest.class);

    @Autowired
    PermissionDao permissionDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new Permission");
        Permission permission = new Permission();
        permission.setName("name");
        permission.setDescription("Description");
        permission.setCode("001");
        permission.setActive(true);

        permission = permissionDao.create(permission);

        assertNotNull(permission);

        permission = permissionDao.get(permission.getId());
        assertNotNull(permission);

        permission.setName("updated name");
        permission = permissionDao.update(permission);
        assertNotNull(permission);
        assertEquals("updated name", permission.getName());

        permissionDao.delete(permission.getId());
        assertNull(permissionDao.get(permission.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all Permission.");
        List<Permission> permissions = permissionDao.getAll();
        assertNotNull(permissions);
    }
}
