package com.lambo.smartpay.ecs.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.OrderCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrder;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swang on 3/27/2015.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private ShipmentStatusService shipmentStatusService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "order";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "order";
    }

    @ModelAttribute("orderStatuses")
    public List<OrderStatus> orderStatuses() {
        logger.debug("order status: " + orderStatusService.countAll());
        return orderStatusService.getAll();
    }

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }
        Merchant merchant = securityUser.getMerchant();
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        List<Site> sites = siteService.findByCriteria(siteCriteria);
        model.addAttribute("sites", sites);
        model.addAttribute("_view", "order/indexAll");
        return "main";
    }

    @RequestMapping(value = {"/index/archive"}, method = RequestMethod.GET)
    public String indexArchive(Model model) {
        model.addAttribute("_view", "site/indexArchive");
        return "main";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {

        // parse sorting column
        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }

        Merchant merchant = securityUser.getMerchant();
        Order orderCriteria = new Order();
        Site siteCriteria = new Site();

        // parse my own query params
        String merchantNumber = request.getParameter("merchantNumber");
        String orderStatus = request.getParameter("orderStatus");
        String site = request.getParameter("site");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        Date beginning = null;
        Date ending = null;
        if ((!StringUtils.isBlank(timeBeginning)) && (!StringUtils.isBlank(timeEnding))) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                beginning = dateFormat.parse(timeBeginning);
                ending = dateFormat.parse(timeEnding);
                Calendar calendar   =   new GregorianCalendar();
                calendar.setTime(ending);
                calendar.add(calendar.DATE,1);
                ending = calendar.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Bad Request.");
            }
        }
        List<DataTablesOrder> dataTablesOrders = new ArrayList<>();
        DataTablesResultSet<DataTablesOrder> result = new DataTablesResultSet<>();
        if (!StringUtils.isBlank(merchantNumber)) {
            orderCriteria.setMerchantNumber(merchantNumber);
        }
        if (!StringUtils.isBlank(orderStatus)) {
            OrderStatus status = null;
            try {
                status = orderStatusService.get(Long.valueOf(orderStatus));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            orderCriteria.setOrderStatus(status);
        }
        if (!StringUtils.isBlank(site)) {
            try {
                siteCriteria = siteService.get(Long.valueOf(site));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            orderCriteria.setSite(siteCriteria);
        }else{
            siteCriteria.setMerchant(merchant);
            orderCriteria.setSite(siteCriteria);
        }
        List<Order> orders = orderService.findByCriteria(orderCriteria, search, start, length,
                order, ResourceProperties.JpaOrderDir.valueOf(orderDir), beginning, ending);
        // count total records
        Long recordsTotal = orderService.countByCriteria(orderCriteria);
        // count records filtered
        Long recordsFiltered = orderService.countByCriteria(orderCriteria, search);
        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        for (Order o : orders) {
            Payment paymentCriteria = new Payment();
            paymentCriteria.setOrder(o);
            List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search,
                    null, null, "", ResourceProperties.JpaOrderDir.valueOf(orderDir), null, null);
            if((payments!= null&&payments.size() >0)) {
                for (Payment p : payments) {
                    DataTablesOrder tablesOrder = new DataTablesOrder(o);
                    tablesOrder.setBankName(p.getBankName());
                    tablesOrder.setBankTransactionNumber(p.getBankTransactionNumber());
                    tablesOrder.setBankReturnCode(p.getBankReturnCode());
                    tablesOrder.setPaymentStatusId(p.getPaymentStatus().getId());
                    tablesOrder.setPaymentStatusName(p.getPaymentStatus().getName());
                    tablesOrder.setPaymentTypeId(p.getPaymentType().getId());
                    tablesOrder.setPaymentTypeName(p.getPaymentType().getName());
                    tablesOrder.setBillFirstName(p.getBillFirstName());
                    tablesOrder.setBillLastName(p.getBillLastName());
                    tablesOrder.setBillAddress1(p.getBillAddress1());
                    tablesOrder.setBillAddress2(p.getBillAddress2());
                    tablesOrder.setBillCity(p.getBillCity());
                    tablesOrder.setBillState(p.getBillState());
                    tablesOrder.setBillZipCode(p.getBillZipCode());
                    tablesOrder.setBillCountry(p.getBillCountry());
                    dataTablesOrders.add(tablesOrder);
                }
            }else {
                DataTablesOrder tablesOrder = new DataTablesOrder(o);
                dataTablesOrders.add(tablesOrder);
            }
        }
        result.setData(dataTablesOrders);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Order order = null;
        try {
            order = orderService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        OrderCommand orderCommand = createOrderCommand(order);
        model.addAttribute("orderCommand", orderCommand);

        model.addAttribute("_view", "order/show");
        return "main";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }
        Merchant merchant = securityUser.getMerchant();
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        List<Site> sites = siteService.findByCriteria(siteCriteria);
        model.addAttribute("sites", sites);
        model.addAttribute("_view", "order/search");
        return "main";
    }

    @RequestMapping(value = "/searchData", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String searchData(HttpServletRequest request) {

        // parse sorting column
        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User is null.");
        }

        Merchant merchant = securityUser.getMerchant();
        Order orderCriteria = new Order();
        Site siteCriteria = new Site();

        // parse my own query params
        String merchantNumber = request.getParameter("merchantNumber");
        String orderStatus = request.getParameter("orderStatus");
        String site = request.getParameter("site");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        Date beginning = null;
        Date ending = null;
        if ((!StringUtils.isBlank(timeBeginning)) && (!StringUtils.isBlank(timeEnding))) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                beginning = dateFormat.parse(timeBeginning);
                ending = dateFormat.parse(timeEnding);
                Calendar calendar   =   new GregorianCalendar();
                calendar.setTime(ending);
                calendar.add(calendar.DATE,1);
                ending = calendar.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Bad Request.");
            }
        }
        List<DataTablesOrder> dataTablesOrders = new ArrayList<>();
        DataTablesResultSet<DataTablesOrder> result = new DataTablesResultSet<>();
        if (!StringUtils.isBlank(merchantNumber)) {
            orderCriteria.setMerchantNumber(merchantNumber);
        }
        if (!StringUtils.isBlank(orderStatus)) {
            OrderStatus status = null;
            try {
                status = orderStatusService.get(Long.valueOf(orderStatus));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            orderCriteria.setOrderStatus(status);
        }
        if (!StringUtils.isBlank(site)) {
            try {
                siteCriteria = siteService.get(Long.valueOf(site));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            orderCriteria.setSite(siteCriteria);
        }else{
            siteCriteria.setMerchant(merchant);
            orderCriteria.setSite(siteCriteria);
        }
        List<Order> orders = orderService.findByCriteria(orderCriteria, search, null, null,
                "", ResourceProperties.JpaOrderDir.valueOf(orderDir), beginning, ending);
        // count total records
        Long recordsTotal = orderService.countByCriteria(orderCriteria);
        // count records filtered
        Long recordsFiltered = recordsTotal;
        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        for (Order o : orders) {
            Payment paymentCriteria = new Payment();
            paymentCriteria.setOrder(o);
            List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search,
                    null, null, "", ResourceProperties.JpaOrderDir.valueOf(orderDir), null, null);
            if((payments!= null&&payments.size() >0)) {
                int len = payments.size();
                if (len > 1) recordsTotal += (len - 1);
                recordsFiltered = recordsTotal;
                for (Payment p : payments) {
                    DataTablesOrder tablesOrder = new DataTablesOrder(o);
                    tablesOrder.setBankName(p.getBankName());
                    tablesOrder.setBankTransactionNumber(p.getBankTransactionNumber());
                    tablesOrder.setBankReturnCode(p.getBankReturnCode());
                    tablesOrder.setPaymentStatusId(p.getPaymentStatus().getId());
                    tablesOrder.setPaymentStatusName(p.getPaymentStatus().getName());
                    tablesOrder.setPaymentTypeId(p.getPaymentType().getId());
                    tablesOrder.setPaymentTypeName(p.getPaymentType().getName());
                    tablesOrder.setBillFirstName(p.getBillFirstName());
                    tablesOrder.setBillLastName(p.getBillLastName());
                    tablesOrder.setBillAddress1(p.getBillAddress1());
                    tablesOrder.setBillAddress2(p.getBillAddress2());
                    tablesOrder.setBillCity(p.getBillCity());
                    tablesOrder.setBillState(p.getBillState());
                    tablesOrder.setBillZipCode(p.getBillZipCode());
                    tablesOrder.setBillCountry(p.getBillCountry());
                    dataTablesOrders.add(tablesOrder);
                }
            }else {
                DataTablesOrder tablesOrder = new DataTablesOrder(o);
                dataTablesOrders.add(tablesOrder);
            }
        }
        result.setData(dataTablesOrders);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = {"/addShipment"}, method = RequestMethod.GET)
    public ModelAndView addShipment(HttpServletRequest request) {

        String orderId = request.getParameter("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new BadRequestException("400", "Order id is blank.");
        }

        ModelAndView view = new ModelAndView("order/_shipmentDialog");
        view.addObject("orderId", orderId);
        return view;
    }

    @RequestMapping(value = {"/saveShipment"}, method = RequestMethod.POST,
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


    private OrderCommand createOrderCommand(Order order) {
        OrderCommand orderCommand = new OrderCommand();

        if (order.getId() != null) {
            orderCommand.setId(order.getId());
        }

        orderCommand.setMerchantNumber(order.getMerchantNumber());
        orderCommand.setAmount(order.getAmount());
        orderCommand.setGoodsName(order.getGoodsName());
        orderCommand.setGoodsAmount(order.getGoodsAmount());
        orderCommand.setCurrencyId(order.getCurrency().getId());
        orderCommand.setCurrencyName(order.getCurrency().getName());
        orderCommand.setSiteId(order.getSite().getId());
        orderCommand.setSiteName(order.getSite().getName());
        orderCommand.setCustomerId(order.getCustomer().getId());
        orderCommand.setCustomerName(
                order.getCustomer().getFirstName() + order.getCustomer().getLastName());
        orderCommand.setCreatedTime(order.getCreatedTime().toString());
        orderCommand.setOrderStatusId(order.getOrderStatus().getId());
        orderCommand.setOrderStatusName(order.getOrderStatus().getName());

        return orderCommand;
    }
}
