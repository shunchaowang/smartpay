package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.service.GenericQueryService;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by swang on 3/10/2015.
 */
@Repository("merchantService")
public class MerchantServiceImpl implements MerchantService, GenericQueryService<Merchant, Long> {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    /**
     * Count number of T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param activeFlag specify active or not.
     * @return count of the result.
     */
    @Override
    public Long countByAdHocSearch(String search, Boolean activeFlag) {
        return null;
    }

    /**
     * Find all T matching the search. Support ad hoc search on attributes of T.
     *
     * @param search     search keyword.
     * @param start      start position for pagination.
     * @param length     result size fo pagination.
     * @param order      ordered field.
     * @param orderDir   ordered direction.
     * @param activeFlag active or not.
     * @return ordered list of the T.
     */
    @Override
    public List<Merchant> findByAdHocSearch(String search, Integer start, Integer length, String
            order, ResourceUtil.JpaOrderDir orderDir, Boolean activeFlag) {
        return null;
    }

    /**
     * Count T by criteria.
     * Support attributes of T.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @return number of the T matching search.
     */
    @Override
    public Long countByAdvanceSearch(Merchant merchant) {
        return null;
    }

    /**
     * Find T by criteria.
     * Support attributes of T.
     *
     * @param merchant contains criteria if the field is not null or empty.
     * @param start
     * @param length   @return List of the T matching search ordered by id with pagination.
     */
    @Override
    public List<Merchant> findByAdvanceSearch(Merchant merchant, Integer start, Integer length) {
        return null;
    }

    @Override
    public Merchant create(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        return null;
    }

    @Override
    public Merchant get(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public Merchant update(Merchant merchant) throws MissingRequiredFieldException,
            NotUniqueException {
        return null;
    }

    @Override
    public Merchant delete(Long id) throws NoSuchEntityException {
        return null;
    }

    @Override
    public Merchant findByName(String name) {
        return null;
    }
}
