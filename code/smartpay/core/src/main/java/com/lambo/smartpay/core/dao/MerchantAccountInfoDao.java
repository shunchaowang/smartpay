package com.lambo.smartpay.core.dao;

import com.lambo.smartpay.core.model.MerchantAccountInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swang on 2/11/2015.
 */
public interface MerchantAccountInfoDao extends GenericDao<MerchantAccountInfo, String> {

    /**
     * Count all record.
     *
     * @param merchantAccountInfo object with criteria.
     */
    public Long countByWhere(MerchantAccountInfo merchantAccountInfo);

    /**
     * Find all records with pagination.
     *
     * @param merchantAccountInfo object with criteria.
     */
    public List<MerchantAccountInfo>
    findAllByWhere(MerchantAccountInfo merchantAccountInfo, Integer pageNumber, Integer pageSize);

}
