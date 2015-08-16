package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.vo.HomeCommand;
import com.lambo.smartpay.ecs.web.vo.PasswordCommand;
import com.lambo.smartpay.ecs.web.vo.UserCommand;
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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

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
    private RefundService refundService;
    @Autowired
    private RefundStatusService refundStatusService;
    @Autowired
    private ClaimService claimService;
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
        model.addAttribute("merchantCommand", command);
        model.addAttribute("_view", "index");

        return "main";
    }

    @RequestMapping(value = "/listTodayOrder", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listTodayOrder() {

        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }

        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(currentUser.getMerchant());

        List<Site> sites = siteService.findByCriteria(site);
        Order orderCriteria = new Order();
        orderCriteria.setSite(site);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date begining = calendar.getTime();
        calendar.add(calendar.DATE, 1);
        Date ending = calendar.getTime();
        List<Order> orders = orderService.findByCriteria(orderCriteria, begining, ending);
        Refund refundCriteria = new Refund();
        refundCriteria.setOrder(orderCriteria);
        RefundStatus refundStatus;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties.REFUND_STATUS_APPROVED_CODE);
            refundCriteria.setRefundStatus(refundStatus);
        }catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        List<Refund> refunds = refundService.findByCriteria(refundCriteria, begining, ending);

        Claim claimCriteria = new Claim();
        List<Claim> claims = claimService.findByCriteria(claimCriteria, begining, ending);
        DataTablesResultSet<DataTablesOrderCount> result = createList(orders, refunds, claims, sites);
        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/listYesterdayOrder", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listYesterdayOrder() {

        SecurityUser currentUser = UserResource.getCurrentUser();
        if (currentUser == null) {
            return "403";
        }

        // find all site, and get count based on site
        Site site = new Site();
        site.setMerchant(currentUser.getMerchant());

        List<Site> sites = siteService.findByCriteria(site);
        Order orderCriteria = new Order();
        orderCriteria.setSite(site);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date begining = calendar.getTime();
        calendar.add(calendar.DATE, 1);
        Date ending = calendar.getTime();

        List<Order> orders = orderService.findByCriteria(orderCriteria, begining, ending);

        Refund refundCriteria = new Refund();
        refundCriteria.setOrder(orderCriteria);
        RefundStatus refundStatus;
        try {
            refundStatus = refundStatusService.findByCode(ResourceProperties.REFUND_STATUS_APPROVED_CODE);
            refundCriteria.setRefundStatus(refundStatus);
        }catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        List<Refund> refunds = refundService.findByCriteria(refundCriteria, begining, ending);

        Claim claimCriteria = new Claim();
        List<Claim> claims = claimService.findByCriteria(claimCriteria, begining, ending);

        DataTablesResultSet<DataTablesOrderCount> result = createList(orders, refunds, claims, sites);

        return JsonUtil.toJson(result);
    }

    private DataTablesResultSet<DataTablesOrderCount> createList(List<Order> orders, List<Refund> refunds, List<Claim> claims, List<Site> sites){
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
                    if(order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_PAID_CODE)
                            || order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_PREPARED_SHIPMENT_CODE)
                            || order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_SHIPPED_CODE)
                            || order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_DELIVERED_CODE)
                            || order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_RETURNED_CODE)
                            || order.getOrderStatus().getCode().equals(ResourceProperties.ORDER_STATUS_REFUNDED_CODE)) {
                        paidCount ++;
                        Iterator<Payment> paymentIterator = order.getPayments().iterator();
                        while(paymentIterator.hasNext()){
                            Payment payment = paymentIterator.next();
                            if(!payment.getPaymentStatus().getCode().equals("501")) {
                                paidAmount += payment.getAmount();
                            }
                        }
                    }
                }
            }
            for (Refund refund : refunds) {
                Order order = refund.getOrder();
                if(order.getSite().getId().equals(s.getId())
                        && refund.getRefundStatus().getCode().equals(ResourceProperties.REFUND_STATUS_APPROVED_CODE)) {
                    refundCount++;
                    refundAmount += refund.getAmount();
                }
            }
            for (Claim claim : claims) {
                Payment payment = claim.getPayment();
                if(payment.getPaymentStatus().getCode().equals(ResourceProperties.PAYMENT_STATUS_CLAIM_RESOLVED_CODE)
                        && payment.getOrder().getSite().getId().equals(s.getId())){
                    refuseCount ++;
                    refuseAmount +=payment.getAmount();
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
        model.addAttribute("_view", "changePassword");
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
            model.addAttribute("_view", "changePassword");
            return "main";
        }

        if (!passwordEncoder.matches(passwordCommand.getCurrentPassword(), securityUser
                .getPassword())) {
            model.addAttribute("message",
                    messageSource.getMessage("password.not.correct.message", null, locale));
            model.addAttribute("_view", "changePassword");
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
        model.addAttribute("_view", "index");
        return "main";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {

        User user = null;
        try {
            user = UserResource.getCurrentUser();
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User not found.");
        }

        // create command user and add to model and view
        UserCommand userCommand = createUserCommand(user);
        model.addAttribute("userCommand", userCommand);
        model.addAttribute("_view", "profile");
        return "main";
    }

    /**
     * Save a user.
     *
     * @param userCommand
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(Model model, @ModelAttribute("userCommand") UserCommand
            userCommand) {
        Locale locale = LocaleContextHolder.getLocale();

        // if the email is change we need to check uniqueness
        User user = null;
        try {
            user = userService.get(userCommand.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new BadRequestException("400", "User " + userCommand.getId() + " not found.");
        }

        // create command user and add to model and view
        if (!userCommand.getEmail().equals(user.getEmail())) {
            User emailUser = userService.findByEmail(userCommand.getEmail());
            if (emailUser != null) {
                // get locale and messages
                String fieldLabel = messageSource.getMessage("email.label", null, locale);
                model.addAttribute("message",
                        messageSource.getMessage("not.unique.message",
                                new String[]{fieldLabel, userCommand.getEmail()}, locale));
                model.addAttribute("userCommand", userCommand);
                model.addAttribute("_view", "profile");
                return "main";
            }
        }

        // pass uniqueness check create the user
        editUser(user, userCommand);

        // user is a user edited by merchant admin
        try {
            userService.update(user);
            String fieldLabel = messageSource.getMessage("user.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("user.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("user.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        }
        return "redirect:/index";
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView view = new ModelAndView();
        view.setViewName("403");
        return view;
    }

    // create UserCommand from User
    private UserCommand createUserCommand(User user) {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setUsername(user.getUsername());
        userCommand.setFirstName(user.getFirstName());
        userCommand.setLastName(user.getLastName());
        userCommand.setEmail(user.getEmail());
        userCommand.setActive(user.getActive());
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        userCommand.setCreatedTime(dateFormat.format(user.getCreatedTime()));
        userCommand.setRemark(user.getRemark());
        if (user.getMerchant() != null) {
            userCommand.setMerchant(user.getMerchant().getId());
            userCommand.setMerchantName(user.getMerchant().getName());
        }

        if (user.getUserStatus() != null) {
            userCommand.setUserStatus(user.getUserStatus().getId());
            userCommand.setUserStatusName(user.getUserStatus().getName());
        }
        return userCommand;
    }

    // edit a User from a UserCommand
    private void editUser(User user, UserCommand userCommand) {
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setEmail(userCommand.getEmail());
        user.setRemark(userCommand.getRemark());
    }
}
