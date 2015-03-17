package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.ShipmentDao;
import com.lambo.smartpay.persistence.entity.Shipment;
import com.lambo.smartpay.util.ResourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by swang on 3/9/2015.
 */
@Repository("shipmentDao")
public class ShipmentDaoImpl extends GenericDaoImpl<Shipment, Long>
        implements ShipmentDao {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentDaoImpl.class);

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search.
     *
     * @param shipment   contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Shipment shipment, String search, Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * There are two parts of the search to support grails criteria search with DataTables instant
     * search; DataTables dynamic ordering is also supported;
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param shipment   contains all criteria for equals, like name equals xx and active equals
     *                   true, etc.
     *                   it means no criteria on exact equals if t is null.
     * @param search     instance wildcard search keyword, like name likes %xx%, etc.
     *                   it means no criteria with wildcard search if search is null.
     * @param start      first position of the result.
     * @param length     max record of the result.
     * @param order      order by field.
     * @param orderDir   order direction on the order field.
     * @param rangeStart createdTime date range, needs both start and end.
     * @param rangeEnd   createdTime date range, needs both start and end.
     * @return
     */
    @Override
    public List<Shipment> findByCriteria(Shipment shipment, String search, Integer start, Integer
            length, String order, ResourceProperties.JpaOrderDir orderDir, Date rangeStart, Date
                                                 rangeEnd) {
        return null;
    }

    @Override
    public Boolean isBlank(Shipment shipment) {
        return null;
    }
}
