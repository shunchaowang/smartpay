package com.lambo.smartpay.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, PK extends Serializable> {

	T create(T persistentObject);

	List<T> getAll();
	
	T get(PK id);

	T update(T persistentObject);

	void delete(PK id);

    /**
     * Counts the record using positional parameters.
     * */
    Long count(String criteria, List<Object> args);

    /**
     * Counts the record using name-value-pair parameters.
     * */
    Long count(String criteria, Map<String, Object> args);

    /**
     * Find all records using positional parameters.
     * */
    List<T> findAll(String criteria, List<Object> args);

    /**
     * Find all records using name-value-pair.
     * */
    List<T> findAll(String criteria, Map<String, Object> args);

    /**
     * Find all records using positional parameters with pagination.
     * */
    List<T> findAll(String criteria, List<Object> args, Integer pageNumber, Integer pageSize);

    /**
     * Find all records using name-value-pair with pagination.
     * */
    List<T> findAll(String criteria, Map<String, Object> args, Integer pageNumber, Integer pageSize);

    /**
     * Find all records with pagination without parameters.
     * */
    List<T> findAll(String criteria, Integer pageNumber, Integer pageSize);

}
