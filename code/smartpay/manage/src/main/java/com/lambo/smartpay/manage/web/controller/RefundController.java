package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.RefundService;
import com.lambo.smartpay.core.service.RefundStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.config.SecurityUser;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesRefund;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
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
@RequestMapping("/refund")
public class RefundController {

    private static final Logger logger = LoggerFactory.getLogger(RefundController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private RefundStatusService refundStatusService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "refund";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Refund";
    }

    @ModelAttribute("paymentStatuses")
    public List<RefundStatus> refundStatuses() {
        return refundStatusService.getAll();
    }

    @RequestMapping(value = {"/", "", "/index"}, method = RequestMethod.GET)
    public String index() {
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

        List<Refund> refunds = refundService.findByCriteria(params.getSearch(),
                start, length, params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = refundService.countAll();
        Long recordsFiltered = refundService.countByCriteria(params.getSearch());
        if (refunds == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesRefund> dataTablesRefunds = new ArrayList<>();
        for (Refund r : refunds) {
            DataTablesRefund refund = new DataTablesRefund(r);
            dataTablesRefunds.add(refund);
        }

        DataTablesResultSet<DataTablesRefund> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesRefunds);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = {"/refund"}, method = RequestMethod.GET)
    public String indexInitiatedRefund(Model model) {

        model.addAttribute("domain", "InitiatedRefund");
        model.addAttribute("action", "refund");
        //model.addAttribute("command", new RefundCommand());
        return "main";
    }

    @RequestMapping(value = {"/listInitiatedRefund"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listInitiatedRefund(Model model, HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        RefundStatus initiatedRefundStatus = null;
        try {
            initiatedRefundStatus = refundStatusService
                    .findByCode(ResourceProperties.REFUND_STATUS_INITIATED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find paid order status.");
        }

        Refund refundCriteria = new Refund();
        refundCriteria.setRefundStatus(initiatedRefundStatus);

        List<Refund> refunds = refundService.findByCriteria(refundCriteria, params.getSearch(),
                start, length, params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = refundService.countByCriteria(refundCriteria);
        Long recordsFiltered = refundService.countByCriteria(refundCriteria, params.getSearch());
        if (refunds == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesRefund> dataTablesRefunds = new ArrayList<>();
        for (Refund r : refunds) {
            DataTablesRefund refund = new DataTablesRefund(r);
            dataTablesRefunds.add(refund);
        }

        DataTablesResultSet<DataTablesRefund> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesRefunds);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = {"/addRefund"}, method = RequestMethod.GET)
    public ModelAndView addRefund(HttpServletRequest request) {

        String orderId = request.getParameter("orderId");
        String amount = request.getParameter("amount");

        logger.debug("~~~~~~~~~~~ orderId = " + orderId);
        logger.debug("~~~~~~~~~~~ amount = " + amount);

        if (StringUtils.isBlank(orderId)) {
            throw new BadRequestException("400", "Order id is blank.");
        }

        ModelAndView view = new ModelAndView("refund/_refundDialog");
        view.addObject("orderId", orderId);
        view.addObject("amount", amount);
        return view;
    }

    @RequestMapping(value = {"/addRefund"}, method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveRefund(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();

        String orderId = request.getParameter("orderId");
        Float amount = Float.parseFloat(request.getParameter("amount"));
        String remark = request.getParameter("amount");

        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(amount.toString())
                || StringUtils.isBlank(remark.toString())) {
            throw new BadRequestException("400", "Bad request.");
        }

        Order order = null;
        try {
            order = orderService.get(Long.valueOf(orderId));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Order cannot be found.");
        }

        // retrieve order and associated customer, set refund recipient to be
        // the same with customer for now
        Refund refund = createRefund(order);
        refund.setAmount(amount);
        refund.setRemark(remark);
        RefundStatus shippedRefundStatus = null;
        OrderStatus shippedOrderStatus = null;
        try {
            shippedRefundStatus = refundStatusService.findByCode(ResourceProperties
                    .REFUND_STATUS_FUNDED_CODE);
            shippedOrderStatus = orderStatusService.findByCode(ResourceProperties
                    .ORDER_STATUS_REFUNDED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find refund or order status.");
        }
        refund.setRefundStatus(shippedRefundStatus);
        order.setOrderStatus(shippedOrderStatus);
        // when persisting refund, order should be cascaded merged
        String domain = messageSource.getMessage("Refund.label", null, locale);
        String failedMessage = messageSource.getMessage("not.saved.message",
                new String[]{domain, amount + " " + remark}, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, amount + " " + remark}, locale);
        try {
            refund = refundService.create(refund);
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

    private Refund createRefund(Order order) {
        Refund refund = new Refund();
        refund.setOrder(order);
        refund.setBillFirstName(order.getCustomer().getFirstName());
        refund.setBillLastName(order.getCustomer().getLastName());
        refund.setBillAddress1(order.getCustomer().getAddress1());
        refund.setBillCity(order.getCustomer().getCity());
        refund.setBillState(order.getCustomer().getState());
        refund.setBillZipCode(order.getCustomer().getZipCode());
        refund.setBillCountry(order.getCustomer().getCountry());
        return refund;
    }
}
