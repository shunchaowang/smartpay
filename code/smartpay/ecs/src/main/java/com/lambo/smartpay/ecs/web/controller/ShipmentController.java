package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.ShipmentService;
import com.lambo.smartpay.core.service.ShipmentStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesShipment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
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

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "shipment";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Shipment";
    }

    @ModelAttribute("paymentStatuses")
    public List<ShipmentStatus> shipmentStatuses() {
        return shipmentStatusService.getAll();
    }

    @RequestMapping(value = {"/", "", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    public String list() {
        return null;
    }

    @RequestMapping(value = {"/shipping"}, method = RequestMethod.GET)
    public String indexWaitForShipping(Model model) {

        model.addAttribute("domain", "WaitForShipping");
        model.addAttribute("action", "shipping");
        return "main";
    }

    @RequestMapping(value = {"/listWaitForShipping"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listWaitForShipping(Model model, HttpServletRequest request) {

        model.addAttribute("domain", "WaitForShipping");

        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        // only paid order can be shipped
        OrderStatus paidOrderStatus = null;
        try {
            paidOrderStatus =  orderStatusService
                    .findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find paid order status.");
        }
        Order orderCriteria = new Order();
        orderCriteria.setOrderStatus(paidOrderStatus);

        List<Order> orders = orderService.findByCriteria(orderCriteria, params.getSearch(),
                start, length, params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = orderService.countByCriteria(orderCriteria);
        Long recordsFiltered = orderService.countByCriteria(orderCriteria, params.getSearch());
        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesShipment> shipments = new ArrayList<>();
        for (Order order : orders) {
            DataTablesShipment shipment = new DataTablesShipment(order);
            shipments.add(shipment);
        }

        DataTablesResultSet<DataTablesShipment> resultSet = new DataTablesResultSet<>();
        resultSet.setData(shipments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }
}
