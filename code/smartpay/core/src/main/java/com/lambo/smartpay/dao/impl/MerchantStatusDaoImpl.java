package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.MerchantStatusDao;
import com.lambo.smartpay.model.MerchantStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by swang on 2/17/2015.
 */
@Repository("merchantStatusDao")
public class MerchantStatusDaoImpl extends GenericDaoImpl<MerchantStatus, Long>
        implements MerchantStatusDao {

    @Override
    public MerchantStatus findByName(String name) {

        Query query = this.entityManager.createQuery("SELECT m FROM MerchantStatus m WHERE m.name = :name");
        query.setParameter("name", name);

        List<MerchantStatus> merchantStatuses = query.getResultList();
        if (merchantStatuses != null && merchantStatuses.size() == 1) {
            return merchantStatuses.get(0);
        }

        return null;
    }

    @Override
    public List<MerchantStatus> getAll() {

        Query query = this.entityManager.createQuery("SELECT m FROM MerchantStatus m");
        List<MerchantStatus> merchantStatuses = query.getResultList();

        if (merchantStatuses != null) {
            return merchantStatuses;
        }

        return null;
    }
}
