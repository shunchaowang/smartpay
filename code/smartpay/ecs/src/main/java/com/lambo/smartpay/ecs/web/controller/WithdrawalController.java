package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.WithdrawalCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesPayment;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesWithdrawal;
import com.lambo.smartpay.ecs.web.vo.table.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private UserService userService;
    private List<Payment> paymentList;

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

    @RequestMapping(value = {"/request"}, method = RequestMethod.GET)
    public String request(Model model) {
        model.addAttribute("_view", "withdrawal/search");
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
        Withdrawal WithdrawalCriteria = new Withdrawal();
        WithdrawalCriteria.setMerchant(merchant);

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("_view", "withdrawal/request");
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
        if(withdrawalId !=null && withdrawalId !="" ){
            try {
                Withdrawal w = withdrawalService.get(Long.parseLong(withdrawalId));
                Set<Payment> paymentSet = w.getPayments();
                Iterator<Payment> it = paymentSet.iterator();
                while (it.hasNext()) {
                    Payment payment = it.next();
                    DataTablesPayment tablesPayment = new DataTablesPayment(payment);
                    dataTablesPayments.add(tablesPayment);
                }
                recordsTotal = Long.parseLong(Integer.valueOf(paymentSet.size()).toString());
                recordsFiltered = recordsTotal;
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }else {
            SecurityUser securityUser = UserResource.getCurrentUser();
            if (securityUser == null) {
                throw new BadRequestException("400", "User is null.");
            }
            Merchant merchant = securityUser.getMerchant();
            Date beginning = merchant.getCreatedTime();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE, -9);
            Date ending = calendar.getTime();

            Order orderCriteria = new Order();
            Site siteCriteria = new Site();
            siteCriteria.setMerchant(merchant);
            orderCriteria.setSite(siteCriteria);
            Payment paymentCriteria = new Payment();
            paymentCriteria.setOrder(orderCriteria);
            paymentCriteria.setWithdrawal(null);
            paymentCriteria.setActive(true);
            Set<PaymentStatus> paymentStatusSet = new HashSet();
            try {
                PaymentStatus paymentStatus = paymentStatusService.findByCode(ResourceProperties.PAYMENT_STATUS_APPROVED_CODE);
                paymentStatusSet.add(paymentStatus);
                paymentStatus = paymentStatusService.findByCode(ResourceProperties.PAYMENT_STATUS_CLAIM_SUCCESSED_CODE);
                paymentStatusSet.add(paymentStatus);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new IntervalServerException("500", "Cannot find status.");
            }

            paymentList = paymentService.findByWithdrawalCriteria(paymentCriteria, paymentStatusSet, beginning, ending);
            // count total and filtered
            recordsTotal = Long.parseLong(Integer.valueOf(paymentList.size()).toString());
            recordsFiltered = recordsTotal;

            if (paymentList == null || recordsTotal == null || recordsFiltered == null) {
                throw new RemoteAjaxException("500", "Internal Server Error.");
            }
            for (Payment payment : paymentList) {
                DataTablesPayment tablesPayment = new DataTablesPayment(payment);
                dataTablesPayments.add(tablesPayment);
            }
        }
        resultSet.setData(dataTablesPayments);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    private WithdrawalCommand createithdrawalCommond(List<Payment> paymentList, Merchant merchant){
        WithdrawalCommand withdrawalCommand = new WithdrawalCommand();
        Float totalAmount = Float.parseFloat("0.00");
        Float totalFee = Float.parseFloat("0.00");
        NumberFormat nf= NumberFormat.getNumberInstance() ;
        nf.setMaximumFractionDigits(3);
        Date beginning = new Date();
        Date ending  = merchant.getCreatedTime();
        for (Payment payment : paymentList) {
            if(beginning.compareTo(payment.getCreatedTime()) > 0)
                beginning = payment.getCreatedTime();
            if (payment.getCreatedTime().compareTo(ending) > 0)
                ending = payment.getCreatedTime();
            totalAmount += payment.getAmount();
            totalFee += payment.getFee();
            Float refundAmt = Float.parseFloat("0.00");
            Float refundFee = Float.parseFloat("0.00");
            if (payment.getOrder().getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_REFUNDED_CODE)) {
                Set st = payment.getOrder().getRefunds();
                for (Iterator it = st.iterator(); it.hasNext(); ) {
                    refundAmt += ((Refund) it.next()).getAmount();
                }
                if (payment.getOrder().getAmount() - refundAmt < 0) refundAmt = payment.getOrder().getAmount();
                    refundAmt = refundAmt * (payment.getAmount() + payment.getFee()) / payment.getOrder().getAmount();
                if (merchant.getReturnFee().getFeeType().getCode().equals(ResourceProperties.MERCHANT_TYPE_STATIC_CODE)) {
                    refundFee += merchant.getReturnFee().getValue();
                } else {
                    refundFee += refundAmt * merchant.getReturnFee().getValue() / 10 * 10;
                }
                totalAmount -= refundAmt;
                refundAmt = refundAmt - refundFee;
                totalFee += refundFee;
            }
        }
        withdrawalCommand.setMerchant(merchant);
        withdrawalCommand.setBalance(Double.parseDouble(nf.format(totalAmount)));
        withdrawalCommand.setAmount(Double.parseDouble(nf.format(0.9 * totalAmount)));
        withdrawalCommand.setTotalFee(totalFee);
        withdrawalCommand.setSecurityDeposit(Double.parseDouble(nf.format(0.1 * totalAmount)));
        withdrawalCommand.setSecurityRate(Float.parseFloat("0.10"));
        withdrawalCommand.setDateStart(beginning);
        withdrawalCommand.setDateEnd(ending);
        withdrawalCommand.setDateRange(beginning + " ~ " + ending);
        SecurityUser securityUser = UserResource.getCurrentUser();
        withdrawalCommand.setRequester(securityUser.getUsername());
        return withdrawalCommand;
    }

    private Withdrawal createithdrawal(WithdrawalCommand withdrawalCommand){
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setDateEnd(withdrawalCommand.getDateEnd());
        withdrawal.setUpdatedTime(new Date());
        withdrawal.setCreatedTime(withdrawalCommand.getCreatedTime());
        withdrawal.setDateStart(withdrawalCommand.getDateStart());
        withdrawal.setActive(true);
        withdrawal.setAmount(withdrawalCommand.getAmount());
        withdrawal.setBalance(withdrawalCommand.getBalance());
        withdrawal.setMerchant(withdrawalCommand.getMerchant());
        withdrawal.setPayments(withdrawalCommand.getPayments());
        withdrawal.setSecurityDeposit(withdrawalCommand.getSecurityDeposit());
        withdrawal.setSecurityRate(withdrawalCommand.getSecurityRate());
        withdrawal.setSecurityWithdrawn(false);
        return withdrawal;
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        if (paymentList == null || paymentList.size() == 0) {
            throw new BadRequestException("400", "withdrawal is null.");
        }
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User has not logged in.");
        }
        WithdrawalCommand withdrawalCommand = createithdrawalCommond(paymentList, securityUser.getMerchant());
        model.addAttribute("_view", "withdrawal/create");
        model.addAttribute("withdrawalCommond", withdrawalCommand);
        return "main";
    }

    // should not have ResponseBody if you want to redirect
    @RequestMapping(value = {"/saveWithdrawal"}, method = RequestMethod.POST/*,
            produces = "application/json;charset=UTF-8"*/)
    //@ResponseBody
    public String saveWithdrawal(HttpServletRequest request,RedirectAttributes attributes) {
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User has not logged in.");
        }
        WithdrawalCommand withdrawalCommand = createithdrawalCommond(paymentList, securityUser.getMerchant());
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String remark = request.getParameter("remark");
        Withdrawal ws = createithdrawal(withdrawalCommand);
        ws.setRequestedBy(securityUser.getId());
        ws.setMerchant(withdrawalCommand.getMerchant());
        ws.setSecurityWithdrawn(false);
        ws.setActive(true);
        ws.setUpdatedTime(new Date());
        try {
            ws = withdrawalService.requestWithdrawal(ws);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "missingRequiredFieldException");
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "notUniqueException");
        }
        for(Payment p:paymentList) {
            paymentService.withdrawPayment(p, ws);
        }
        String fieldLabel = messageSource.getMessage("withdrawal.label", null, locale);
        attributes.addFlashAttribute("message",
                messageSource.getMessage("created.message",
                        new String[]{fieldLabel, String.valueOf(ws.getAmount()) }, locale));
        String domain = messageSource.getMessage("withdrawal.label", null, locale);
        String successfulMessage = messageSource.getMessage("saved.message",
                new String[]{domain, ws.getAmount() + " " + remark}, locale);
        response.setStatus("successful");
        response.setMessage(successfulMessage);
        return "redirect:/withdrawal/index/all";
    }

    @RequestMapping(value = "/cancelWithdrawal", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String cancelWithdrawal(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("withdrawal.label", null, locale);
        try {
            Withdrawal w = withdrawalService.get(id);
//            if (!w.getWithdrawalStatus().getCode().equals(ResourceProperties.WITHDRAWAL_STATUS_DECLINED_CODE) ) {
//                throw new BadRequestException("400", "can not cancel.");
//            }
            Set<Payment> paymentSet = w.getPayments();
            Iterator<Payment> it = paymentSet.iterator();
            while (it.hasNext()) {
                Payment payment = it.next();
                paymentService.unwithdrawPayment(payment);
            }
            withdrawalService.delete(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String message = messageSource
                    .getMessage("not.deleted.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }
        String message = messageSource
                .getMessage("deleted.message", new String[]{label, id.toString()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/securityWithdrawn", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String securityWithdrawn(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("securityWithdrawn.label", null, locale);
        try {
            Withdrawal w = withdrawalService.get(id);
            withdrawalService.requestDepositWithdrawal(w);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String message = messageSource
                    .getMessage("not.withdrawn.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }catch (NotUniqueException e){
            e.printStackTrace();
            String message = messageSource
                    .getMessage("not.withdrawn.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }catch(MissingRequiredFieldException e){
            e.printStackTrace();
            String message = messageSource
                    .getMessage("not.withdrawn.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }
        String message = messageSource
                .getMessage("withdrawn.message", new String[]{label, id.toString()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

}
