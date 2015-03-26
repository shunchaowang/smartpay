package com.lambo.smartpay.core.test.dao;

import com.lambo.smartpay.core.config.PersistenceConfigDev;
import com.lambo.smartpay.core.persistence.dao.FeeDao;
import com.lambo.smartpay.core.persistence.dao.FeeTypeDao;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by swang on 3/2/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfigDev.class})
@ActiveProfiles("dev")
public class FeeDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(FeeDaoImplTest.class);

    private String staticFeeTypeCode = "100";

    @Autowired
    private FeeDao feeDao;
    @Autowired
    private FeeTypeDao feeTypeDao;

    @Test
    @Transactional
    public void testCrud() {
        LOG.info("Testing crud of Fee.");
        FeeType staticFeeType = feeTypeDao.findByCode(staticFeeTypeCode);
        Date today = Calendar.getInstance().getTime();

        Fee fee = new Fee();
        fee.setValue(new Float(0.5));
        fee.setCreatedTime(today);
        fee.setActive(true);
        fee.setFeeType(staticFeeType);

        fee = feeDao.create(fee);
        assertNotNull(fee);

        fee = feeDao.get(fee.getId());
        assertNotNull(fee);

        fee.setValue(new Float(1.0));
        fee = feeDao.update(fee);
        assertNotNull(fee);
        assertEquals(new Float(1.0), fee.getValue());

        feeDao.delete(fee.getId());

        assertNull(feeDao.get(fee.getId()));
    }
}
