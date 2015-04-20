package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.PaymentCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Created by linly on 3/27/2015.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private PaymentStatusService paymentStatusService;

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
        logger.debug("payment status: " + paymentStatusService.countAll());
        return paymentStatusService.getAll();
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index() {
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

        //
        String codeString = "";
        List<Payment> payments = null;
        Long recordsTotal;
        Long recordsFiltered;

        if (codeString.equals("")) {

            payments = paymentService.findByCriteria(search, start,
                    length, order, ResourceProperties.JpaOrderDir.valueOf(orderDir));
            // count total and filtered
            recordsTotal = paymentService.countAll();
            recordsFiltered = paymentService.countByCriteria(search);

        } else {
            // normal payment status
            Payment paymentCriteria = new Payment();
            PaymentStatus status = null;
            try {
                status = paymentStatusService.findByCode(codeString);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("Cannot find PaymentStatus with Code", codeString);
            }

            paymentCriteria.setPaymentStatus(status);
            payments = paymentService.findByCriteria(paymentCriteria, search, start, length, order,
                    ResourceProperties.JpaOrderDir.valueOf(orderDir));
            // count total and filtered
            recordsTotal = paymentService.countByCriteria(paymentCriteria);
            recordsFiltered = paymentService.countByCriteria(paymentCriteria, search);
        }

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

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Payment payment;
        try {
            payment = paymentService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        PaymentCommand paymentCommand = createPaymentCommand(payment);
        model.addAttribute("paymentCommand", paymentCommand);

        model.addAttribute("action", "show");
        return "main";
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
        String bankTransactionNumber = request.getParameter("bankTransactionNumber");
        String paymentStatus = request.getParameter("paymentStatus");
        String site = request.getParameter("site");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        Payment paymentCriteria = new Payment();
        if (!StringUtils.isBlank(id)) {
            paymentCriteria.setId(Long.valueOf(id));
        }
        if (!StringUtils.isBlank(bankTransactionNumber)) {
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
        if (!StringUtils.isBlank(site)) {
            Site siteCriteria = null;
            try {
                siteCriteria = siteService.get(Long.valueOf(site));
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            Order orderCriteria = new Order();
            orderCriteria.setSite(siteCriteria);
            paymentCriteria.setOrder(orderCriteria);
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

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search,
                start, length,
                order, ResourceProperties.JpaOrderDir.valueOf(orderDir),
                beginning, ending);

        // count total records
        Long recordsTotal = paymentService.countByCriteria(paymentCriteria);
        // count records filtered
        Long recordsFiltered = paymentService.countByCriteria(paymentCriteria, search);

        if (payments == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesPayment> dataTablesPayments = new ArrayList<>();

        for (Payment o : payments) {
            DataTablesPayment tablesPayment = new DataTablesPayment(o);
            dataTablesPayments.add(tablesPayment);
        }

        DataTablesResultSet<DataTablesPayment> result = new DataTablesResultSet<>();
        result.setData(dataTablesPayments);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());
        return JsonUtil.toJson(result);
    }

    private PaymentCommand createPaymentCommand(Payment payment) {
        PaymentCommand PaymentCommand = new PaymentCommand();

        if (payment.getId() != null) {
            PaymentCommand.setId(payment.getId());
        }

        //
        PaymentCommand.setAmount(payment.getAmount());
        PaymentCommand.setCreatedTime(payment.getCreatedTime().toString());
        PaymentCommand.setBankName(payment.getBankName());
        PaymentCommand.setBankTransactionNumber(payment.getBankTransactionNumber());
        PaymentCommand.setBankReturnCode(payment.getBankReturnCode());
        PaymentCommand.setPaymentStatusId(payment.getPaymentStatus().getId());
        PaymentCommand.setPaymentStatusName(payment.getPaymentStatus().getName());
        PaymentCommand.setPaymentTypeId(payment.getPaymentType().getId());
        PaymentCommand.setPaymentTypeName(payment.getPaymentType().getName());
        PaymentCommand.setOrderId(payment.getOrder().getId());
        PaymentCommand.setOrderNumber(payment.getOrder().getMerchantNumber());
        PaymentCommand.setCurrencyId(payment.getCurrency().getId());
        PaymentCommand.setCurrencyName(payment.getCurrency().getName());
        PaymentCommand.setMerchantId(payment.getOrder().getSite().getMerchant().getId());
        PaymentCommand.setMerchantName(payment.getOrder().getSite().getMerchant().getName());
        PaymentCommand.setMerchantNumber(payment.getOrder().getMerchantNumber());
        PaymentCommand.setSiteId(payment.getOrder().getSite().getId());
        PaymentCommand.setSiteName(payment.getOrder().getSite().getName());


        if (payment.getSuccessTime() != null) {
            PaymentCommand.setSuccessTime(payment.getSuccessTime().toString());
        }
        PaymentCommand.setRemark(payment.getRemark());
        PaymentCommand.setBillAddress1(payment.getBillAddress1());
        PaymentCommand.setBillAddress2(payment.getBillAddress2());
        PaymentCommand.setBillFirstName(payment.getBillFirstName());
        PaymentCommand.setBillLastName(payment.getBillLastName());
        PaymentCommand.setBillCity(payment.getBillCity());
        PaymentCommand.setBillState(payment.getBillState());
        PaymentCommand.setBillZipCode(payment.getBillZipCode());
        PaymentCommand.setBillCountry(payment.getBillCountry());

        return PaymentCommand;
    }
}


