package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
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
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesRefund;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = {"/index{domain}"}, method = RequestMethod.GET)
    public String index(@PathVariable("domain") String domain, Model model) {
        if (domain.equals("All")) {
            model.addAttribute("action", "indexAll");
        } else if (domain.equals("Initiated")) {
            model.addAttribute("domain", "InitiatedRefund");
            model.addAttribute("action", "indexInitiated");
        } else if (domain.equals("Approved")) {
            model.addAttribute("domain", "ApprovedRefund");
            model.addAttribute("action", "indexApproved");
        } else {
            throw new BadRequestException("400", "Domain does not exist.");
        }
        return "main";
    }

    @RequestMapping(value = {"/list{domain}"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(@PathVariable("domain") String domain, HttpServletRequest request) {

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

        List<Refund> refunds = null;
        Long recordsTotal = null;
        Long recordsFiltered = null;
        if (domain.equals("All")) {
            refunds = refundService.findByCriteria(params.getSearch(),
                    start, length, params.getOrder(),
                    ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
            recordsTotal = refundService.countAll();
            recordsFiltered = refundService.countByCriteria(params.getSearch());
        } else if (domain.equals("Initiated")) {
            RefundStatus status = null;
            try {
                status = refundStatusService.findByCode(ResourceProperties
                        .REFUND_STATUS_INITIATED_CODE);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Refund status doesn't exist.");
            }
            Refund refundCriteria = new Refund();
            refundCriteria.setRefundStatus(status);
            refunds = refundService.findByCriteria(refundCriteria, params.getSearch(),
                    start, length, params.getOrder(),
                    ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
            recordsTotal = refundService.countByCriteria(refundCriteria);
            recordsFiltered = refundService.countByCriteria(refundCriteria, params.getSearch());
        } else if (domain.equals("Approved")) {
            RefundStatus status = null;
            try {
                status = refundStatusService.findByCode(ResourceProperties
                        .REFUND_STATUS_APPROVED_CODE);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "Refund status doesn't exist.");
            }
            Refund refundCriteria = new Refund();
            refundCriteria.setRefundStatus(status);
            refunds = refundService.findByCriteria(refundCriteria, params.getSearch(),
                    start, length, params.getOrder(),
                    ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
            recordsTotal = refundService.countByCriteria(refundCriteria);
            recordsFiltered = refundService.countByCriteria(refundCriteria, params.getSearch());
        } else {
            throw new BadRequestException("400", "Domain does not exist.");
        }

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

    /**
     * ajax calls to audit a refund by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/approveRefund", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String approveRefund(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Refund refund = null;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Refund.label", null, locale);
        try {
            refund = refundService.approveRefund(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notApprovedMessage = messageSource.getMessage("not.audit.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notApprovedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String approvedMessage = messageSource.getMessage("audit.message",
                new String[]{label, id.toString()}, locale);
        response.setMessage(approvedMessage);
        return JsonUtil.toJson(response);
    }

}
