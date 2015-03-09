package com.lambo.smartpay.service;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;

import java.io.Serializable;

/**
 * Created by swang on 3/9/2015.
 */
public interface GenericService<T extends Serializable, PK> {

    T create(T t) throws MissingRequiredFieldException, NotUniqueException;

    T get(PK id) throws NoSuchEntityException;

    T update(T t) throws MissingRequiredFieldException, NotUniqueException;

    T delete(PK id) throws NoSuchEntityException;
}
