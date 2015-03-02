package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.AdministratorDao;
import com.lambo.smartpay.dao.RoleDao;
import com.lambo.smartpay.model.Administrator;
import com.lambo.smartpay.model.Role;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Integration test for AdministratorDaoImpl.
 * Created by swang on 2/27/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("dev")
public class AdministratorDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(AdministratorDaoImplTest.class);

    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private RoleDao roleDao;

    private String adminRoleCode = "100";

    private Date todayDate = Calendar.getInstance().getTime();

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new Administrator");
        Administrator administrator = new Administrator();
        administrator.setUsername("name");
        administrator.setPassword("password");
        administrator.setFirstName("first name");
        administrator.setLastName("last name");
        administrator.setEmail("me@me.com");
        administrator.setActive(true);
        administrator.setCreatedTime(todayDate);

        Role adminRole = roleDao.findByCode(adminRoleCode);
        administrator.setRole(adminRole);

        administrator = administratorDao.create(administrator);

        assertNotNull(administrator);

        administrator = administratorDao.get(administrator.getId());
        assertNotNull(administrator);

        administrator.setUsername("updated name");
        administrator = administratorDao.update(administrator);
        assertNotNull(administrator);
        assertEquals("updated name", administrator.getUsername());

        administratorDao.delete(administrator.getId());
        assertNull(administratorDao.get(administrator.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all Administrator.");
        List<Administrator> administrators = administratorDao.getAll();
        assertNotNull(administrators);
    }

    @Test
    @Transactional
    public void testFindByUsernameOrEmail() {
        LOG.info("Testing equally find by username or email.");
        // create 3 object to count using name
        Role adminRole = roleDao.findByCode(adminRoleCode);
        Administrator administrator = new Administrator();
        administrator.setUsername("xyzadmin");
        administrator.setPassword("password");
        administrator.setFirstName("first name");
        administrator.setLastName("last name");
        administrator.setEmail("xyzadmin@me.com");
        administrator.setRole(adminRole);
        administrator.setActive(true);
        administrator.setCreatedTime(todayDate);
        administrator = administratorDao.create(administrator);

        Administrator adminByUsername = administratorDao.findByUsername("xyzadmin");
        assertNotNull(adminByUsername);
        assertEquals(administrator, adminByUsername);

        Administrator adminByEmail = administratorDao.findByEmail("xyzadmin@me.com");
        assertNotNull(adminByEmail);
        assertEquals(administrator, adminByEmail);

    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        Role adminRole = roleDao.findByCode(adminRoleCode);
        for (int i = 0; i < 3; i++) {
            Administrator administrator = new Administrator();
            administrator.setUsername("ad hoc " + i);
            administrator.setPassword("password " + i);
            administrator.setFirstName("first name " + i);
            administrator.setLastName("last name " + i);
            administrator.setEmail("me" + i + "@me.com");
            administrator.setRole(adminRole);
            administrator.setActive(true);
            administrator.setCreatedTime(todayDate);
            administratorDao.create(administrator);
        }

        // create one with active is false
        Administrator administrator = new Administrator();
        administrator.setUsername("ad hoc " + 3);
        administrator.setPassword("password " + 3);
        administrator.setFirstName("first name " + 3);
        administrator.setLastName("last name " + 3);
        administrator.setEmail("me" + 3 + "@me.com");
        administrator.setRole(adminRole);
        administrator.setActive(false);
        administrator.setCreatedTime(todayDate);
        administratorDao.create(administrator);

        Long countByAdHoc = administratorDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = administratorDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = administratorDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = administratorDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = administratorDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        Role adminRole = roleDao.findByCode(adminRoleCode);
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            Administrator administrator = new Administrator();
            administrator.setUsername("ad hoc " + i);
            administrator.setPassword("password " + i);
            administrator.setFirstName("first name " + i);
            administrator.setLastName("last name " + i);
            administrator.setEmail("me" + i + "@me.com");
            administrator.setRole(adminRole);
            administrator.setActive(true);
            administrator.setCreatedTime(todayDate);
            administratorDao.create(administrator);
        }

        // create one with active is false
        Administrator administrator = new Administrator();
        administrator.setUsername("ad hoc " + 3);
        administrator.setPassword("password " + 3);
        administrator.setFirstName("first name " + 3);
        administrator.setLastName("last name " + 3);
        administrator.setEmail("me" + 3 + "@me.com");
        administrator.setRole(adminRole);
        administrator.setActive(false);
        administrator.setCreatedTime(todayDate);
        administratorDao.create(administrator);

        // testing order asc
        List<Administrator> administrators =
                administratorDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, administrators.size());

        Administrator administrator1 = administrators.get(0);
        assertNotNull(administrator1);
        assertEquals("password 0", administrator1.getPassword());

        List<Administrator> activeAdministrators =
                administratorDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeAdministrators.size());

        List<Administrator> archivedAdministrators =
                administratorDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedAdministrators.size());


        // testing order desc
        administrators = administratorDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, administrators.size());

        administrator1 = administrators.get(0);
        assertNotNull(administrator1);
        assertEquals("password 3", administrator1.getPassword());

        administrators = administratorDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil
                        .JpaOrderDir.ASC,
                null);
        assertNotNull(administrators);
        assertEquals(0, administrators.size());
    }
}
