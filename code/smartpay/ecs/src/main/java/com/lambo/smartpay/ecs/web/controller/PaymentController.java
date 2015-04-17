package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.PaymentCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
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
import java.util.ArrayList;
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
        return paymentStatusService.getAll();
    }

    @RequestMapping(value = {"", "/index", "/"}, method = RequestMethod.GET)
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

        List<Payment> payments = paymentService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));
        // count total and filtered
        Long recordsTotal = paymentService.countAll();
        Long recordsFiltered = paymentService.countByCriteria(search);

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

        Payment payment = null;
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


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        Payment payment = null;
        try {
            payment = paymentService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }

        PaymentCommand paymentCommand = createPaymentCommand(payment);

        model.addAttribute("paymentCommand", paymentCommand);

        model.addAttribute("action", "edit");
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

}

