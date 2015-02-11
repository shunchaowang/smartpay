package com.lambo.smartpay.core.dao.impl;

import com.lambo.smartpay.core.dao.CityDao;
import com.lambo.smartpay.core.model.City;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author swang
 */
@Repository("cityDao")
public class CityDaoImpl extends GenericDaoImpl<City, Integer> implements CityDao {

    /*
     * (non-Javadoc)
     *
     * @see com.lambo.smartpay.core.dao.CityDao#getCityByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public City findByName(String name) {
        Query query = this.entityManager.createQuery("SELECT c FROM City c WHERE c.name= :name");
        query.setParameter("name", name);
        List<City> cities = query.getResultList();

        if (cities != null && cities.size() == 1) {
            return cities.get(0);
        }
        return null;
    }

}
