package com.lambo.smartpay.ecs.web.controller;

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
import com.lambo.smartpay.ecs.web.vo.PaymentCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesPayment;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by linly on 3/27/2015.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private RefundStatusService refundStatusService;
    @Autowired
    private MessageSource messageSource;
    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "payment";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Payment";
    }

    @ModelAttribute("paymentStatuses")
    public List<PaymentStatus> paymentStatuses() {
        return paymentStatusService.getAll();
    }

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "payment/indexAll");
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
        siteCriteria.setMerchant(merchant);
        orderCriteria.setSite(siteCriteria);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setOrder(orderCriteria);

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search, start,
                length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));
        // count total and filtered
        Long recordsTotal = paymentService.countByCriteria(paymentCriteria);
        Long recordsFiltered = paymentService.countByCriteria(paymentCriteria, search);

        if (payments == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesPayment> dataTablesPayments = new ArrayList<>();
        for (Payment payment : payments) {
            DataTablesPayment tablesPayment = new DataTablesPayment(payment);
            dataTablesPayments.add(tablesPayment);
        }

        DataTablesResultSet<DataTablesPayment> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesPayments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("_view", "payment/search");
        return "main";
    }
    @RequestMapping(value = "/searchData", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchData(HttpServletRequest request) {

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

        // parse my own query params
        String orderNumber = request.getParameter("orderNumber");
        String bankTransactionNumber = request.getParameter("bankTransactionNumber");
        String paymentStatus = request.getParameter("paymentStatus");
        String siteUrl = request.getParameter("siteUrl");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        List<DataTablesPayment> dataTablesPayments = new ArrayList<>();
        DataTablesResultSet<DataTablesPayment> resultSet = new DataTablesResultSet<>();

        Merchant merchant = securityUser.getMerchant();
        Order orderCriteria = new Order();
        Site siteCriteria = new Site();
        if (!StringUtils.isBlank(siteUrl)) {
            siteCriteria = siteService.findByUrl(siteUrl);
            if(siteCriteria ==null){
                resultSet.setData(dataTablesPayments);
                resultSet.setRecordsFiltered(0);
                resultSet.setRecordsTotal(0);
                return JsonUtil.toJson(resultSet);
            }
            orderCriteria.setSite(siteCriteria);
        }
        siteCriteria.setMerchant(merchant);
        orderCriteria.setSite(siteCriteria);
        if (!StringUtils.isBlank(orderNumber)) {
            orderCriteria.setMerchantNumber(orderNumber);
        }
        Payment paymentCriteria = new Payment();
        paymentCriteria.setOrder(orderCriteria);
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
        }if (!StringUtils.isBlank(bankTransactionNumber)) {
            paymentCriteria.setBankTransactionNumber(bankTransactionNumber);
        }
        if (!StringUtils.isBlank(paymentStatus)) {
            PaymentStatus status = null;
            try {
                status = paymentStatusService.get(Long.valueOf(paymentStatus));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            paymentCriteria.setPaymentStatus(status);
        }

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search, null, null, "",
                ResourceProperties.JpaOrderDir.valueOf(orderDir), beginning, ending);
        // count total and filtered
        Long recordsTotal = paymentService.countByCriteria(paymentCriteria, beginning, ending);
        Long recordsFiltered = recordsTotal;

        if (payments == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        for (Payment payment : payments) {
            DataTablesPayment tablesPayment = new DataTablesPayment(payment);
            dataTablesPayments.add(tablesPayment);
        }

        resultSet.setData(dataTablesPayments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Payment payment = null;
        try {
            payment = paymentService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        PaymentCommand paymentCommand = createPaymentCommand(payment);
        model.addAttribute("paymentCommand", paymentCommand);

        model.addAttribute("_view", "payment/show");
        return "main";
    }


    private PaymentCommand createPaymentCommand(Payment payment) {
        PaymentCommand paymentCommand = new PaymentCommand();

        if (payment.getId() != null) {
            paymentCommand.setId(payment.getId());
        }

        //
        paymentCommand.setAmount(payment.getAmount());
        paymentCommand.setCreatedTime(payment.getCreatedTime().toString());
        paymentCommand.setBankName(payment.getBankName());
        paymentCommand.setBankTransactionNumber(payment.getBankTransactionNumber());
        paymentCommand.setBankReturnCode(payment.getBankReturnCode());
        paymentCommand.setPaymentStatusId(payment.getPaymentStatus().getId());
        paymentCommand.setPaymentStatusName(payment.getPaymentStatus().getName());
        paymentCommand.setPaymentTypeId(payment.getPaymentType().getId());
        paymentCommand.setPaymentTypeName(payment.getPaymentType().getName());
        paymentCommand.setOrderId(payment.getOrder().getId());
        paymentCommand.setOrderNumber(payment.getOrder().getMerchantNumber());
        paymentCommand.setCurrencyId(payment.getCurrency().getId());
        paymentCommand.setCurrencyName(payment.getCurrency().getName());
        paymentCommand.setMerchantId(payment.getOrder().getSite().getMerchant().getId());
        paymentCommand.setMerchantName(payment.getOrder().getSite().getMerchant().getName());
        paymentCommand.setMerchantNumber(payment.getOrder().getMerchantNumber());
        paymentCommand.setSiteId(payment.getOrder().getSite().getId());
        paymentCommand.setSiteName(payment.getOrder().getSite().getName());

        if (payment.getSuccessTime() != null) {
            paymentCommand.setSuccessTime(payment.getSuccessTime().toString());
        }

        paymentCommand.setRemark(payment.getRemark());
        paymentCommand.setBillAddress1(payment.getBillAddress1());
        paymentCommand.setBillAddress2(payment.getBillAddress2());
        paymentCommand.setBillFirstName(payment.getBillFirstName());
        paymentCommand.setBillLastName(payment.getBillLastName());
        paymentCommand.setBillCity(payment.getBillCity());
        paymentCommand.setBillState(payment.getBillState());
        paymentCommand.setBillZipCode(payment.getBillZipCode());
        paymentCommand.setBillCountry(payment.getBillCountry());

        return paymentCommand;
    }

    @RequestMapping(value = {"/addRefund"}, method = RequestMethod.GET)
    public ModelAndView addRefund(HttpServletRequest request) {
        String paymentId = request.getParameter("paymentId");
        if (StringUtils.isBlank(paymentId)) {
            throw new BadRequestException("400", "Payment id is blank.");
        }
        Payment payment = null;
        try {
            payment = paymentService.get(Long.valueOf(paymentId));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Payment cannot be found.");
        }
        Order order = payment.getOrder();
        String orderId = String.valueOf(order.getId());
        Refund refund = createRefund(order);
        RefundStatus refundStatus = null;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties.REFUND_STATUS_APPROVED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find refund or order status.");
        }
        refund.setRefundStatus(refundStatus);
        List<Refund> refunds = refundService.findByCriteria(refund);
        Float amount = order.getAmount();
        for (Refund r : refunds) {
            amount -= r.getAmount();
        }

        ModelAndView view = new ModelAndView("payment/_refundDialog");
        view.addObject("domain", "Refund");
        view.addObject("orderId", orderId);
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("0.00");
        view.addObject("orderAmount", myformat.format(amount));
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

        // retrieve order and associated customer, set refund recipient to be
        // the same with customer for now
        Refund refund = createRefund(order);
        RefundStatus refundStatus = null;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties.REFUND_STATUS_INITIATED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find refund or order status.");
        }
        refund.setRefundStatus(refundStatus);
        List<Refund> refunds = refundService.findByCriteria(refund);
        if(refunds != null && refunds.size() > 0) {
            for (Refund r : refunds) {
                r.setAmount(amount);
                r.setRemark(remark);
                try {
                    refundService.update(r);
                } catch (MissingRequiredFieldException e) {
                    e.printStackTrace();
                } catch (NotUniqueException e) {
                    e.printStackTrace();
                }
            }
        }else{
            refund.setAmount(amount);
            refund.setRemark(remark);
            try {
                refundService.create(refund);
            } catch (MissingRequiredFieldException e) {
                e.printStackTrace();
                throw new BadRequestException("400", e.getMessage());
            } catch (NotUniqueException e) {
                e.printStackTrace();
                throw new BadRequestException("400", e.getMessage());
            }
        }
        String domain = messageSource.getMessage("refund.label", null, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, amount + " " + remark}, locale);
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

