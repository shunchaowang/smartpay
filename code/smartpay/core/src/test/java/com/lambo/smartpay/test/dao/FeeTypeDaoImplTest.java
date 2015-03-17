package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.persistence.entity.FeeType;
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
}
