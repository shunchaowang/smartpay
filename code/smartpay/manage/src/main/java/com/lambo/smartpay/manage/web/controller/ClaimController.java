package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Claim;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.service.ClaimService;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesClaim;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index( Model model) {
        model.addAttribute("_view", "claim/indexAll");
        return "main";
    }
    @RequestMapping(value = {"/audit"}, method = RequestMethod.GET)
    public String claimAudit( Model model) {
        model.addAttribute("_view", "claim/indexResolved");
        return "main";
    }

    /**
     * Query all json data.
     * @param request
     * @return payment data of the domain
     */
    @RequestMapping(value = {"/list/Resolved"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listResolved(HttpServletRequest request) {
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(ResourceProperties.PAYMENT_STATUS_CLAIM_RESOLVED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        DataTablesParams params = new DataTablesParams(request);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setPaymentStatus(paymentStatus);

        List<Payment> payments = paymentService.findByCriteria(params.getSearch(),
                Integer.valueOf(params.getOffset()), Integer.valueOf(params.getMax()),
                params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        Long recordsTotal = paymentService.countAll();
        Long recordsFiltered = paymentService.countByCriteria(params.getSearch());

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

    /**
     * Query all json data.
     *
     * @param request
     * @return payment data of the domain
     */
    @RequestMapping(value = {"/list/Approved"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listApproved(HttpServletRequest request) {
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(ResourceProperties.PAYMENT_STATUS_APPROVED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        DataTablesParams params = new DataTablesParams(request);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setPaymentStatus(paymentStatus);

        List<Payment> payments = paymentService.findByCriteria(params.getSearch(),
                Integer.valueOf(params.getOffset()), Integer.valueOf(params.getMax()),
                params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        Long recordsTotal = paymentService.countAll();
        Long recordsFiltered = paymentService.countByCriteria(params.getSearch());

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

    /**
     * Query all json data.
     *
     * @param request
     * @return payment data of the domain
     */
    @RequestMapping(value = {"/list/Process"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listProcess(HttpServletRequest request) {
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(ResourceProperties.PAYMENT_STATUS_CLAIM_IN_PROCESS_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        DataTablesParams params = new DataTablesParams(request);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setPaymentStatus(paymentStatus);

        List<Payment> payments = paymentService.findByCriteria(params.getSearch(),
                Integer.valueOf(params.getOffset()), Integer.valueOf(params.getMax()),
                params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        Long recordsTotal = paymentService.countAll();
        Long recordsFiltered = paymentService.countByCriteria(params.getSearch());

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
            throw new BadRequestException("400", "Payment id is blank.");
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
        Long paymentId = Long.valueOf(request.getParameter("paymentId"));
        String remark = request.getParameter("remark");
        Payment payment = null;
        try {
            payment = paymentService.get(paymentId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Cannot find payment " + paymentId);
        }


        Claim claim = new Claim();
        claim.setPayment(payment);
        claim.setRemark(remark);
        if (request.getFileNames() != null && request.getFileNames().hasNext()) {
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            logger.debug("file: " + file.getOriginalFilename());
            byte[] fileContent = null;
            try {
                fileContent = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Cannot ready file.");
            }
            claim.setAttachment(fileContent);
        }

        try {
            claim = claimService.create(claim);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        // set payment status to be claim in pending
        payment = paymentService.initiatePaymentClaim(payment);
        String domain = messageSource.getMessage("PaymentRefused.label", null, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, payment.getBankTransactionNumber()}, locale);
        response.setStatus("successful");
        response.setMessage(successfulMessage);

        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = {"/showClaim"}, method = RequestMethod.GET)
    public ModelAndView showClaim(HttpServletRequest request) {

        String paymentId = request.getParameter("paymentId");

        if (StringUtils.isBlank(paymentId)) {
            throw new BadRequestException("400", "Payment id is blank.");
        }

        ModelAndView view = new ModelAndView("claim/_showClaimDialog");
        view.addObject("paymentId", paymentId);
        return view;
    }

    /**
     * Query all json data.
     *
     * @param request
     * @return payment data of the domain
     */
    @RequestMapping(value = "/listClaim", method = RequestMethod.GET)
    public
    @ResponseBody
    String listClaim(HttpServletRequest request) {

        if (request.getParameter("paymentId") == null) {
            throw new BadRequestException("400", "Payment id is null.");
        }
        Long paymentId = Long.valueOf(request.getParameter("paymentId"));
        Payment payment = null;
        try {
            payment = paymentService.get(paymentId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Payment is null.");
        }
        Claim claimCriteria = new Claim();
        claimCriteria.setPayment(payment);

        List<Claim> claims = claimService.findByCriteria(claimCriteria, "createdTime",
                ResourceProperties.JpaOrderDir.DESC);

        Long recordsTotal = claimService.countByCriteria(claimCriteria);
        Long recordsFiltered = claimService.countByCriteria(claimCriteria);

        if (claims == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesClaim> dataTablesClaims = new ArrayList<>();
        for (Claim c : claims) {
            DataTablesClaim claim = new DataTablesClaim(c);
            dataTablesClaims.add(claim);
        }

        DataTablesResultSet<DataTablesClaim> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesClaims);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = {"/downloadClaim/{id}"}, method = RequestMethod.GET)
    public String downloadClaim(@PathVariable("id") Long id, HttpServletResponse response) {

        if (id == null) {
            throw new BadRequestException("400", "Claim id is blank.");
        }

        Claim claim = null;
        try {
            claim = claimService.get(Long.valueOf(id));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        if (claim.getAttachment() == null || claim.getAttachment().length <= 0) {
            throw new BadRequestException("400", "Claim does not have an attachment");
        }

        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            OutputStream outputStream = response.getOutputStream();
            FileCopyUtils.copy(claim.getAttachment(), outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        return null;
    }

    @RequestMapping(value = {"/auditClaim"}, method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String auditClaim(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        Long paymentId = Long.valueOf(request.getParameter("paymentId"));

        Payment payment = null;
        try {
            payment = paymentService.get(paymentId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Cannot find payment " + paymentId);
        }

        // set payment status to be claim in pending
        payment = paymentService.approvePaymentClaim(payment);
        String domain = messageSource.getMessage("PaymentRefused.label", null, locale);
        String successfulMessage = messageSource.getMessage("audit.message",
                new String[]{domain, payment.getBankTransactionNumber()}, locale);
        response.setStatus("successful");
        response.setMessage(successfulMessage);

        return JsonUtil.toJson(response);
    }
}
