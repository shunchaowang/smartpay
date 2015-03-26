package com.lambo.smartpay.core.service;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by swang on 3/9/2015.
 */
public interface GenericService<T extends Serializable, PK> {

    T create(T t) throws MissingRequiredFieldException, NotUniqueException;

    T get(PK id) throws NoSuchEntityException;

    T update(T t) throws MissingRequiredFieldException, NotUniqueException;

    T delete(PK id) throws NoSuchEntityException;

    List<T> getAll();

    Long countAll();
}
