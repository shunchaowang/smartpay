package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesMerchant;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.util.JsonUtil;
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
import javax.xml.crypto.Data;
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

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    /*
    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
            */

    @RequestMapping(value = "/list{domain}", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listDomain(@PathVariable("domain") String domain, HttpServletRequest request) {

        logger.debug("~~~~~~~~~ listDomain ~~~~~~~~~" + domain);

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

        /*
        if (domain.equals("FreezeList"))
            codeString = ResourceProperties.MERCHANT_STATUS_NORMAL_CODE;
        if (domain.equals("UnfreezeList"))
            codeString = ResourceProperties.MERCHANT_STATUS_FROZEN_CODE;
            */

        if (codeString.equals("")) {
            logger.debug("~~~~~~~~~~ payment list ~~~~~~~~~~" + "all codeString ！！！");

            payments = paymentService.findByCriteria(search, start,
                    length, order, ResourceProperties.JpaOrderDir.valueOf(orderDir));
            // count total and filtered
            recordsTotal = paymentService.countAll();
            recordsFiltered = paymentService.countByCriteria(search);

        } else {
            logger.debug("~~~~~~~~~~ payment list ~~~~~~~~~~" + "codeString = " + codeString);
            // normal payment status
            Payment paymentCriteria = new Payment();
            PaymentStatus status =null;
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
        for (Payment payment: payments) {
            DataTablesPayment tablesPayment = new DataTablesPayment(payment);
            dataTablesPayments.add(tablesPayment);
        }

        DataTablesResultSet<DataTablesPayment> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesPayments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }


/*
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        // parse sorting column
        String paymentIndex = request.getParameter("payment[0][column]");
        String payment = request.getParameter("columns[" + paymentIndex + "][name]");

        // parse sorting direction
        String paymentDir = StringUtils.upperCase(request.getParameter("payment[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || payment == null || paymentDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<Payment> payments = paymentService.findByCriteria(search, start, length,
                payment, ResourceProperties.JpaOrderDir.valueOf(paymentDir));

        // count total records
        Long recordsTotal = paymentService.countAll();
        // count records filtered
        Long recordsFiltered = paymentService.countByCriteria(search);

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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    /*
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("action", "search");
        model.addAttribute("sites", siteService.getAll());
        return "main";
    }
    */

    /*

    @RequestMapping(value = "/searchData", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String searchData(HttpServletRequest request) {

        // parse sorting column
        String paymentIndex = request.getParameter("payment[0][column]");
        String payment = request.getParameter("columns[" + paymentIndex + "][name]");

        // parse sorting direction
        String paymentDir = StringUtils.upperCase(request.getParameter("payment[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || payment == null || paymentDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        // parse my own query params
        String id = request.getParameter("id");
        String merchantNumber = request.getParameter("merchantNUmber");
        String paymentStatus = request.getParameter("paymentStatus");
        String site = request.getParameter("site");
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");

        Payment paymentCriteria = new Payment();
        if (!StringUtils.isBlank(id)) {
            paymentCriteria.setId(Long.valueOf(id));
        }
        if (!StringUtils.isBlank(merchantNumber)) {
            paymentCriteria.setMerchantNumber(merchantNumber);
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
            paymentCriteria.setSite(siteCriteria);
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

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, search, start, length,
                payment, ResourceProperties.JpaOrderDir.valueOf(paymentDir), beginning, ending);

        // count total records
        Long recordsTotal = paymentService.countAll();
        // count records filtered
        Long recordsFiltered = paymentService.countByCriteria(search);

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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }
      */
}


