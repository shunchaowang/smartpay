package com.lambo.smartpay.dao;

import com.lambo.smartpay.model.Merchant;

import java.util.List;

/**
 * Created by swang on 2/19/2015.
 */
public interface MerchantDao extends GenericDao<Merchant, Long> {

    /**
     * Count all record.
     *
     * @param merchant object with criteria for basic attributes.
     */
    public Long countByMerchant(Merchant merchant);

    /**
     * Find all records with pagination in order.
     *
     * @param merchant object with criteria for basic attributes.
     */
    public List<Merchant> findAllByMerchant(Merchant merchant, Integer pageNumber, Integer pageSize,
                                            String order, String orderDir);

    /**
     * Find all records with pagination without order.
     *
     * @param merchant object with criteria for basic attributes.
     */
    public List<Merchant> findAllByMerchant(Merchant merchant, Integer pageNumber, Integer pageSize);

}
