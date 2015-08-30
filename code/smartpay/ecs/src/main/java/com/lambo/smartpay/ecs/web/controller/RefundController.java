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
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrder;
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

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "refund/index");
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
        Refund refundCriteria = new Refund();
        refundCriteria.setOrder(orderCriteria);

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

    @RequestMapping(value = {"/refund"}, method = RequestMethod.GET)
    public String indexPaidOrder(Model model) {

        model.addAttribute("domain", "PaidOrder");
        model.addAttribute("_view", "refund/refund");
        return "main";
    }

    @RequestMapping(value = {"/listPaidOrder"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listPaidOrder(Model model, HttpServletRequest request) {


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
        OrderStatus paidOrderStatus = null;
        try {
            paidOrderStatus =
                    orderStatusService.findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
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

    @RequestMapping(value = {"/addRefund"}, method = RequestMethod.GET)
    public ModelAndView addRefund(HttpServletRequest request) {

        String orderId = request.getParameter("orderId");

        if (StringUtils.isBlank(orderId)) {
            throw new BadRequestException("400", "Order id is blank.");
        }

        ModelAndView view = new ModelAndView("refund/_refundDialog");
        view.addObject("domain", "Refund");
        view.addObject("orderId", orderId);
        return view;
    }

    @RequestMapping(value = {"/saveRefund"}, method = RequestMethod.POST,
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
        Refund refund = createRefund(order);
        refund.setAmount(amount);
        refund.setRemark(remark);
        RefundStatus refundStatus = null;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties
                    .REFUND_STATUS_INITIATED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find refund or order status.");
        }
        refund.setRefundStatus(refundStatus);
        String domain = messageSource.getMessage("refund.label", null, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, amount + " " + remark}, locale);
        try {
            refundService.create(refund);
            orderService.update(order);
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
