package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.entity.FeeType;
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
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class FeeTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(FeeTypeDaoImplTest.class);

    @Autowired
    FeeTypeDao feeTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new FeeType");
        FeeType feeType = new FeeType();
        feeType.setName("name");
        feeType.setDescription("Description");
        feeType.setCode("001");
        feeType.setActive(true);

        feeType = feeTypeDao.create(feeType);

        assertNotNull(feeType);

        feeType = feeTypeDao.get(feeType.getId());
        assertNotNull(feeType);

        feeType.setName("updated name");
        feeType = feeTypeDao.update(feeType);
        assertNotNull(feeType);
        assertEquals("updated name", feeType.getName());

        feeTypeDao.delete(feeType.getId());
        assertNull(feeTypeDao.get(feeType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all FeeType.");
        List<FeeType> feeTypes = feeTypeDao.getAll();
        assertNotNull(feeTypes);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            FeeType feeType = new FeeType();
            feeType.setName("ad hoc " + i);
            feeType.setCode("00" + i);
            feeType.setActive(true);
            feeTypeDao.create(feeType);
        }

        // create one with active is false
        FeeType feeType = new FeeType();
        feeType.setName("ad hoc " + 3);
        feeType.setCode("00" + 3);
        feeType.setActive(false);
        feeTypeDao.create(feeType);

        Long countByAdHoc = feeTypeDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = feeTypeDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = feeTypeDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = feeTypeDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = feeTypeDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            FeeType feeType = new FeeType();
            feeType.setName("ad hoc " + i);
            feeType.setCode("00" + i);
            feeType.setActive(true);
            feeTypeDao.create(feeType);
        }

        // create one with active is false
        FeeType feeType = new FeeType();
        feeType.setName("ad hoc " + 3);
        feeType.setCode("00" + 3);
        feeType.setActive(false);
        feeTypeDao.create(feeType);

        // testing order asc
        List<FeeType> types =
                feeTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        null);
        assertEquals(4, types.size());

        FeeType type = types.get(0);
        assertNotNull(type);
        assertEquals("000", type.getCode());

        List<FeeType> activeTypes =
                feeTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<FeeType> archivedTypes =
                feeTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        types = feeTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, types.size());

        type = types.get(0);
        assertNotNull(type);
        assertEquals("003", type.getCode());

        types = feeTypeDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(types);
        assertEquals(0, types.size());
    }
}
