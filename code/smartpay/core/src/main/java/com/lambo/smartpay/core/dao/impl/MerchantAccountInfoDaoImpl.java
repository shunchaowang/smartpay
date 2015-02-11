package com.lambo.smartpay.core.dao.impl;

import com.lambo.smartpay.core.dao.MerchantAccountInfoDao;
import com.lambo.smartpay.core.model.MerchantAccountInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swang on 2/11/2015.
 */
public class MerchantAccountInfoDaoImpl extends GenericDaoImpl<MerchantAccountInfo, String>
        implements MerchantAccountInfoDao {

    /**
     * Count all record.
     *
     * @param merchantAccountInfo object with criteria.
     */
    @Override
    public Long countByWhere(MerchantAccountInfo merchantAccountInfo) {

        String jpql = "SELECT COUNT(m.accountNo) FROM MerchantAccountInfo m";

        String formulateJPQL = formulateJPQL(jpql, merchantAccountInfo);
        Map<String, Object> formulateParams = foumulateParams(merchantAccountInfo);

        return super.count(formulateJPQL, formulateParams);
    }

    /**
     * Find all records with pagination.
     *
     * @param merchantAccountInfo object with criteria.
     * @param pageNumber
     * @param pageSize
     */
    @Override
    public List<MerchantAccountInfo>
    findAllByWhere(MerchantAccountInfo merchantAccountInfo, Integer pageNumber, Integer pageSize) {

        String jpql = "SELECT m FROM MerchantAccountInfo m";
        String formulateJPQL = formulateJPQL(jpql, merchantAccountInfo);
        Map<String, Object> formulateParams = foumulateParams(merchantAccountInfo);

        return super.findAll(formulateJPQL, formulateParams, pageNumber, pageSize);
    }

    /**
     * Formulate jpql with named parameters.
     *
     * @param jpql                initial query language
     * @param merchantAccountInfo all query criteria
     * @return a jqpl string like SELECT m FROM Merchant m WHERE m.name = :name AND m.description = : description
     * ORDER BY :order :direction
     */
    private String formulateJPQL(String jpql, MerchantAccountInfo merchantAccountInfo) {

        List<String> whereClause = new ArrayList<String>();

        // iterate all attributes of merchantAccountInfo to put the content in the clause if present
        if (StringUtils.isNotBlank(merchantAccountInfo.getAccountNo())) {
            whereClause.add("m.accountNo = :accountNo");
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getCustomerNo())) {
            whereClause.add("m.customerNo = :customerNo");
        }
        if (merchantAccountInfo.getVersion() != null) {
            whereClause.add("m.version = :version");
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getStatus())) {
            whereClause.add("m.status = :status");
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getAccountType())) {
            whereClause.add("m.accountType = :accountType");
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getCurrencyType())) {
            whereClause.add("m.currencyType = :currencyType");
        }

        if (whereClause.size() > 0) {
            jpql += " WHERE " + StringUtils.join(whereClause, " and ");
        }

        jpql += " ORDER BY m.accountNo DESC";
        return jpql;
    }

    /**
     * Formulate jqpl named parameters map.
     *
     * @param merchantAccountInfo all query criteria
     * @return a HashMap with parameters like name: 'Jack'
     */
    private Map<String, Object> foumulateParams(MerchantAccountInfo merchantAccountInfo) {

        Map<String, Object> params = new HashMap<String, Object>();

        // iterate all attributes of merchantAccountInfo to put the content into the params map if present
        if (StringUtils.isNotBlank(merchantAccountInfo.getAccountNo())) {
            params.put("accountNo", merchantAccountInfo.getAccountNo());
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getCustomerNo())) {
            params.put("customerNo", merchantAccountInfo.getCustomerNo());
        }
        if (merchantAccountInfo.getVersion() != null) {
            params.put("version", merchantAccountInfo.getAccountNo());
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getStatus())) {
            params.put("status", merchantAccountInfo.getStatus());
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getAccountType())) {
            params.put("accountType", merchantAccountInfo.getAccountType());
        }
        if (StringUtils.isNotBlank(merchantAccountInfo.getCurrencyType())) {
            params.put("currencyType", merchantAccountInfo.getCurrencyType());
        }

        return params;
    }
}
