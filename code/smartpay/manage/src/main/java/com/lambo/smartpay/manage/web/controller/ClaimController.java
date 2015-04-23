package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.service.ClaimService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 4/20/2015.
 */
@Controller
@RequestMapping("/claim")
public class ClaimController {

    private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);

    @Autowired
    private ClaimService claimService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "claim";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "PaymentRefused";
    }

    /**
     * Index page for claims.
     *
     * @param domain can be Approved, Process, Resolved
     * @param model
     * @return index page of the domain payment
     */
    @RequestMapping(value = {"/index{domain}"}, method = RequestMethod.GET)
    public String index(@PathVariable("domain") String domain, Model model) {

        String action = "";
        if (domain.equals("Approved")) {
            action = "indexApproved";
        } else if (domain.equals("Process")) {
            action = "indexProcess";
        } else if (domain.equals("Resolved")) {
            action = "indexResolved";
        } else {
            throw new BadRequestException("400", "Bad request.");
        }

        model.addAttribute("action", action);
        return "main";
    }

    /**
     * Query all json data.
     *
     * @param domain  can be Approved, Process, Resolved
     * @param request
     * @return payment data of the domain
     */
    @RequestMapping(value = "/list{domain}", method = RequestMethod.GET)
    public
    @ResponseBody
    String list(@PathVariable("domain") String domain, HttpServletRequest request) {

        String paymentStatusCode = "";
        // only approved payment status can be initiated to be pending, this list
        // will show all approved payment and user can mark any of them to be claim pending

        if (domain.equals("Approved")) { // for indexApproved, only approved payment
            paymentStatusCode = ResourceProperties.PAYMENT_STATUS_APPROVED_CODE;
        } else if (domain.equals("Process")) { // for indexProcess, only payment status process
            paymentStatusCode = ResourceProperties.PAYMENT_STATUS_CLAIM_IN_PROCESS_CODE;
        } else if (domain.equals("Resolved")) { // for indexResolved, only payment status resolved
            paymentStatusCode = ResourceProperties.PAYMENT_STATUS_CLAIM_RESOLVED_CODE;
        } else {
            throw new BadRequestException("400", "Bad request.");
        }

        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(paymentStatusCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        DataTablesParams params = new DataTablesParams(request);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setPaymentStatus(paymentStatus);

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, params.getSearch(),
                Integer.valueOf(params.getOffset()), Integer.valueOf(params.getMax()),
                params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        Long recordsTotal = paymentService.countByCriteria(paymentCriteria);
        Long recordsFiltered = paymentService.countByCriteria(paymentCriteria, params.getSearch());

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

    @RequestMapping(value = {"/addClaim"}, method = RequestMethod.GET)
    public ModelAndView addClaim(HttpServletRequest request) {

        String paymentId = request.getParameter("paymentId");

        if (StringUtils.isBlank(paymentId)) {
            throw new BadRequestException("400", "Order id is blank.");
        }

        ModelAndView view = new ModelAndView("claim/_addClaimDialog");
        view.addObject("paymentId", paymentId);
        return view;
    }

    @RequestMapping(value = {"/addClaim"}, method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveClaim(MultipartHttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        logger.debug("paymentId: " + request.getParameter("paymentId"));
        logger.debug("remark: " + request.getParameter("remark"));
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        logger.debug("file: " + file.getOriginalFilename());

        return null;
    }
}
