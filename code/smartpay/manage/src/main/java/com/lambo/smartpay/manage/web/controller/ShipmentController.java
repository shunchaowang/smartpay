package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.persistence.entity.Shipment;
import com.lambo.smartpay.core.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.ShipmentService;
import com.lambo.smartpay.core.service.ShipmentStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesShipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swang on 4/8/2015.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private ShipmentStatusService shipmentStatusService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("paymentStatuses")
    public List<ShipmentStatus> shipmentStatuses() {
        return shipmentStatusService.getAll();
    }


    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index( Model model) {
        model.addAttribute("_view", "shipment/indexAll");
        return "main";
    }

    @RequestMapping(value = {"/list/all"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {
        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<Shipment> shipments =
                shipmentService.findByCriteria(params.getSearch(),
                        start, length, params.getOrder(),
                        ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = shipmentService.countAll();
        Long recordsFiltered =
                shipmentService.countByCriteria(params.getSearch());
        if (shipments == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesShipment> dataTablesShipments = new ArrayList<>();
        for (Shipment s : shipments) {
            DataTablesShipment shipment = new DataTablesShipment(s);
            dataTablesShipments.add(shipment);
        }

        DataTablesResultSet<DataTablesShipment> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesShipments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

}
