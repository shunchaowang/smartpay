package com.lambo.smartpay.core.dao;

import com.lambo.smartpay.core.model.City;

/**
 * @author swang
 *
 */
public interface CityDao extends GenericDao<City, Integer> {

	City findByName(String name);
}
