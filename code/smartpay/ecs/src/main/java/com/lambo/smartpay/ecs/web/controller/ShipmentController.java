package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.Shipment;
import com.lambo.smartpay.core.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.ShipmentService;
import com.lambo.smartpay.core.service.ShipmentStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrder;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesShipment;
import com.lambo.smartpay.ecs.web.vo.table.JsonResponse;
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

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "shipment/indexAll");
        return "main";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("_view", "shipment/shipping");
        return "main";
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {
        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }
        Merchant merchant = securityUser.getMerchant();
        Order orderCriteria = new Order();
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        orderCriteria.setSite(siteCriteria);
        Shipment shipmentCriteria = new Shipment();
        shipmentCriteria.setOrder(orderCriteria);

        List<Shipment> shipments =
                shipmentService.findByCriteria(shipmentCriteria, params.getSearch(),
                        start, length, params.getOrder(),
                        ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = shipmentService.countByCriteria(shipmentCriteria);
        Long recordsFiltered =
                shipmentService.countByCriteria(shipmentCriteria, params.getSearch());
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

    @RequestMapping(value = {"/shipping"}, method = RequestMethod.GET)
    public String indexWaitForShipping(Model model) {

        model.addAttribute("domain", "WaitForShipping");
        model.addAttribute("_view", "shipment/shipping");
        //model.addAttribute("command", new ShipmentCommand());
        return "main";
    }

    @RequestMapping(value = {"/listWaitForShipping"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listWaitForShipping(Model model, HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }

        Merchant merchant = securityUser.getMerchant();
        Order orderCriteria = new Order();
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        orderCriteria.setSite(siteCriteria);

        // only paid order can be shipped
        OrderStatus paidOrderStatus = null;
        try {
            paidOrderStatus = orderStatusService
                    .findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find paid order status.");
        }

        orderCriteria.setOrderStatus(paidOrderStatus);

        List<Order> orders = orderService.findByCriteria(orderCriteria, params.getSearch(),
                start, length, params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = orderService.countByCriteria(orderCriteria);
        Long recordsFiltered = orderService.countByCriteria(orderCriteria, params.getSearch());
        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesOrder> dataTablesOrders = new ArrayList<>();
        for (Order o : orders) {
            DataTablesOrder order = new DataTablesOrder(o);
            dataTablesOrders.add(order);
        }

        DataTablesResultSet<DataTablesOrder> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesOrders);
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
        String domain = messageSource.getMessage("shipment.label", null, locale);
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
