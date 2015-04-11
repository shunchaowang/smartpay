package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
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
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesShipment;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        logger.debug("~~~~~~~~shipping~~~~~~~~~~" + "/shipping");
        model.addAttribute("domain", "WaitForShipping");
        model.addAttribute("action", "shipping");
        //model.addAttribute("command", new ShipmentCommand());
        return "main";
    }

    @RequestMapping(value = {"/listWaitForShipping"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listWaitForShipping(Model model, HttpServletRequest request) {

        logger.debug("~~~~~~~~shipping~~~~~~~~~~" + "/WaitForShipping");

        model.addAttribute("domain", "WaitForShipping");

        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        // only shipped order can be shipped
        OrderStatus paidOrderStatus = null;
        try {
            paidOrderStatus = orderStatusService
                    .findByCode(ResourceProperties.ORDER_STATUS_SHIPPED_CODE);
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

    @RequestMapping(value = {"/addShipment"}, method = RequestMethod.GET)
    public ModelAndView addShipment(HttpServletRequest request) {

        String orderId = request.getParameter("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new BadRequestException("400", "Order id is blank.");
        }

        ModelAndView view = new ModelAndView("shipment/_shipmentDialog");
        view.addObject("orderId", orderId);
        return view;
    }

    @RequestMapping(value = {"/addShipment"}, method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveShipment(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();

        String orderId = request.getParameter("orderId");
        String carrier = request.getParameter("carrier");
        String trackingNumber = request.getParameter("trackingNumber");

        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(carrier)
                || StringUtils.isBlank(trackingNumber)) {
            throw new BadRequestException("400", "Bad request.");
        }

        Order order = null;
        try {
            order = orderService.get(Long.valueOf(orderId));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Order cannot be found.");
        }

        // retrieve order and associated customer, set shipment recipient to be
        // the same with customer for now
        Shipment shipment = createShipment(order);
        shipment.setCarrier(carrier);
        shipment.setTrackingNumber(trackingNumber);
        ShipmentStatus shippedShipmentStatus = null;
        OrderStatus shippedOrderStatus = null;
        try {
            shippedShipmentStatus = shipmentStatusService.findByCode(ResourceProperties
                    .SHIPMENT_STATUS_SHIPPED_CODE);
            shippedOrderStatus = orderStatusService.findByCode(ResourceProperties
                    .ORDER_STATUS_SHIPPED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find shipment or order status.");
        }
        shipment.setShipmentStatus(shippedShipmentStatus);
        order.setOrderStatus(shippedOrderStatus);
        // when persisting shipment, order should be cascaded merged
        String domain = messageSource.getMessage("Shipping.label", null, locale);
        String failedMessage = messageSource.getMessage("not.saved.message",
                new String[]{domain, carrier + " " + trackingNumber}, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, carrier + " " + trackingNumber}, locale);
        try {
            shipment = shipmentService.create(shipment);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            response.setStatus("failed");
            response.setMessage(failedMessage);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            response.setStatus("failed");
            response.setMessage(failedMessage);
        }
        response.setStatus("successful");
        response.setMessage(successfulMessage);

        return JsonUtil.toJson(response);
    }

    private Shipment createShipment(Order order) {
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setFirstName(order.getCustomer().getFirstName());
        shipment.setLastName(order.getCustomer().getLastName());
        shipment.setAddress1(order.getCustomer().getAddress1());
        shipment.setCity(order.getCustomer().getCity());
        shipment.setState(order.getCustomer().getState());
        shipment.setZipCode(order.getCustomer().getZipCode());
        shipment.setCountry(order.getCustomer().getCountry());
        return shipment;
    }
}
