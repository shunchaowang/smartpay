package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.CredentialTypeDao;
import com.lambo.smartpay.model.CredentialType;
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
 * Integration test for CredentialTypeDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("dev")
public class CredentialTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialTypeDaoImplTest.class);

    @Autowired
    CredentialTypeDao credentialTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new CredentialType");
        CredentialType credentialType = new CredentialType();
        credentialType.setName("name");
        credentialType.setDescription("Description");
        credentialType.setCode("001");
        credentialType.setActive(true);

        credentialType = credentialTypeDao.create(credentialType);

        assertNotNull(credentialType);

        credentialType = credentialTypeDao.get(credentialType.getId());
        assertNotNull(credentialType);

        credentialType.setName("updated name");
        credentialType = credentialTypeDao.update(credentialType);
        assertNotNull(credentialType);
        assertEquals("updated name", credentialType.getName());

        credentialTypeDao.delete(credentialType.getId());
        assertNull(credentialTypeDao.get(credentialType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all CredentialType.");
        List<CredentialType> credentialTypes = credentialTypeDao.getAll();
        assertNotNull(credentialTypes);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CredentialType credentialType = new CredentialType();
            credentialType.setName("ad hoc " + i);
            credentialType.setCode("00" + i);
            credentialType.setActive(true);
            credentialTypeDao.create(credentialType);
        }

        // create one with active is false
        CredentialType credentialType = new CredentialType();
        credentialType.setName("ad hoc " + 3);
        credentialType.setCode("00" + 3);
        credentialType.setActive(false);
        credentialTypeDao.create(credentialType);

        Long countByAdHoc = credentialTypeDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = credentialTypeDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = credentialTypeDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = credentialTypeDao.countByAdHocSearch("XYZ", null);
        assertEquals(new Long(0), countByX);

        Long countById = credentialTypeDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            CredentialType credentialType = new CredentialType();
            credentialType.setName("ad hoc " + i);
            credentialType.setCode("00" + i);
            credentialType.setActive(true);
            credentialTypeDao.create(credentialType);
        }

        // create one with active is false
        CredentialType credentialType = new CredentialType();
        credentialType.setName("ad hoc " + 3);
        credentialType.setCode("00" + 3);
        credentialType.setActive(false);
        credentialTypeDao.create(credentialType);

        // testing order asc
        List<CredentialType> types =
                credentialTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, types.size());

        CredentialType type = types.get(0);
        assertNotNull(type);
        assertEquals("000", type.getCode());

        List<CredentialType> activeTypes =
                credentialTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<CredentialType> archivedTypes =
                credentialTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        types = credentialTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, types.size());

        type = types.get(0);
        assertNotNull(type);
        assertEquals("003", type.getCode());

        types = credentialTypeDao.findByAdHocSearch("XYZ", 0, 10, "id", ResourceUtil.JpaOrderDir
                        .ASC,
                null);
        assertNotNull(types);
        assertEquals(0, types.size());
    }
}
