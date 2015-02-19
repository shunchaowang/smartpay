package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.dao.impl.GenericDaoImpl;
import com.lambo.smartpay.model.MerchantStatus;
import com.lambo.smartpay.service.MerchantStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by swang on 2/19/2015.
 */
@Service("merchantStatusService")
public class MerchantStatusServiceImpl extends GenericDaoImpl<MerchantStatus, Long> implements MerchantStatusService {

    @Autowired
    private MerchantStatusDao merchantStatusDao;

    @Override
    public MerchantStatus get(String name) {
        if (StringUtils.isNotBlank(name)) {
            return merchantStatusDao.findByName(name);
        }
        return null;
    }

    @Override
    public Boolean delete(MerchantStatus merchantStatus) {
        if (merchantStatus == null) {
            return false;
        }

        merchantStatus = merchantStatusDao.findByName(merchantStatus.getName());

        if (merchantStatus == null) {
            return false;
        }

        super.delete(merchantStatus.getId());

        return true;
    }
}
