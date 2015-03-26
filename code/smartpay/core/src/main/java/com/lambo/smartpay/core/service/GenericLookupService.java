package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.NoSuchEntityException;

import java.io.Serializable;

/**
 * Created by swang on 3/9/2015.
 */
public interface GenericLookupService<T extends Serializable, PK>
        extends GenericService<T, PK> {

    T findByName(String name) throws NoSuchEntityException;

    T findByCode(String code) throws NoSuchEntityException;
}
