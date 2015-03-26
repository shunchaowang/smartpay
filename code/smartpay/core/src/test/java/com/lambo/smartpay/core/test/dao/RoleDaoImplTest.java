package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.RoleDao;
import com.lambo.smartpay.core.persistence.entity.Role;
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
}
