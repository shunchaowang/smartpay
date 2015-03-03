package com.lambo.smartpay.test.dao;

import com.lambo.smartpay.config.PersistenceConfigDev;
import com.lambo.smartpay.persistence.dao.PaymentTypeDao;
import com.lambo.smartpay.persistence.entity.PaymentType;
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
public class PaymentTypeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentTypeDaoImplTest.class);

    @Autowired
    PaymentTypeDao paymentTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing creating new PaymentType");
        PaymentType paymentType = new PaymentType();
        paymentType.setName("name");
        paymentType.setDescription("Description");
        paymentType.setCode("001");
        paymentType.setActive(true);

        paymentType = paymentTypeDao.create(paymentType);

        assertNotNull(paymentType);

        paymentType = paymentTypeDao.get(paymentType.getId());
        assertNotNull(paymentType);

        paymentType.setName("updated name");
        paymentType = paymentTypeDao.update(paymentType);
        assertNotNull(paymentType);
        assertEquals("updated name", paymentType.getName());

        paymentTypeDao.delete(paymentType.getId());
        assertNull(paymentTypeDao.get(paymentType.getId()));
    }

    @Test
    public void testGetAll() {

        LOG.info("Testing getting all PaymentType.");
        List<PaymentType> paymentTypes = paymentTypeDao.getAll();
        assertNotNull(paymentTypes);
    }

    @Test
    @Transactional
    public void testCountByAdHocSearch() {

        LOG.info("Testing counting by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            PaymentType paymentType = new PaymentType();
            paymentType.setName("ad hoc " + i);
            paymentType.setCode("00" + i);
            paymentType.setActive(true);
            paymentTypeDao.create(paymentType);
        }

        // create one with active is false
        PaymentType paymentType = new PaymentType();
        paymentType.setName("ad hoc " + 3);
        paymentType.setCode("00" + 3);
        paymentType.setActive(false);
        paymentTypeDao.create(paymentType);

        Long countByAdHoc = paymentTypeDao.countByAdHocSearch("ad hoc", null);
        assertEquals(new Long(4), countByAdHoc);

        Long countActiveByAdHoc = paymentTypeDao.countByAdHocSearch("ad hoc", true);
        assertEquals(new Long(3), countActiveByAdHoc);

        Long countArchiveByAdHoc = paymentTypeDao.countByAdHocSearch("ad hoc", false);
        assertEquals(new Long(1), countArchiveByAdHoc);

        Long countByX = paymentTypeDao.countByAdHocSearch("X", null);
        assertEquals(new Long(0), countByX);

        Long countById = paymentTypeDao.countByAdHocSearch("1", null);
        assertNotNull(countById);
    }

    @Test
    @Transactional
    public void testFindByAdHocSearch() {

        LOG.info("Testing finding by ad hoc.");
        // create 3 object to count using name
        for (int i = 0; i < 3; i++) {
            PaymentType paymentType = new PaymentType();
            paymentType.setName("ad hoc " + i);
            paymentType.setCode("00" + i);
            paymentType.setActive(true);
            paymentTypeDao.create(paymentType);
        }

        // create one with active is false
        PaymentType paymentType = new PaymentType();
        paymentType.setName("ad hoc " + 3);
        paymentType.setCode("00" + 3);
        paymentType.setActive(false);
        paymentTypeDao.create(paymentType);

        // testing order asc
        List<PaymentType> types =
                paymentTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir
                                .ASC,
                        null);
        assertEquals(4, types.size());

        PaymentType type = types.get(0);
        assertNotNull(type);
        assertEquals("000", type.getCode());

        List<PaymentType> activeTypes =
                paymentTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir
                                .ASC,
                        true);
        assertEquals(3, activeTypes.size());

        List<PaymentType> archivedTypes =
                paymentTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id", ResourceUtil.JpaOrderDir
                                .ASC,
                        false);
        assertEquals(1, archivedTypes.size());


        // testing order desc
        types = paymentTypeDao.findByAdHocSearch("ad hoc", 0, 10, "id",
                ResourceUtil.JpaOrderDir.DESC, null);
        assertEquals(4, types.size());

        type = types.get(0);
        assertNotNull(type);
        assertEquals("003", type.getCode());

        types = paymentTypeDao.findByAdHocSearch("X", 0, 10, "id", ResourceUtil.JpaOrderDir.ASC,
                null);
        assertNotNull(types);
        assertEquals(0, types.size());
    }
}
