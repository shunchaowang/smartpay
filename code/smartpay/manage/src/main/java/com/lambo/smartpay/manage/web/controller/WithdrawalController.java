package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.vo.WithdrawalCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesWithdrawal;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by chensf on 2015/7/31.
 */
@Controller
@RequestMapping("/withdrawal")
public class WithdrawalController {
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalController.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private WithdrawalStatusService withdrawalStatusService;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {return "withdrawal";}

    @ModelAttribute("domain")
    public String domain() {return "Withdrawal";}

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "withdrawal/indexAll");
        return "main";
    }

    @RequestMapping(value = {"/audit"}, method = RequestMethod.GET)
    public String audit(Model model) {
        model.addAttribute("_view", "withdrawal/audit");
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

        Withdrawal WithdrawalCriteria = new Withdrawal();
        WithdrawalStatus withdrawalStatus = null;
        try {
            withdrawalStatus = withdrawalStatusService.findByCode(ResourceProperties.WITHDRAWAL_STATUS_APPROVED_CODE);
            WithdrawalCriteria.setWithdrawalStatus(withdrawalStatus);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        List<Withdrawal> withdrawals = withdrawalService.findByCriteria(WithdrawalCriteria, params.getSearch(),
                start, length, params.getOrder(), ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = withdrawalService.countByCriteria(WithdrawalCriteria);
        Long recordsFiltered = withdrawalService.countByCriteria(WithdrawalCriteria, params.getSearch());
        if (withdrawals == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesWithdrawal> DataTablesWithdrawals = new ArrayList<>();
        for (Withdrawal w : withdrawals) {
            DataTablesWithdrawal tmp_withdrawal = new DataTablesWithdrawal(w);
            DataTablesWithdrawals.add(tmp_withdrawal);
        }

        DataTablesResultSet<DataTablesWithdrawal> resultSet = new DataTablesResultSet<>();
        resultSet.setData(DataTablesWithdrawals);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("withdrawalId", id);
        model.addAttribute("_view", "withdrawal/search");
        return "main";
    }

    @RequestMapping(value = {"/searchData"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchData(HttpServletRequest request) {
        List<DataTablesPayment> dataTablesPayments = new ArrayList<>();
        DataTablesResultSet<DataTablesPayment> resultSet = new DataTablesResultSet<>();
        Long recordsTotal = Long.parseLong("0") ;
        Long recordsFiltered = Long.parseLong("0") ;
        String withdrawalId = request.getParameter("withdrawalId");
        if (withdrawalId == null || withdrawalId == "") {
            throw new BadRequestException("400", "withdrawalId is null.");
        }
        try {
            Withdrawal w = withdrawalService.get(Long.parseLong(withdrawalId));
            Set paymentSet = w.getPayments();
            Iterator<Object> it = paymentSet.iterator();
            while (it.hasNext()) {
                Payment payment = (Payment)it.next();
                DataTablesPayment tablesPayment = new DataTablesPayment(payment);
                dataTablesPayments.add(tablesPayment);
            }
            recordsTotal = Long.parseLong(Integer.valueOf(paymentSet.size()).toString());
            recordsFiltered = recordsTotal;
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        resultSet.setData(dataTablesPayments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = {"/listAudit"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listAudit(HttpServletRequest request) {
        DataTablesParams params = new DataTablesParams(request);
        Integer start = Integer.valueOf(params.getOffset());
        Integer length = Integer.valueOf(params.getMax());
        if (start == null || length == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        Withdrawal withdrawalCriteria = new Withdrawal();
        Set<WithdrawalStatus> withdrawalStatuses = new HashSet<>();
        try {
            WithdrawalStatus withdrawalStatus = withdrawalStatusService.findByCode(ResourceProperties.WITHDRAWAL_STATUS_PENDING_CODE);
            withdrawalStatuses.add(withdrawalStatus);
            withdrawalStatus = withdrawalStatusService.findByCode(ResourceProperties.WITHDRAWAL_STATUS_DEPOSIT_PENDING_CODE);
            withdrawalStatuses.add(withdrawalStatus);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        withdrawalCriteria.setActive(true);
        List<Withdrawal> withdrawals = withdrawalService.findByAdvanceCriteria(withdrawalCriteria, withdrawalStatuses, null, null);
        Long recordsTotal = Long.parseLong("0");
        if(withdrawals !=null && withdrawals.size() > 0)
            recordsTotal = Long.parseLong(String.valueOf(withdrawals.size()));
        Long recordsFiltered = recordsTotal;
        if (withdrawals == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }
        List<DataTablesWithdrawal> DataTablesWithdrawals = new ArrayList<>();
        for (Withdrawal w : withdrawals) {
            DataTablesWithdrawal tmp_withdrawal = new DataTablesWithdrawal(w);
            DataTablesWithdrawals.add(tmp_withdrawal);
        }

        DataTablesResultSet<DataTablesWithdrawal> resultSet = new DataTablesResultSet<>();
        resultSet.setData(DataTablesWithdrawals);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = "/declineWithdrawal", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String declineWithdrawal(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("withdrawal.label", null, locale);
        try {
            Withdrawal w = withdrawalService.get(id);
            if (!w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_PENDING_CODE)
                   || !w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_DEPOSIT_PENDING_CODE) ) {
                throw new BadRequestException("400", "can not cancel.");
            }

            withdrawalService.declineWithdrawal(w);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        String message = messageSource
                .getMessage("decline.message", new String[]{label, id.toString()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/approveWithdrawal", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String approveWithdrawal(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("withdrawal.label", null, locale);
        try {
            Withdrawal w = withdrawalService.get(id);
            if (!w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_PENDING_CODE)
                    && !w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_DEPOSIT_PENDING_CODE) ) {
                throw new BadRequestException("400", "can not approve.");
            }
            if(w.getAmountAdjust() !=null && w.getAmountAdjust() > 0)
                w.setAmountApproved(w.getAmount() - w.getAmountAdjust());
            else
                w.setAmountApproved(w.getAmount());
            withdrawalService.approveWithdrawal(w);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        String message = messageSource
                .getMessage("approve.message", new String[]{label, id.toString()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/securityWithdrawal", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String securityWithdrawal(@RequestParam(value = "id") Long id) {
        if (id == null) {
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("withdrawal.label", null, locale);
        try {
            Withdrawal w = withdrawalService.get(id);
            if (!w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_DEPOSIT_PENDING_CODE)) {
                throw new BadRequestException("400", "can not cancel.");
            }
            withdrawalService.approvedepositWithdrawal(w);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
        }
        String message = messageSource
                .getMessage("approve.message", new String[]{label, id.toString()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {

        String withdrawalId = request.getParameter("withdrawalId");
        if (StringUtils.isBlank(withdrawalId)) {
            throw new BadRequestException("400", "withdrawalId id is blank.");
        }
        Long id = Long.valueOf(withdrawalId);
        Withdrawal withdrawal = null;
        try {
            withdrawal = withdrawalService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "withdrawalId " + id + " not found.");
        }

        WithdrawalCommand withdrawalCommand = new WithdrawalCommand(withdrawal);
        ModelAndView view = new ModelAndView("withdrawal/_editDialog");
        view.addObject("withdrawalCommand", withdrawalCommand);
        view.addObject("amount", withdrawalCommand.getAmount());
        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("withdrawal.label", null, locale);
        Withdrawal withdrawal = editWithdrawal(request);
        try {
            withdrawal = withdrawalService.update(withdrawal);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{label}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    private Withdrawal editWithdrawal(HttpServletRequest request) {

        Withdrawal withdrawal = null;
        try {
            withdrawal = withdrawalService.get(Long.valueOf(request.getParameter("withdrawalId")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        withdrawal.setAmountAdjust(Double.parseDouble(request.getParameter("adjustAmt")));
        withdrawal.setRemark(request.getParameter("remark"));
        return withdrawal;
    }
}
