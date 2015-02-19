package com.lambo.smartpay.dao.impl;

import com.lambo.smartpay.dao.MerchantDao;
import com.lambo.smartpay.model.Merchant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swang on 2/19/2015.
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends GenericDaoImpl<Merchant, Long> implements MerchantDao {


    /**
     * Count all record.
     *
     * @param merchant object with criteria.
     */
    @Override
    public Long countByMerchant(Merchant merchant) {

        String jpql = "SELECT COUNT(m.id) FROM Merchant m";

        String formulateJPQL = formulateWhere(jpql, merchant);
        Map<String, Object> formulateParams = foumulateParams(merchant);

        return super.count(formulateJPQL, formulateParams);
    }

    /**
     * Find all records with pagination in order.
     *
     * @param merchant   object with criteria for basic attributes.
     * @param pageNumber
     * @param pageSize
     */
    @Override
    public List<Merchant>
    findAllByMerchant(Merchant merchant, Integer pageNumber, Integer pageSize, String order, String orderDir) {

        String jpql = "SELECT m FROM Merchant m";
        String jpqlWithWhere = formulateWhere(jpql, merchant);
        Map<String, Object> formulateParams = foumulateParams(merchant);
        String jpqlWithOrder = formulateOrderBy(jpqlWithWhere, order, orderDir);

        return super.findAll(jpqlWithOrder, formulateParams, pageNumber, pageSize);
    }

    /**
     * Find all records with pagination in order.
     *
     * @param merchant   object with criteria for basic attributes.
     * @param pageNumber
     * @param pageSize
     */
    @Override
    public List<Merchant>
    findAllByMerchant(Merchant merchant, Integer pageNumber, Integer pageSize) {

        String jpql = "SELECT m FROM Merchant m";
        String jpqlWithWhere = formulateWhere(jpql, merchant);
        Map<String, Object> formulateParams = foumulateParams(merchant);

        return super.findAll(jpqlWithWhere, formulateParams, pageNumber, pageSize);
    }

    /**
     * Formulate jpql with named parameters.
     *
     * @param jpql     initial query language
     * @param merchant all query criteria
     * @return a jqpl string like SELECT m FROM Merchant m WHERE m.name = :name AND m.description = :description
     * ORDER BY :order :direction
     */
    private String formulateWhere(String jpql, Merchant merchant) {

        List<String> whereClause = new ArrayList<String>();

        // iterate all basic attributes of merchant to put the content in the clause if present
        if (StringUtils.isNotBlank(merchant.getNumber())) {
            whereClause.add("m.number LIKE :number");
        }
        if (StringUtils.isNotBlank(merchant.getName())) {
            whereClause.add("m.name LIKE :name");
        }
        if (StringUtils.isNotBlank(merchant.getAddress())) {
            whereClause.add("m.address LIKE :address");
        }
        if (StringUtils.isNotBlank(merchant.getContact())) {
            whereClause.add("m.contact LIKE :contact");
        }
        if (StringUtils.isNotBlank(merchant.getTel())) {
            whereClause.add("m.tel LIKE :tel");
        }
        if (StringUtils.isNotBlank(merchant.getEmail())) {
            whereClause.add("m.email LIKE :email");
        }

        if (StringUtils.isNotBlank(merchant.getRemark())) {
            whereClause.add("m.remark LIKE :remark");
        }

        if (whereClause.size() > 0) {
            jpql += " WHERE " + StringUtils.join(whereClause, " and ");
        }

        jpql += " ORDER BY m.number DESC";
        return jpql;
    }

    private String formulateOrderBy(String jpql, String order, String orderDir) {

        String result = jpql + " ORDER BY m.";
        // check if order is the attribute of Merchant
        Field[] fields = Merchant.class.getFields();
        //List<Field> fieldList = Arrays.asList(fields);
        List<String> fieldNameList = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            fieldNameList.add(fields[i].getName());
        }

        // use number as default if order is not any name of the attributes
        if (fieldNameList.contains(order)) {
            result += order;
        } else {
            result += "number";
        }

        // default order direction would be DESC
        if (("ASC".equals(orderDir)) || ("DESC".equals(orderDir))) {
            result += " " + orderDir;
        } else {
            result += " " + "DESC";
        }

        return result;
    }

    /**
     * Formulate jqpl named parameters map.
     *
     * @param merchant all query criteria
     * @return a HashMap with parameters like name: 'Jack'
     */
    private Map<String, Object> foumulateParams(Merchant merchant) {

        Map<String, Object> params = new HashMap<String, Object>();

        // iterate all attributes of merchant to put the content into the params map if present
        if (StringUtils.isNotBlank(merchant.getNumber())) {
            params.put("number", merchant.getNumber());
        }
        if (StringUtils.isNotBlank(merchant.getName())) {
            params.put("name", merchant.getName());
        }
        if (StringUtils.isNotBlank(merchant.getAddress())) {
            params.put("address", merchant.getAddress());
        }
        if (StringUtils.isNotBlank(merchant.getContact())) {
            params.put("contact", merchant.getContact());
        }
        if (StringUtils.isNotBlank(merchant.getTel())) {
            params.put("tel", merchant.getTel());
        }
        if (StringUtils.isNotBlank(merchant.getEmail())) {
            params.put("email", merchant.getEmail());
        }

        if (StringUtils.isNotBlank(merchant.getRemark())) {
            params.put("remark", merchant.getRemark());
        }

        return params;
    }
}
