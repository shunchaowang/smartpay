package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.CurrencyService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.vo.HomeCommand;
import com.lambo.smartpay.ecs.web.vo.PasswordCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderAmount;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesOrderCount;
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
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class HomeController {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

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

    @RequestMapping(value = {"/", "/index"})
    public String home(Model model) {
        //view.addObject("action", "index");
        // we need to retrieve all orders for the merchant
        // and calculate the count of the order and amount of the order
        // order summary based on currency
        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }
        Merchant merchant = currentUser.getMerchant();
        HomeCommand command = new HomeCommand();
        command.setMerchantId(merchant.getId());
        command.setMerchantName(merchant.getName());
        command.setMerchantIdentity(merchant.getIdentity());
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        Long siteCount = siteService.countByCriteria(siteCriteria);
        command.setSiteCount(siteCount);
        Order orderCriteria = new Order();
        Site site = new Site();
        site.setMerchant(merchant);
        orderCriteria.setSite(site);

        Long orderCount = orderService.countByCriteria(orderCriteria);
        command.setOrderCount(orderCount);

        List<Order> orders = orderService.findByCriteria(orderCriteria);
        Double amount = 0.0;
        for (Order order : orders) {
            amount += order.getAmount();
        }
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.##");
        command.setOrderAmount(Double.valueOf(decimalFormat.format(amount)));
        model.addAttribute("merchantCommand", command);

        return "main";
    }

    @RequestMapping(value = "/listOrderCount", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listOrderCount() {

        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }
        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(currentUser.getMerchant());

        List<Site> sites = siteService.findByCriteria(site);
        List<DataTablesOrderCount> counts = new ArrayList<>();
        for (Site s : sites) {
            DataTablesOrderCount count = new DataTablesOrderCount();
            count.setSiteId(s.getId());
            count.setSiteIdentity(s.getIdentity());
            count.setSiteName(s.getName());
            Order orderCriteria = new Order();
            orderCriteria.setSite(s);
            count.setOrderCount(orderService.countByCriteria(orderCriteria));
            counts.add(count);
        }

        DataTablesResultSet<DataTablesOrderCount> result = new DataTablesResultSet<>();
        result.setData(counts);
        result.setRecordsTotal(sites.size());
        result.setRecordsFiltered(sites.size());

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/listOrderAmount", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listOrderAmount() {

        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.##");

        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(currentUser.getMerchant());

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
            for (Order order : orders) {
                sum += order.getAmount();
            }
            amount.setOrderAmount(Double.valueOf(decimalFormat.format(sum)));
            amounts.add(amount);
        }

        DataTablesResultSet<DataTablesOrderAmount> result = new DataTablesResultSet<>();
        result.setData(amounts);
        result.setRecordsTotal(sites.size());
        result.setRecordsFiltered(sites.size());

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/listOrderCurrency", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listOrderCurrency() {

        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("###.##");

        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(currentUser.getMerchant());

        List<Currency> currencies = currencyService.getAll();
        List<DataTablesOrderCurrency> amounts = new ArrayList<>();
        for (Currency c : currencies) {
            DataTablesOrderCurrency amount = new DataTablesOrderCurrency();
            amount.setCurrencyId(c.getId());
            amount.setCurrencyName(c.getName());
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

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model) {

        PasswordCommand passwordCommand = new PasswordCommand();
        model.addAttribute("passwordCommand", passwordCommand);
        model.addAttribute("action", "changePassword");
        return "main";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("passwordCommand") PasswordCommand passwordCommand,
                                 Model model) {

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            return "403";
        }
        Locale locale = LocaleContextHolder.getLocale();

        if (!passwordCommand.getPassword().equals(passwordCommand.getConfirmPassword())) {
            model.addAttribute("message",
                    messageSource.getMessage("password.not.match.message", null, locale));
            model.addAttribute("action", "changePassword");
            return "main";
        }

        if (!passwordEncoder.matches(passwordCommand.getCurrentPassword(), securityUser
                .getPassword())) {
            model.addAttribute("message",
                    messageSource.getMessage("password.not.correct.message", null, locale));
            model.addAttribute("action", "changePassword");
            return "main";
        }
        User user = null;
        try {
            user = userService.get(securityUser.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Cannot find user.");
        }
        user.setPassword(passwordEncoder.encode(passwordCommand.getPassword()));
        try {
            user = userService.update(user);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Missing fields.");
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Not unique.");
        }
        securityUser.setPassword(user.getPassword());
        model.addAttribute("action", "index");
        return "main";
    }


    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView view = new ModelAndView();
        view.setViewName("403");
        return view;
    }
}
