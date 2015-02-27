package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.AppConfig;
import com.lambo.smartpay.dao.EncryptionTypeDao;
import com.lambo.smartpay.model.EncryptionType;
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
 * Integration test for EncryptionTypeDaoImpl.
 * Created by swang on 2/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("dev")
public class EncryptionTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionTypeDaoImplTest.class);

    @Autowired
    EncryptionTypeDao encryptionTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new EncryptionType");
        EncryptionType encryptionType = new EncryptionType();
        encryptionType.setName("name");
        encryptionType.setDescription("Description");
        encryptionType.setCode("001");
        encryptionType.setActive(true);

        encryptionType = encryptionTypeDao.create(encryptionType);

        assertNotNull(encryptionType);

        encryptionType = encryptionTypeDao.get(encryptionType.getId());
        assertNotNull(encryptionType);

        encryptionType.setName("updated name");
        encryptionType = encryptionTypeDao.update(encryptionType);
        assertNotNull(encryptionType);
        assertEquals("updated name", encryptionType.getName());

        encryptionTypeDao.delete(encryptionType.getId());
        assertNull(encryptionTypeDao.get(encryptionType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all EncryptionType.");
        List<EncryptionType> encryptionTypes = encryptionTypeDao.getAll();
        assertNotNull(encryptionTypes);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            EncryptionType encryptionType = new EncryptionType();
            encryptionType.setName("ad hoc " + i);
            encryptionType.setCode("00" + i);
            encryptionType.setActive(true);
            encryptionTypeDao.create(encryptionType);
        }

        // create one with active is false
        EncryptionType encryptionType = new EncryptionType();
        encryptionType.setName("ad hoc " + 3);
        encryptionType.setCode("00" + 3);
        encryptionType.setActive(false);
        encryptionTypeDao.create(encryptionType);

        Long countByAdHoc = encryptionTypeDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = encryptionTypeDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = encryptionTypeDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = encryptionTypeDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = encryptionTypeDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            EncryptionType encryptionType = new EncryptionType();
            encryptionType.setName("ad hoc " + i);
            encryptionType.setCode("00" + i);
            encryptionType.setActive(true);
            encryptionTypeDao.create(encryptionType);
        }

        // create one with active is false
        EncryptionType encryptionType = new EncryptionType();
        encryptionType.setName("ad hoc " + 3);
        encryptionType.setCode("00" + 3);
        encryptionType.setActive(false);
        encryptionTypeDao.create(encryptionType);

        // testing order asc
        List<EncryptionType> types =
                encryptionTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        null);
        assertEquals(4, types.size());

        EncryptionType type = types.get(0);
        assertNotNull(type);
        assertEquals("000", type.getCode());

        List<EncryptionType> activeTypes =
                encryptionTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<EncryptionType> archivedTypes =
                encryptionTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil
                                .JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        types = encryptionTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, types.size());

        type = types.get(0);
        assertNotNull(type);
        assertEquals("003", type.getCode());

        types = encryptionTypeDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(types);
        assertEquals(0, types.size());
    }
}
