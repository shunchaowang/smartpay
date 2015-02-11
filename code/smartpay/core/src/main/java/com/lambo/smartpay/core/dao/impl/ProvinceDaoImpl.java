package com.lambo.smartpay.core.dao.impl;

import com.lambo.smartpay.core.dao.ProvinceDao;
import com.lambo.smartpay.core.model.Province;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author swang
 */
@Repository("provinceDao")
public class ProvinceDaoImpl extends GenericDaoImpl<Province, Integer> implements ProvinceDao {

    @SuppressWarnings("unchecked")
    @Override
    public Province findByName(String name) {
        Query query = entityManager.createQuery("SELECT p FROM Province p WHERE p.name= :name");
        query.setParameter("name", name);

        List<Province> provinces = query.getResultList();

        if (provinces != null && provinces.size() == 1) {
            return provinces.get(0);
        }
        return null;
    }

}
