package com.lambo.smartpay.service;

import com.lambo.smartpay.exception.NoSuchEntityException;

import java.io.Serializable;

/**
 * Created by swang on 3/9/2015.
 */
public interface GenericLookupService<T extends Serializable, PK>
        extends GenericQueryService<T, PK> {

    T findByName(String name) throws NoSuchEntityException;

    T findByCode(String code) throws NoSuchEntityException;
}
