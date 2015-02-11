package com.lambo.smartpay.core.dao;

import com.lambo.smartpay.core.model.Province;

/**
 * @author swang
 *
 */
public interface ProvinceDao extends GenericDao<Province, Integer> {

	Province findByName(String name);
}
