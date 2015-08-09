package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.CurrencyService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderCount;
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
        List<Order> orders = orderService.findByCriteria(orderCriteria, beginning, ending);
        DataTablesResultSet<DataTablesOrderCount> result = createOrderList(orders, sites);
        return JsonUtil.toJson(result);
    }

    private DataTablesResultSet<DataTablesOrderCount> createOrderList(List<Order> orders, List<Site> sites){
        List<DataTablesOrderCount> counts = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.###");
        for (Site s : sites) {
            DataTablesOrderCount count = new DataTablesOrderCount();
            count.setSiteId(s.getId());
            count.setSiteIdentity(s.getIdentity());
            count.setSiteName(s.getName());
            Long orderCount = Long.parseLong("0");
            Long paidCount = Long.parseLong("0");
            Double paidAmount = Double.parseDouble("0.00");
            Long refundCount = Long.parseLong("0");
            Double refundAmount = Double.parseDouble("0.00");
            Long refuseCount = Long.parseLong("0");
            Double refuseAmount = Double.parseDouble("0.00");
            for (Order order : orders) {
                if(order.getSite().getId().equals(s.getId())){
                    orderCount ++;
                    if(order.getOrderStatus().getCode().equals("401")
                            || order.getOrderStatus().getCode().equals("501")
                            || order.getOrderStatus().getCode().equals("502")
                            || order.getOrderStatus().getCode().equals("503")
                            || order.getOrderStatus().getCode().equals("403")
                            || order.getOrderStatus().getCode().equals("504")) {
                        paidCount ++;
                        Iterator<Payment> paymentIterator = order.getPayments().iterator();
                        Double paymentAmount = Double.parseDouble("0.00");
                        boolean refundFlag = true;
                        while(paymentIterator.hasNext()){
                            Payment payment = paymentIterator.next();
                            if(!payment.getPaymentStatus().getCode().equals("501")) {
                                paidAmount += payment.getAmount();
                                paymentAmount += payment.getAmount() + payment.getFee();
                            }
                            if(payment.getPaymentStatus().getCode().equals("502")){
                                refuseCount ++;
                                refuseAmount += payment.getAmount();
                                refundFlag = false;
                            }
                        }
                        if(refundFlag){
                            if(order.getOrderStatus().getCode().equals("504")){
                                Iterator<Refund> refundIterator = order.getRefunds().iterator();
                                Double refundAmt = Double.parseDouble("0.00");
                                while (refundIterator.hasNext()){
                                    Refund refund = refundIterator.next();
                                    if(refund.getRefundStatus().getCode().equals("502")){
                                        refundCount ++;
                                        refundAmt += refund.getAmount();
                                    }
                                }
                                refundAmt = refundAmt * paymentAmount / order.getAmount();
                                refundAmount += refundAmt;
                            }
                        }
                    }
                }
            }
            count.setOrderCount(orderCount);
            count.setPaidCount(paidCount);
            count.setPaidAmount(Double.valueOf(decimalFormat.format(paidAmount)));
            count.setRefuseCount(refuseCount);
            count.setRefuseAmount(Double.valueOf(decimalFormat.format(refuseAmount)));
            count.setRefundCount(refundCount);
            count.setRefundAmount(Double.valueOf(decimalFormat.format(refundAmount)));
            counts.add(count);
        }
        DataTablesResultSet<DataTablesOrderCount> dataTablesOrderCountResult= new DataTablesResultSet<>();
        dataTablesOrderCountResult.setData(counts);
        dataTablesOrderCountResult.setRecordsTotal(counts.size());
        dataTablesOrderCountResult.setRecordsFiltered(counts.size());
        return dataTablesOrderCountResult;
    }
}
