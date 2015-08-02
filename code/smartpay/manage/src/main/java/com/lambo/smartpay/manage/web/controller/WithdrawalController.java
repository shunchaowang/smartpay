package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesWithdrawal;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private UserService userService;
    private Withdrawal withdrawal;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {return "withdrawal";}

    @ModelAttribute("domain")
    public String domain() {return "Withdrawal";}

    @RequestMapping(value = {"/audit"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "withdrawal/indexAll");
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
            try {
                tmp_withdrawal.setRequester(userService.get(w.getRequestedBy()).getUsername());
                tmp_withdrawal.setAuditer(userService.get(w.getAuditedBy()).getUsername());
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
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

}
