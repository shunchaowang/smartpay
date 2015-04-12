package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.Refund;
import com.lambo.smartpay.core.persistence.entity.RefundStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.RefundService;
import com.lambo.smartpay.core.service.RefundStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesRefund;
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
        return "index";
    }

    public String list() {
        return null;
    }

    @RequestMapping(value = {"/refund"}, method = RequestMethod.GET)
    public String indexWaitForRefund(Model model) {

        model.addAttribute("domain", "WaitForRefund");
        model.addAttribute("action", "refund");
        //model.addAttribute("command", new RefundCommand());
        return "main";
    }

    @RequestMapping(value = {"/listWaitForRefund"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listWaitForRefund(Model model, HttpServletRequest request) {

        model.addAttribute("domain", "WaitForRefund");

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

        List<DataTablesRefund> refunds = new ArrayList<>();
        for (Order order : orders) {
            DataTablesRefund refund = new DataTablesRefund(order);
            refunds.add(refund);
        }

        DataTablesResultSet<DataTablesRefund> resultSet = new DataTablesResultSet<>();
        resultSet.setData(refunds);
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
        String remark = request.getParameter("remark");

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
        RefundStatus refundStatus = null;
        OrderStatus refundedOrderStatus = null;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties
                    .REFUND_STATUS_FUNDED_CODE);
            refundedOrderStatus = orderStatusService.findByCode(ResourceProperties
                    .ORDER_STATUS_REFUNDED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find refund or order status.");
        }
        refund.setRefundStatus(refundStatus);
        order.setOrderStatus(refundedOrderStatus);
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
            throw new BadRequestException("400", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        response.setStatus("successful");
        response.setMessage(successfulMessage);

        return JsonUtil.toJson(response);
    }

    private Refund createRefund(Order order) {
        Refund refund = new Refund();
        refund.setOrder(order);
        refund.setCurrency(order.getCurrency());
        refund.setBillFirstName(order.getCustomer().getFirstName());
        refund.setBillLastName(order.getCustomer().getLastName());
        refund.setBillAddress1(order.getCustomer().getAddress1());
        refund.setBillCity(order.getCustomer().getCity());
        refund.setBillState(order.getCustomer().getState());
        refund.setBillZipCode(order.getCustomer().getZipCode());
        refund.setBillCountry(order.getCustomer().getCountry());
        // bank account number, return code and transaction number
        // are set to be 0000 for now
        refund.setBankAccountNumber("0000");
        refund.setBankReturnCode("0000");
        refund.setBankTransactionNumber("0000");
        return refund;
    }
}
