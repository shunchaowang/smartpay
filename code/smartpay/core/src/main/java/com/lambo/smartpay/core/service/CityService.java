package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.EntityNotFoundException;
import com.lambo.smartpay.core.model.City;

public interface CityService {

    City createCity(String name, Integer provinceId) throws EntityNotFoundException;

}
