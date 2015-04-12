package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.CurrencyService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderAmount;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderCurrency;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by linly on 3/11/2015.
 */
@Controller
@RequestMapping("/count")
public class CountController {

    private static Logger logger = LoggerFactory.getLogger(CountController.class);

    @ModelAttribute("controller")
    public String controller() {
        return "count";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Count";
    }

    @Autowired
    private OrderService orderService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/currency"}, method = RequestMethod.GET)
    public String countByCurrency(Model model) {
        model.addAttribute("action", "currency");
        return "main";
    }

    @RequestMapping(value = {"/site"}, method = RequestMethod.GET)
    public String countBySite(Model model) {
        model.addAttribute("action", "site");
        return "main";
    }

    @RequestMapping(value = "/countByCurrency", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String countByCurrency() {

        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.##");

        // find all site, and get count based on site
        Site site = new Site();
        //site.setMerchant(currentUser.getMerchant());

        List<Currency> currencies = currencyService.getAll();
        List<DataTablesOrderCurrency> amounts = new ArrayList<>();
        for (Currency c : currencies) {
            //
            DataTablesOrderCurrency amount = new DataTablesOrderCurrency();
            amount.setCurrencyId(c.getId());
            amount.setCurrencyName(c.getName());

            //
            Order orderCriteria = new Order();
            orderCriteria.setCurrency(c);
            Long count = orderService.countByCriteria(orderCriteria);
            amount.setOrderCount(count);
            List<Order> orders = orderService.findByCriteria(orderCriteria);
            Double sum = 0.0;
            for (Order order : orders) {
                sum += order.getAmount();
            }
            amount.setOrderAmount(Double.valueOf(decimalFormat.format(sum)));
            amounts.add(amount);
        }

        DataTablesResultSet<DataTablesOrderCurrency> result = new DataTablesResultSet<>();
        result.setData(amounts);
        result.setRecordsTotal(currencies.size());
        result.setRecordsFiltered(currencies.size());

        return JsonUtil.toJson(result);
    }



    @RequestMapping(value = "/countBySite", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String countBySite() {

        /*
        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }

        */


        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.##");

        // find all site, and get count based on site
        Site site = new Site();
        //site.setMerchant(currentUser.getMerchant());

        List<Site> sites = siteService.findByCriteria(site);
        List<DataTablesOrderAmount> amounts = new ArrayList<>();
        for (Site s : sites) {
            DataTablesOrderAmount amount = new DataTablesOrderAmount();
            amount.setSiteId(s.getId());
            amount.setSiteIdentity(s.getIdentity());
            amount.setSiteName(s.getName());
            Order orderCriteria = new Order();
            orderCriteria.setSite(s);
            List<Order> orders = orderService.findByCriteria(orderCriteria);
            Double sum = 0.0;
            int count = 0;
            for (Order order : orders) {
                sum += order.getAmount();
                count += 1;
            }
            amount.setOrderAmount(Double.valueOf(decimalFormat.format(sum)));
            amount.setOrderCount(count);
            amounts.add(amount);

            /*
            DataTablesOrderCount count = new DataTablesOrderCount();
            count.setSiteId(s.getId());
            count.setSiteIdentity(s.getIdentity());
            count.setSiteName(s.getName());
            Order orderCriteria = new Order();
            orderCriteria.setSite(s);
            count.setOrderCount(orderService.countByCriteria(orderCriteria));
            counts.add(count);
            */
        }

        DataTablesResultSet<DataTablesOrderAmount> result = new DataTablesResultSet<>();
        result.setData(amounts);
        result.setRecordsTotal(sites.size());
        result.setRecordsFiltered(sites.size());

        return JsonUtil.toJson(result);
    }

}
