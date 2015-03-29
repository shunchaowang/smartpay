package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesOrder;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "order";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Order";
    }

    @ModelAttribute("orderStatuses")
    public List<OrderStatus> orderStatuses() {
        logger.debug("order status: " + orderStatusService.countAll());
        return orderStatusService.getAll();
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String list(HttpServletRequest request) {

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

        List<Order> orders = orderService.findByCriteria(search, start, length,
                order, ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Long recordsTotal = orderService.countAll();
        // count records filtered
        Long recordsFiltered = orderService.countByCriteria(search);

        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesOrder> dataTablesOrders = new ArrayList<>();

        for (Order o : orders) {
            DataTablesOrder tablesOrder = new DataTablesOrder(o);
            dataTablesOrders.add(tablesOrder);
        }

        DataTablesResultSet<DataTablesOrder> result = new DataTablesResultSet<>();
        result.setData(dataTablesOrders);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("action", "search");
        model.addAttribute("sites", siteService.getAll());
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

        // parse my own query params
        String id = request.getParameter("id");
        String merchantNumber = request.getParameter("merchantNUmber");
        String orderStatus = request.getParameter("orderStatus");
        String site = request.getParameter("site");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        Order orderCriteria = new Order();
        if (!StringUtils.isBlank(id)) {
            orderCriteria.setId(Long.valueOf(id));
        }
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
            Site siteCriteria = null;
            try {
                siteCriteria = siteService.get(Long.valueOf(site));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            orderCriteria.setSite(siteCriteria);
        }
        Date beginning = null;
        Date ending = null;
        if ((!StringUtils.isBlank(timeBeginning)) && (!StringUtils.isBlank(timeEnding))) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                beginning = dateFormat.parse(timeBeginning);
                ending = dateFormat.parse(timeEnding);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Bad Request.");
            }
        }

        List<Order> orders = orderService.findByCriteria(orderCriteria, search, start, length,
                order, ResourceProperties.JpaOrderDir.valueOf(orderDir), beginning, ending);

        // count total records
        Long recordsTotal = orderService.countAll();
        // count records filtered
        Long recordsFiltered = orderService.countByCriteria(search);

        if (orders == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesOrder> dataTablesOrders = new ArrayList<>();

        for (Order o : orders) {
            DataTablesOrder tablesOrder = new DataTablesOrder(o);
            dataTablesOrders.add(tablesOrder);
        }

        DataTablesResultSet<DataTablesOrder> result = new DataTablesResultSet<>();
        result.setData(dataTablesOrders);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        return JsonUtil.toJson(result);
    }
}
