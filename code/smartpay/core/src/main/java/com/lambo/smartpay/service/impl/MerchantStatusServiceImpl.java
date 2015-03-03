package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.persistence.dao.MerchantStatusDao;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
import com.lambo.smartpay.service.MerchantStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by swang on 2/19/2015.
 */
@Service("merchantStatusService")
public class MerchantStatusServiceImpl implements MerchantStatusService {

    @Autowired
    private MerchantStatusDao merchantStatusDao;

    @Override
    public MerchantStatus create(MerchantStatus merchantStatus) {
        return null;
    }

    @Override
    public MerchantStatus update(MerchantStatus merchantStatus) {
        return null;
    }

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

        merchantStatusDao.delete(merchantStatus.getId());

        return true;
    }
}
