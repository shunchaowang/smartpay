package com.lambo.smartpay.pay.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.persistence.entity.Customer;
import com.lambo.smartpay.core.persistence.entity.CustomerStatus;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Order;
import com.lambo.smartpay.core.persistence.entity.OrderStatus;
import com.lambo.smartpay.core.persistence.entity.Payment;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.CurrencyService;
import com.lambo.smartpay.core.service.CustomerService;
import com.lambo.smartpay.core.service.CustomerStatusService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.pay.util.MDUtil;
import com.lambo.smartpay.pay.util.PayConfiguration;
import com.lambo.smartpay.pay.util.ResourceProperties;
import com.lambo.smartpay.pay.web.exception.BadRequestException;
import com.lambo.smartpay.pay.web.vo.OrderCommand;
import com.lambo.smartpay.pay.web.vo.PaymentCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/11/2015.
 * Pay home.
 */
@Controller
@SessionAttributes("orderCommand")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerStatusService customerStatusService;
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView home() {
        //view.addObject("action", "index");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest request, HttpServletResponse response, Model model) {

        // params passed from client
        // merchant and site number
        String merNo = formatString(request.getParameter("merNo"));
        // merchant need to pass a site number for site check
        String siteNo = formatString(request.getParameter("siteNo"));
        logger.debug("Merchant number is " + merNo);
        logger.debug("Site number is " + siteNo);
        if (StringUtils.isBlank(merNo) || StringUtils.isBlank(siteNo)) {
            throw new BadRequestException("400", "Merchant number or site number is blank.");
        }
        // transaction info
        String orderNo = formatString(request.getParameter("orderNo"));
        logger.debug("Order number is " + orderNo);
        if (StringUtils.isBlank(orderNo)) {
            throw new BadRequestException("400", "Order number is blank.");
        }

        String amount = formatString(request.getParameter("amount"));
        logger.debug("Amount is " + amount);
        if (StringUtils.isBlank(amount)) {
            throw new BadRequestException("400", "Amount is blank.");
        }

        String currency = formatString(request.getParameter("currency"));
        logger.debug("Currency is " + currency);
        if (StringUtils.isBlank(currency)) {
            throw new BadRequestException("400", "Currency is blank.");
        }

        String md5Info = formatString(request.getParameter("merMd5info"));
        logger.debug("MD5 summary is " + md5Info);
        if (StringUtils.isBlank(md5Info)) {
            throw new BadRequestException("400", "MD5 summary is blank.");
        }

        String productType = formatString(request.getParameter("productType"));
        logger.debug("Product type is " + productType);
        if (StringUtils.isBlank(productType)) {
            throw new BadRequestException("400", "Product type is blank.");
        }
        String goodsName = formatString(request.getParameter("goodsName"));
        logger.debug("Goods name is " + goodsName);
        if (StringUtils.isBlank(goodsName)) {
            throw new BadRequestException("400", "Goods name is blank.");
        }

        String goodsNumber = formatString(request.getParameter("goodsNumber"));
        logger.debug("Goods number is " + goodsNumber);
        if (StringUtils.isBlank(goodsNumber)) {
            throw new BadRequestException("400", "Goods number is blank.");
        }
        String goodsPrice = formatString(request.getParameter("goodsPrice"));
        logger.debug("Goods price is " + goodsPrice);
        if (StringUtils.isBlank(goodsPrice)) {
            throw new BadRequestException("400", "Goods price is blank.");
        }

        // shipping and customer info
        String email = formatString(request.getParameter("email"));
        logger.debug("Shipping email is " + email);
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("400", "Shipping email is blank.");
        }
        String shipFirstName = formatString(request.getParameter("shipFirstName"));
        logger.debug("Shipping last name is " + shipFirstName);
        if (StringUtils.isBlank(shipFirstName)) {
            throw new BadRequestException("400", "Shipping last name is blank.");
        }
        String shipLastName = formatString(request.getParameter("shipLastName"));
        logger.debug("Shipping last name is " + shipLastName);
        if (StringUtils.isBlank(shipLastName)) {
            throw new BadRequestException("400", "Shipping last name is blank.");
        }
        String shipAddress = formatString(request.getParameter("shipAddress"));
        logger.debug("Shipping address is " + shipAddress);
        if (StringUtils.isBlank(shipAddress)) {
            throw new BadRequestException("400", "Shipping address is blank.");
        }
        String shipCity = formatString(request.getParameter("shipCity"));
        logger.debug("Shipping city is " + shipCity);
        if (StringUtils.isBlank(shipCity)) {
            throw new BadRequestException("400", "Shipping city is blank.");
        }
        String shipState = formatString(request.getParameter("shipState"));
        logger.debug("Shipping state is " + shipState);
        if (StringUtils.isBlank(shipState)) {
            throw new BadRequestException("400", "Shipping state is blank.");
        }
        String shipCountry = formatString(request.getParameter("shipCountry"));
        logger.debug("Shipping country is " + shipCountry);
        if (StringUtils.isBlank(shipCountry)) {
            throw new BadRequestException("400", "Shipping country is blank.");
        }
        String shipZipCode = formatString(request.getParameter("shipZipCode"));
        logger.debug("Ship zip code is " + shipZipCode);
        if (StringUtils.isBlank(shipZipCode)) {
            throw new BadRequestException("400", "Shipping zip code is blank.");
        }

        // customer optional info
        String phone = formatString(request.getParameter("phone"));
        logger.debug("Optional shipping phone is " + phone);
        String remark = formatString(request.getParameter("remark"));
        logger.debug("Optional shipping remark is " + remark);

        // notification url
        String returnURL = formatString(request.getParameter("returnURL"));
        logger.debug("Merchant return URL is " + returnURL);
        if (StringUtils.isBlank(returnURL)) {
            throw new BadRequestException("400", "Merchant return URL is blank.");
        }

        // we need to check if the merchant or the site is frozen
        // if so decline the payment request
        Merchant merchant = merchantService.findByIdentity(merNo);
        String merchantKey = merchant.getEncryption().getKey();
        Site site = siteService.findByIdentity(siteNo);
        if (merchant == null || site == null) {
            return "403";
        }
        Boolean canOperate;
        try {
            canOperate = merchantService.canOperate(merchant.getId())
                    && siteService.canOperate(site.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return "404";
        }
        if (!canOperate) {
            return "403";
        }

        // check md5info, md5 summary should be generated based merNo, merKey,
        // siteNo and formatted amount
        String calculatedMd5Info = MDUtil.getMD5Str(merNo + merchantKey + siteNo + amount);
        if (!md5Info.equals(calculatedMd5Info)) {
            String succeed = "0";
            String errcode = "1002. MD5 Info Checking Error.";
            String resultMd5Info = MDUtil.getMD5Str(merNo + siteNo + amount + succeed);
            try {
                response.sendRedirect(returnURL + "&succeed=" + succeed + "&amount=" +
                        amount + "&orderNo=" + orderNo
                        + "&currency=" + currency + "&errcode=" + errcode + "&md5info=" +
                        resultMd5Info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OrderCommand orderCommand = createOrderCommand(request);

        // Order and Customer should be created here
        // if customer exists using current one
        Customer customer = customerService.findByEmail(email);
        if (customer == null) {
            customer = new Customer();
            customer.setFirstName(shipFirstName);
            customer.setLastName(shipLastName);
            customer.setEmail(email);
            customer.setAddress1(shipAddress);
            customer.setCity(shipCity);
            customer.setState(shipState);
            customer.setZipCode(shipZipCode);
            customer.setCountry(shipCountry);
            CustomerStatus customerStatus = null;
            try {
                customerStatus = customerStatusService
                        .findByCode(ResourceProperties.CUSTOMER_STATUS_NORMAL_CODE);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            customer.setCustomerStatus(customerStatus);
            try {
                customer = customerService.create(customer);
            } catch (MissingRequiredFieldException e) {
                e.printStackTrace();
            } catch (NotUniqueException e) {
                e.printStackTrace();
            }
        }

        OrderStatus orderStatus = null;
        try {
            orderStatus = orderStatusService
                    .findByCode(ResourceProperties.ORDER_STATUS_INITIATED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        // set currency
        Currency domainCurrency = null;
        try {
            domainCurrency = currencyService.findByName(StringUtils.upperCase(currency));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        // create Order
        Order order = new Order();
        order.setMerchantNumber(merNo);
        order.setAmount(Float.valueOf(amount));
        order.setGoodsName(orderCommand.getGoodsName());
        order.setGoodsAmount(orderCommand.getGoodsNumber());
        order.setSite(site);
        order.setOrderStatus(orderStatus);
        order.setCurrency(domainCurrency);
        order.setCustomer(customer);

        try {
            order = orderService.create(order);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
        }

        // create order command object and add to model
        orderCommand.setCustomerId(customer.getId());
        orderCommand.setSiteId(site.getId());

        if (orderStatus != null) {
            orderCommand.setOrderStatusId(orderStatus.getId());
        }
        if (domainCurrency != null) {
            orderCommand.setCurrencyId(domainCurrency.getId());
        }
        orderCommand.setOrderId(order.getId());

        model.addAttribute("orderCommand", orderCommand);

        return "pay";
    }

    @RequestMapping(value = {"/payByCard"}, method = RequestMethod.POST)
    public String payByCard(@ModelAttribute("paymentCommand") PaymentCommand paymentCommand,
                            Model model, HttpServletRequest request) {

        Date date = Calendar.getInstance().getTime();
        DecimalFormat decimalFormat = new DecimalFormat("###.##");

        // formulate params sending to hooppay

        HttpSession session = request.getSession();
        OrderCommand orderCommand = (OrderCommand) session.getAttribute("orderCommand");
        logger.debug("orderCommand: " + orderCommand.getMerNo());

        //TODO CODING HERE
        // save payment object

        // create hooppay params
        List<BasicNameValuePair> params = formulateHooppayParams(paymentCommand, orderCommand,
                request);

        // params passed from client
        // merchant and site number
        String merNo = formatString(request.getParameter("merNo"));
        // merchant need to pass a site number for site check
        String siteNo = formatString(request.getParameter("siteNo"));
        logger.debug("Merchant number is " + merNo);
        logger.debug("Site number is " + siteNo);
        if (StringUtils.isBlank(merNo) || StringUtils.isBlank(siteNo)) {
            throw new BadRequestException("400", "Merchant number or site number is blank.");
        }
        // transaction info
        String orderNo = formatString(request.getParameter("orderNo"));
        logger.debug("Order number is " + orderNo);
        if (StringUtils.isBlank(orderNo)) {
            throw new BadRequestException("400", "Order number is blank.");
        }
        String amount = formatString(request.getParameter("amount"));
        logger.debug("Amount is " + amount);
        if (StringUtils.isBlank(amount)) {
            throw new BadRequestException("400", "Amount is blank.");
        }
        // format amount
        amount = decimalFormat.format(Float.parseFloat(amount));
        String currency = formatString(request.getParameter("currency"));
        logger.debug("Currency is " + currency);
        if (StringUtils.isBlank(currency)) {
            throw new BadRequestException("400", "Currency is blank.");
        }
        String productType = formatString(request.getParameter("productType"));
        logger.debug("Product type is " + productType);
        if (StringUtils.isBlank(productType)) {
            throw new BadRequestException("400", "Product type is blank.");
        }
        String goodsName = formatString(request.getParameter("goodsName"));
        logger.debug("Goods name is " + goodsName);
        if (StringUtils.isBlank(goodsName)) {
            throw new BadRequestException("400", "Goods name is blank.");
        }
        String goodsNumber = formatString(request.getParameter("goodsNumber"));
        logger.debug("Goods number is " + goodsNumber);
        if (StringUtils.isBlank(goodsNumber)) {
            throw new BadRequestException("400", "Goods number is blank.");
        }
        String goodsPrice = formatString(request.getParameter("goodsPrice"));
        logger.debug("Goods price is " + goodsPrice);
        if (StringUtils.isBlank(goodsPrice)) {
            throw new BadRequestException("400", "Goods price is blank.");
        }
        // format price
        String[] goodsPriceArray = goodsPrice.split(",");
        String goodsPriceString = "";
        for (int i = 0; i < goodsPriceArray.length; i++) {
            goodsPriceString = goodsPriceString
                    + decimalFormat.format(Float.parseFloat(goodsPriceArray[i]));
            if (i < (goodsPriceArray.length - 1))
                goodsPriceString = goodsPriceString + ",";
        }
        goodsPrice = goodsPriceString;


        // shipping and customer info
        String email = formatString(request.getParameter("email"));
        logger.debug("Shipping email is " + email);
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("400", "Shipping email is blank.");
        }
        String shipFirstName = formatString(request.getParameter("shipFirstName"));
        logger.debug("Shipping last name is " + shipFirstName);
        if (StringUtils.isBlank(shipFirstName)) {
            throw new BadRequestException("400", "Shipping last name is blank.");
        }
        String shipLastName = formatString(request.getParameter("shipLastName"));
        logger.debug("Shipping last name is " + shipLastName);
        if (StringUtils.isBlank(shipLastName)) {
            throw new BadRequestException("400", "Shipping last name is blank.");
        }
        String shipAddress = formatString(request.getParameter("shipAddress"));
        logger.debug("Shipping address is " + shipAddress);
        if (StringUtils.isBlank(shipAddress)) {
            throw new BadRequestException("400", "Shipping address is blank.");
        }
        String shipCity = formatString(request.getParameter("shipCity"));
        logger.debug("Shipping city is " + shipCity);
        if (StringUtils.isBlank(shipCity)) {
            throw new BadRequestException("400", "Shipping city is blank.");
        }
        String shipState = formatString(request.getParameter("shipState"));
        logger.debug("Shipping state is " + shipState);
        if (StringUtils.isBlank(shipState)) {
            throw new BadRequestException("400", "Shipping state is blank.");
        }
        String shipCountry = formatString(request.getParameter("shipCountry"));
        logger.debug("Shipping country is " + shipCountry);
        if (StringUtils.isBlank(shipCountry)) {
            throw new BadRequestException("400", "Shipping country is blank.");
        }
        String shipZipCode = formatString(request.getParameter("shipZipCode"));
        logger.debug("Ship zip code is " + shipZipCode);
        if (StringUtils.isBlank(shipZipCode)) {
            throw new BadRequestException("400", "Shipping zip code is blank.");
        }

        // customer optional info
        String phone = formatString(request.getParameter("phone"));
        logger.debug("Optional shipping phone is " + phone);
        String remark = formatString(request.getParameter("remark"));
        logger.debug("Optional shipping remark is " + remark);

        // notification url
        String returnURL = formatString(request.getParameter("returnURL"));
        logger.debug("Merchant return URL is " + returnURL);
        if (StringUtils.isBlank(returnURL)) {
            throw new BadRequestException("400", "Merchant return URL is blank.");
        }

        // format decimal digit

        // Parse all parameters from request object
        String acceptLanguage = request.getHeader("Accept-Language");
        logger.debug("Accept language is " + acceptLanguage);
        String userAgent = request.getHeader("User-Agent");
        logger.debug("User agent is " + userAgent);
        Locale locale = request.getLocale();
        String language = locale.getLanguage();
        logger.debug("Language is " + language);
        //is client behind something?
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        logger.debug("Client ip is " + clientIp);

        // params obtained from properties
        String payUrl = PayConfiguration.getInstance()
                .getValue(ResourceProperties.HOOPPAY_URL_KEY);
        logger.debug("Pay url is " + payUrl);
        String merchantId = PayConfiguration.getInstance()
                .getValue(ResourceProperties.MERCHANT_ID_KEY);
        logger.debug("Merchant id is " + merchantId);
        String merKey = PayConfiguration.getInstance().getValue(ResourceProperties.MER_KEY_KEY);
        logger.debug("Merchant key is " + merKey);
        String referer = PayConfiguration.getInstance().getValue(ResourceProperties.REFERER_KEY);
        logger.debug("Merchant referer is " + referer);
        String shopName = PayConfiguration.getInstance()
                .getValue(ResourceProperties.SHOP_NAME_KEY);
        logger.debug("Merchant shop name is " + shopName);

        // repayment flag needs to be set on UI form


        // params obtained from container or browser
        // del String acceptLanguage = formatString(request.getParameter("acceptLanguage"));
        // del String userAgent = formatString(request.getParameter("userAgent"));

        // del String clientIp = formatString(request.getParameter("clientIp"));
        // del String language = formatString(request.getParameter("language"));

        // we need to check if the merchant or the site is frozen
        // if so decline the payment request
        Merchant merchant = merchantService.findByIdentity(merNo);
        Site site = siteService.findByIdentity(siteNo);
        if (merchant == null || site == null) {
            return "403";
        }
        Boolean canOperate;
        try {
            canOperate = merchantService.canOperate(merchant.getId())
                    && siteService.canOperate(site.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return "404";
        }
        if (!canOperate) {
            return "403";
        }
        // calculate the md5 of the request based on merchant number and key
        // if not correct decline the payment request

        return "index";
    }

    @RequestMapping(value = {"/payByBitCoin"}, method = RequestMethod.POST)
    public String payByBitCoin(HttpServletRequest request) {
        return null;
    }


    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView view = new ModelAndView();
        view.setViewName("403");
        return view;
    }

    private String formatString(String string) {
        if (StringUtils.isBlank(string)) {
            return "";
        }
        return StringUtils.trim(string);
    }

    private OrderCommand createOrderCommand(HttpServletRequest request) {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setOrderNo(request.getParameter("orderNo"));
        orderCommand.setMerNo(request.getParameter("merNo"));
        orderCommand.setSiteNo(request.getParameter("siteNo"));
        orderCommand.setReturnUrl(request.getParameter("returnURL"));
        orderCommand.setAmount(request.getParameter("amount"));
        orderCommand.setCurrency(request.getParameter("currency"));
        orderCommand.setProductType(request.getParameter("productType"));
        String goodsName = request.getParameter("goodsName");
        if (StringUtils.length(goodsName) > 128) {
            goodsName = StringUtils.substring(goodsName, 0, 125);
        }
        orderCommand.setGoodsName(goodsName);
        String[] goodsNames = goodsName.split(",");
        List goodsNameArray = Arrays.asList(goodsNames);
        orderCommand.setGoodsNameArray(goodsNameArray);

        String goodsNumber = request.getParameter("goodsNumber");
        orderCommand.setGoodsNumber(goodsNumber);
        String[] goodsNumbers = goodsNumber.split(",");
        orderCommand.setGoodsNumberArray(Arrays.asList(goodsNumbers));
        String goodsPrice = request.getParameter("goodsPrice");
        orderCommand.setGoodsPrice(goodsPrice);
        String[] goodsPrices = goodsPrice.split(",");
        orderCommand.setGoodsPriceArray(Arrays.asList(goodsPrices));
        orderCommand.setEmail(request.getParameter("email"));
        orderCommand.setPhone(request.getParameter("phone"));
        orderCommand.setShipFirstName(request.getParameter("shipFirstName"));
        orderCommand.setShipLastName(request.getParameter("shipLastName"));
        orderCommand.setShipCity(request.getParameter("shipCity"));
        orderCommand.setShipAddress(request.getParameter("shipAddress"));
        orderCommand.setShipState(request.getParameter("shipState"));
        orderCommand.setShipZipCode(request.getParameter("shipZipCode"));
        orderCommand.setShipCountry(request.getParameter("shipCountry"));
        orderCommand.setRemark(request.getParameter("remark"));
        return orderCommand;
    }

    Payment createPayment(PaymentCommand paymentCommand) {
        Payment payment = new Payment();
        return payment;
    }

    List<BasicNameValuePair> formulateHooppayParams(PaymentCommand paymentCommand, OrderCommand
            orderCommand, HttpServletRequest request) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

        // Parse all parameters from request object
        String acceptLanguage = request.getHeader("Accept-Language");
        logger.debug("Accept language is " + acceptLanguage);
        String userAgent = request.getHeader("User-Agent");
        logger.debug("User agent is " + userAgent);
        Locale locale = request.getLocale();
        String language = locale.getLanguage();
        logger.debug("Language is " + language);
        //is client behind something?
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        logger.debug("Client ip is " + clientIp);

        pairs.add(new BasicNameValuePair("acceptLanguage", acceptLanguage));
        pairs.add(new BasicNameValuePair("userAgent", userAgent));
        pairs.add(new BasicNameValuePair("clientIp", clientIp));
        pairs.add(new BasicNameValuePair("language", language));

        // params obtained from properties
        String payUrl = PayConfiguration.getInstance()
                .getValue(ResourceProperties.HOOPPAY_URL_KEY);
        logger.debug("Pay url is " + payUrl);
        String merchantId = PayConfiguration.getInstance()
                .getValue(ResourceProperties.MERCHANT_ID_KEY);
        logger.debug("Merchant id is " + merchantId);
        String merKey = PayConfiguration.getInstance().getValue(ResourceProperties.MER_KEY_KEY);
        logger.debug("Merchant key is " + merKey);
        String referer = PayConfiguration.getInstance().getValue(ResourceProperties.REFERER_KEY);
        logger.debug("Merchant referer is " + referer);
        String shopName = PayConfiguration.getInstance()
                .getValue(ResourceProperties.SHOP_NAME_KEY);
        logger.debug("Merchant shop name is " + shopName);

        pairs.add(new BasicNameValuePair("shopName", shopName));
        pairs.add(new BasicNameValuePair("referer", referer));
        pairs.add(new BasicNameValuePair("merNo", merchantId));

        // calcuate hooppay md5
        String md5Info = MDUtil.getMD5Str(merchantId + orderCommand.getOrderId()
                + orderCommand.getAmount() + orderCommand.getCurrency()
                + merKey + orderCommand.getEmail());
        pairs.add(new BasicNameValuePair("md5Info", md5Info));

        // parse payment command and add those to pairs
        pairs.add(new BasicNameValuePair("orderNo", paymentCommand.getId().toString()));
        pairs.add(new BasicNameValuePair("amount", paymentCommand.getAmount().toString()));
        pairs.add(new BasicNameValuePair("currency", paymentCommand.getCurrencyName()));
        pairs.add(new BasicNameValuePair("payMethod", "0")); // means credit card
        pairs.add(new BasicNameValuePair("cardNo", paymentCommand.getBankAccountNumber()));
        pairs.add(new BasicNameValuePair("cvv", paymentCommand.getCvv()));
        pairs.add(new BasicNameValuePair("expireMonth", paymentCommand.getExpireMonth()));
        pairs.add(new BasicNameValuePair("expireYear", paymentCommand.getExpireYear()));
        pairs.add(new BasicNameValuePair("issuingBank", paymentCommand.getBankName()));
        pairs.add(new BasicNameValuePair("billFirstName", paymentCommand.getBillFirstName()));
        pairs.add(new BasicNameValuePair("billLastName", paymentCommand.getBillLastName()));
        pairs.add(new BasicNameValuePair("billAddress", paymentCommand.getBillAddress1()));
        pairs.add(new BasicNameValuePair("billCity", paymentCommand.getBillCity()));
        pairs.add(new BasicNameValuePair("billState", paymentCommand.getBillState()));
        pairs.add(new BasicNameValuePair("billCountry", paymentCommand.getBillCountry()));
        pairs.add(new BasicNameValuePair("billZipCode", paymentCommand.getBillZipCode()));
        pairs.add(new BasicNameValuePair("repayment", paymentCommand.getRepayment()));


        // parse order command and add those to pairs
        pairs.add(new BasicNameValuePair("email", orderCommand.getEmail()));
        pairs.add(new BasicNameValuePair("phone", orderCommand.getPhone()));
        pairs.add(new BasicNameValuePair("merNo", orderCommand.getMerNo()));
        pairs.add(new BasicNameValuePair("returnURL", orderCommand.getReturnUrl()));
        pairs.add(new BasicNameValuePair("productType", orderCommand.getProductType()));
        pairs.add(new BasicNameValuePair("goodsName", orderCommand.getGoodsName()));
        pairs.add(new BasicNameValuePair("goodsNumber", orderCommand.getGoodsNumber()));
        pairs.add(new BasicNameValuePair("goodsPrice", orderCommand.getGoodsPrice()));
        pairs.add(new BasicNameValuePair("shipFirstName", orderCommand.getShipFirstName()));
        pairs.add(new BasicNameValuePair("shipLastName", orderCommand.getShipLastName()));
        pairs.add(new BasicNameValuePair("shipAddress", orderCommand.getShipAddress()));
        pairs.add(new BasicNameValuePair("shipCity", orderCommand.getShipCity()));
        pairs.add(new BasicNameValuePair("shipState", orderCommand.getShipState()));
        pairs.add(new BasicNameValuePair("shipCountry", orderCommand.getShipCountry()));
        pairs.add(new BasicNameValuePair("shipZipCode", orderCommand.getShipZipCode()));

        return pairs;
    }
}
