package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.PermissionDao;
import com.lambo.smartpay.model.Permission;
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
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
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

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Permission permission = new Permission();
            permission.setName("ad hoc " + i);
            permission.setCode("00" + i);
            permission.setActive(true);
            permissionDao.create(permission);
        }

        // create one with active is false
        Permission permission = new Permission();
        permission.setName("ad hoc " + 3);
        permission.setCode("00" + 3);
        permission.setActive(false);
        permissionDao.create(permission);

        Long countByAdHoc = permissionDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = permissionDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = permissionDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = permissionDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = permissionDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Permission permission = new Permission();
            permission.setName("ad hoc " + i);
            permission.setCode("00" + i);
            permission.setActive(true);
            permissionDao.create(permission);
        }

        // create one with active is false
        Permission permission = new Permission();
        permission.setName("ad hoc " + 3);
        permission.setCode("00" + 3);
        permission.setActive(false);
        permissionDao.create(permission);

        // testing order asc
        List<Permission> permissions =
                permissionDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, permissions.size());

        permission = permissions.get(0);
        assertNotNull(permission);
        assertEquals("000", permission.getCode());

        List<Permission> activeTypes =
                permissionDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<Permission> archivedTypes =
                permissionDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        permissions = permissionDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, permissions.size());

        permission = permissions.get(0);
        assertNotNull(permission);
        assertEquals("003", permission.getCode());

        permissions = permissionDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir
                        .ASC,
                null);
        assertNotNull(permissions);
        assertEquals(0, permissions.size());
    }
}
