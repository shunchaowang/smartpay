package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.RoleDao;
import com.lambo.smartpay.persistence.entity.Role;
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
public class RoleDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(RoleDaoImplTest.class);

    @Autowired
    RoleDao roleDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new Role");
        Role role = new Role();
        role.setName("name");
        role.setDescription("Description");
        role.setCode("001");
        role.setActive(true);

        role = roleDao.create(role);

        assertNotNull(role);

        role = roleDao.get(role.getId());
        assertNotNull(role);

        role.setName("updated name");
        role = roleDao.update(role);
        assertNotNull(role);
        assertEquals("updated name", role.getName());

        roleDao.delete(role.getId());
        assertNull(roleDao.get(role.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all Role.");
        List<Role> roles = roleDao.getAll();
        assertNotNull(roles);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Role role = new Role();
            role.setName("ad hoc " + i);
            role.setCode("00" + i);
            role.setActive(true);
            roleDao.create(role);
        }

        // create one with active is false
        Role role = new Role();
        role.setName("ad hoc " + 3);
        role.setCode("00" + 3);
        role.setActive(false);
        roleDao.create(role);

        Long countByAdHoc = roleDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = roleDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = roleDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = roleDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = roleDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Role role = new Role();
            role.setName("ad hoc " + i);
            role.setCode("00" + i);
            role.setActive(true);
            roleDao.create(role);
        }

        // create one with active is false
        Role role = new Role();
        role.setName("ad hoc " + 3);
        role.setCode("00" + 3);
        role.setActive(false);
        roleDao.create(role);

        // testing order asc
        List<Role> roles =
                roleDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties.JpaOrderDir.ASC,
                        null);
        assertEquals(4, roles.size());

        role = roles.get(0);
        assertNotNull(role);
        assertEquals("000", role.getCode());

        List<Role> activeTypes =
                roleDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<Role> archivedTypes =
                roleDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceProperties.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        roles = roleDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceProperties.JpaOrderDir.DESC, null);
        assertEquals(4, roles.size());

        role = roles.get(0);
        assertNotNull(role);
        assertEquals("003", role.getCode());

        roles = roleDao.findByAdHocSearch("X", 0, 10, "id", ResourceProperties.JpaOrderDir.ASC,
                null);
        assertNotNull(roles);
        assertEquals(0, roles.size());
    }
}
