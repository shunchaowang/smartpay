/**
 * 
 */
package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lambo.smartpay.core.dao.CityDao;
import com.lambo.smartpay.core.dao.ProvinceDao;
import com.lambo.smartpay.core.model.City;
import com.lambo.smartpay.core.model.Province;
import com.lambo.smartpay.core.service.CityService;

/**
 * @author swang
 *
 */
@Service("cityService")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;

	@Autowired
	private ProvinceDao provinceDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lambo.smartpay.core.service.CityService#createCity(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public City createCity(String name, Integer provinceId) throws EntityNotFoundException {
		
		// find province by name
		Province province = provinceDao.get(provinceId);
        if (province == null) {
            throw new EntityNotFoundException("Province " + provinceId + " does not exist.");
        }
		// create city, and set the province
        City city = new City();
        city.setProvinceId(provinceId);
		// call dao to persist the entity
        city = cityDao.create(city);
		return city;
	}

}
