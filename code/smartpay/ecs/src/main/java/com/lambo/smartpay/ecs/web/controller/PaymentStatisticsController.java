package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderCount;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesPaymentStatistic;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.*;
import java.util.*;

/**
 * Created by chensf on 2015/8/9.
 */
@Controller
@RequestMapping("/report")
public class PaymentStatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentStatisticsController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private UserService userService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private RefundStatusService refundStatusService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;

    @ModelAttribute("controller")
    public String controller() {
        return "report";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Report";
    }

    @RequestMapping(value = {"/paymentStatistics"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "report/paymentStatistics");
        return "main";
    }

    @RequestMapping(value = {"/summaryData"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String summaryData(HttpServletRequest request) {
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
        String timeBeginning = request.getParameter("timeBeginning");
        String timeEnding = request.getParameter("timeEnding");
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
        }
        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(securityUser.getMerchant());

        List<Site> sites = siteService.findByCriteria(site);
        Order orderCriteria = new Order();
        orderCriteria.setSite(site);
        Payment paymentCriteria = new Payment();
        paymentCriteria.setOrder(orderCriteria);

        List<Payment> payments = paymentService.findByCriteria(paymentCriteria, "", null, null, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir), beginning, ending);
//        Refund refundCriteria = new Refund();
//        refundCriteria.setOrder(orderCriteria);
//        try {
//            RefundStatus refundStatus = refundStatusService.findByCode(ResourceProperties.REFUND_STATUS_APPROVED_CODE);
//            refundCriteria.setRefundStatus(refundStatus);
//        } catch (NoSuchEntityException e) {
//            e.printStackTrace();
//        }
//        List<Refund> refunds = refundService.findByCriteria(refundCriteria, beginning, ending);
        DataTablesResultSet<DataTablesPaymentStatistic> result = createStatisticList(payments);
        return JsonUtil.toJson(result);
    }

    private DataTablesResultSet<DataTablesPaymentStatistic> createStatisticList(List<Payment> payments){
        List<DataTablesPaymentStatistic> PaymentStatistics = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.###");
        DataTablesPaymentStatistic ps = new DataTablesPaymentStatistic();
        Long paidCount = Long.parseLong("0");
        Double paidAmount = Double.parseDouble("0.00");
        Long refundCount = Long.parseLong("0");
        Double refundAmount = Double.parseDouble("0.00");
        Long refuseCount = Long.parseLong("0");
        Double refuseAmount = Double.parseDouble("0.00");
        for(Payment payment : payments){
            System.out.println("payment.getCreatedTime===" + payment.getCreatedTime());
            if(!payment.getCreatedTime().toString().substring(0, 7).equals(ps.getStatisticsDate())){
                if(ps.getStatisticsDate() !=null && ps.getStatisticsDate() !=""){
                    ps.setPaidCount(paidCount);
                    ps.setPaidAmount(Double.valueOf(decimalFormat.format(paidAmount)));
                    ps.setRefundCount(refundCount);
                    ps.setRefundAmount(Double.valueOf(decimalFormat.format(refundAmount)));
                    ps.setRefuseCount(refuseCount);
                    ps.setRefuseAmount(Double.valueOf(decimalFormat.format(refuseAmount)));
                    PaymentStatistics.add(ps);
                    System.out.println("ps.getStatisticsDate=1==" + ps.getStatisticsDate());
                }
                paidCount = Long.parseLong("0");
                paidAmount = Double.parseDouble("0.00");
                refundCount = Long.parseLong("0");
                refundAmount = Double.parseDouble("0.00");
                refuseCount = Long.parseLong("0");
                refuseAmount = Double.parseDouble("0.00");
                ps = new DataTablesPaymentStatistic();
                ps.setStatisticsDate(payment.getCreatedTime().toString().substring(0, 7));
            }
            String paymentStatusCode = payment.getPaymentStatus().getCode();
            if(!paymentStatusCode.equals(ResourceProperties.PAYMENT_STATUS_DECLINED_CODE)
                    &&!paymentStatusCode.equals(ResourceProperties.PAYMENT_STATUS_CLAIM_RESOLVED_CODE)){//PAID
                paidCount ++;
                paidAmount +=payment.getAmount();
                Order order = payment.getOrder();
                if(order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_REFUNDED_CODE)){
                    Iterator<Refund> refundIterator = order.getRefunds().iterator();
                    while (refundIterator.hasNext()){
                        Refund refund = refundIterator.next();
                        if(refund.getRefundStatus().getCode().equals(ResourceProperties.REFUND_STATUS_APPROVED_CODE)){
                            refundCount ++;
                            refundAmount += refund.getAmount();
                        }
                    }
                }
            }
            if(paymentStatusCode.equals(ResourceProperties.PAYMENT_STATUS_CLAIM_RESOLVED_CODE)){
                refuseCount ++;
                refuseAmount +=payment.getAmount();
            }
        }
        if(ps.getStatisticsDate() !=null && ps.getStatisticsDate() !=""){
            ps.setPaidCount(paidCount);
            ps.setPaidAmount(Double.valueOf(decimalFormat.format(paidAmount)));
            ps.setRefundCount(refundCount);
            ps.setRefundAmount(Double.valueOf(decimalFormat.format(refundAmount)));
            ps.setRefuseCount(refuseCount);
            ps.setRefuseAmount(Double.valueOf(decimalFormat.format(refuseAmount)));
            PaymentStatistics.add(ps);
            System.out.println("ps.getStatisticsDate=2==" + ps.getStatisticsDate());
        }
        DataTablesResultSet<DataTablesPaymentStatistic> dataTablesPaymentStatisticResult= new DataTablesResultSet<>();
        dataTablesPaymentStatisticResult.setData(PaymentStatistics);
        dataTablesPaymentStatisticResult.setRecordsTotal(PaymentStatistics.size());
        dataTablesPaymentStatisticResult.setRecordsFiltered(PaymentStatistics.size());
        return dataTablesPaymentStatisticResult;
    }
}
