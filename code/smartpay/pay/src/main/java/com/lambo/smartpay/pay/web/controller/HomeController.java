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
import com.lambo.smartpay.core.persistence.entity.PaymentStatus;
import com.lambo.smartpay.core.persistence.entity.PaymentType;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.CurrencyService;
import com.lambo.smartpay.core.service.CustomerService;
import com.lambo.smartpay.core.service.CustomerStatusService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import com.lambo.smartpay.core.service.PaymentTypeService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.pay.util.MDUtil;
import com.lambo.smartpay.pay.util.PayConfiguration;
import com.lambo.smartpay.pay.util.ResourceProperties;
import com.lambo.smartpay.pay.web.exception.BadRequestException;
import com.lambo.smartpay.pay.web.exception.IntervalServerException;
import com.lambo.smartpay.pay.web.vo.OrderCommand;
import com.lambo.smartpay.pay.web.vo.PaymentCommand;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import java.io.UnsupportedEncodingException;
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
@RequestMapping("/")
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
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private PaymentStatusService paymentStatusService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = {"index"})
    public ModelAndView home(Model model) {
        //view.addObject("action", "index");
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setMerNo("M0000000");
        orderCommand.setSiteNo("S0000000");
        orderCommand.setOrderNo("O1111111");
        orderCommand.setAmount("12.34");
        orderCommand.setReturnUrl("www.google.com");
        orderCommand.setReferer("www.google.com");
        orderCommand.setCurrency("USD");
        orderCommand.setProductType("1");
        orderCommand.setGoodsName("Mac, iPhone, iPad, iWatch");
        orderCommand.setGoodsNumber("1, 2, 3, 4");
        orderCommand.setGoodsPrice("10.00, 120.00, 200, 400");
        orderCommand.setEmail("customer@me.com");
        orderCommand.setPhone("2883659999");
        orderCommand.setShipFirstName("First Name");
        orderCommand.setShipLastName("Last Name");
        orderCommand.setShipAddress("100 Paradise Rd");
        orderCommand.setShipCity("Los Angles");
        orderCommand.setShipState("CA");
        orderCommand.setShipZipCode("98765");
        orderCommand.setShipCountry("US");

        model.addAttribute("orderCommand", orderCommand);
        // check md5info, md5 summary should be generated based on merNo,
        // orderNo, formatted amount, 3 uppercase currency characters, merKey and email
        String calculatedMd5Info = MDUtil.getMD5Str(orderCommand.getMerNo()
                + orderCommand.getOrderNo() + orderCommand.getAmount() + orderCommand.getCurrency()
                + "12345" + orderCommand.getEmail());

        model.addAttribute("md5Info", calculatedMd5Info);
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest request, HttpServletResponse response, Model model) {

        // params passed from client
        // merchant and site number
        String merNo = formatString(request.getParameter("merNo"));
        logger.debug("Merchant number is " + merNo);
        if (StringUtils.isBlank(merNo)) {
            throw new BadRequestException("400", "Merchant number is blank.");
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
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        amount = decimalFormat.format(Float.parseFloat(amount));

        String currency = formatString(request.getParameter("currency"));
        logger.debug("Currency is " + currency);
        if (StringUtils.isBlank(currency)) {
            throw new BadRequestException("400", "Currency is blank.");
        }

        String md5Info = formatString(request.getParameter("md5Info"));
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

        // referer is the site url in our system, used for site approval
        String referer = formatString(request.getParameter("referer"));
        logger.debug("Referer is " + referer);
        if (StringUtils.isBlank(referer)) {
            throw new BadRequestException("400", "Referer is blank.");
        }

        // we need to check if the merchant or the site is frozen
        // if so decline the payment request
        Merchant merchant = merchantService.findByIdentity(merNo);
        String merchantKey = merchant.getEncryption().getKey();
        Site site = siteService.findByUrl(referer);
        if (merchant == null) {
            logger.debug("Merchant " + merNo + " is null.");
            return "403";
        }
        if (site == null) {
            logger.debug("Site " + referer + " is null.");
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

        // check md5info, md5 summary should be generated based merNo, orderNo, formatted amount,
        // 3 uppercase characters currency, merchantKey and email
        String calculatedMd5Info = MDUtil.getMD5Str(merNo + orderNo + amount + currency
                + merchantKey + email);
        if (!md5Info.equals(calculatedMd5Info)) {
            String succeed = "0";
            String errcode = "1002. MD5 Info Checking Error.";
            String resultMd5Info = MDUtil.getMD5Str(merchantKey + merNo + orderNo + amount +
                    currency + succeed);
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
        } else {
            customer.setFirstName(shipFirstName);
            customer.setLastName(shipLastName);
            customer.setEmail(email);
            customer.setAddress1(shipAddress);
            customer.setCity(shipCity);
            customer.setState(shipState);
            customer.setZipCode(shipZipCode);
            customer.setCountry(shipCountry);
            try {
                customer = customerService.update(customer);
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
        Order order = orderService.findByMerchantNumber(orderNo);

        // if order does not exist create new
        if (order == null) {
            // create Order
            order = new Order();
            order.setMerchantNumber(orderNo);
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
        } else { // i order already existed update
            order.setAmount(Float.valueOf(amount));
            order.setGoodsName(orderCommand.getGoodsName());
            order.setGoodsAmount(orderCommand.getGoodsNumber());
            order.setSite(site);
            order.setOrderStatus(orderStatus);
            order.setCurrency(domainCurrency);
            order.setCustomer(customer);

            try {
                order = orderService.update(order);
            } catch (MissingRequiredFieldException e) {
                e.printStackTrace();
            } catch (NotUniqueException e) {
                e.printStackTrace();
            }
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
        model.addAttribute("paymentCommand", new PaymentCommand());

        return "pay";
    }

    @RequestMapping(value = {"payByCard"}, method = RequestMethod.POST)
    public void payByCard(@ModelAttribute("paymentCommand") PaymentCommand paymentCommand,
                          Model model,
                          HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // formulate params sending to hooppay

        HttpSession session = request.getSession();
        OrderCommand orderCommand = (OrderCommand) session.getAttribute("orderCommand");
        logger.debug("orderCommand: " + orderCommand.getMerNo());
        // we need to check if the merchant or the site is frozen
        // if so decline the payment request
        Merchant merchant = merchantService.findByIdentity(orderCommand.getMerNo());
        String merchantKey = merchant.getEncryption().getKey();

        // create payment object
        Payment payment = createPayment(paymentCommand);

        // create hooppay params and make the pay request
        List<BasicNameValuePair> params = formulateHooppayParams(paymentCommand, orderCommand,
                request);

        String result = hooppay(params);
        logger.debug("Hooppay return result: " + result);

        // base64 decoding
        String stringFromBase = new String(Base64.decodeBase64(result));
        logger.info("Decoded Base64 String: " + stringFromBase);

        // initial return parameter
        String[] strReturn = stringFromBase.split("&");
        String[] returnTradeNo = strReturn[0].split("=");
        String[] returnOrderNo = strReturn[1].split("=");
        String[] returnSucceed = strReturn[2].split("=");
        String[] returnBankInfo = strReturn[3].split("=");
        String[] returnMd5Info = strReturn[4].split("=");
        String[] returnCurrency = strReturn[5].split("=");
        String[] returnLanguage = strReturn[6].split("=");
        String[] returnReturnURL = strReturn[7].split("=");
        String[] returnAmount = strReturn[8].split("=");
        String returnStringMd5Info = formatString(MDUtil.getHashEncryption(returnMd5Info[1]));

        // SUCCEED CHECK
        String payTranNo = returnTradeNo[1]; // this will be bank transaction number
        logger.info("Pay gateway transaction number " + payTranNo);
        payment.setBankTransactionNumber(payTranNo);
        String bankCode = returnBankInfo[1];
        payment.setBankReturnCode(bankCode);

        String succeed = "0";
        //String orderStatus = "2"; // 交易失败
        String paymentStatusCode = "501";

        if (returnSucceed[1].equals("1")) {
            succeed = "1"; // 交易成功
            //orderStatus = "20"; // 交易成功
            paymentStatusCode = "500";
            payment.setSuccessTime(Calendar.getInstance().getTime());
            OrderStatus paidOrderStatus = null;
            try {
                paidOrderStatus = orderStatusService
                        .findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new IntervalServerException("500", "Cannot find paid order status.");
            }
            payment.getOrder().setOrderStatus(paidOrderStatus);
            try {
                orderService.update(payment.getOrder());
            } catch (MissingRequiredFieldException e) {
                e.printStackTrace();
                throw new IntervalServerException("500", "Cannot change order status to be paid.");
            } catch (NotUniqueException e) {
                e.printStackTrace();
                throw new IntervalServerException("500", "Cannot change order status to be paid.");
            }
        }

        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(paymentStatusCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        payment.setPaymentStatus(paymentStatus);

        // save payment object
        try {
            payment = paymentService.create(payment);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        // return string md5 encoded return string
        String calculatedMd5Info = MDUtil.getMD5Str(merchantKey + orderCommand.getMerNo()
                + orderCommand.getOrderNo() + orderCommand.getAmount()
                + orderCommand.getCurrency() + succeed);

        result = "?succeed=" + succeed + "&amount=" + orderCommand.getAmount()
                + "&orderNo=" + orderCommand.getOrderNo()
                + "&currency=" + orderCommand.getCurrency() + "&errcode=" + bankCode
                + "&md5info=" + calculatedMd5Info;
        if (StringUtils.contains(orderCommand.getReturnUrl(), '?')) {
            result = "&succeed=" + succeed + "&amount=" + orderCommand.getAmount()
                    + "&orderNo=" + orderCommand.getOrderNo()
                    + "&currency=" + orderCommand.getCurrency() + "&errcode=" + bankCode
                    + "&md5info=" + calculatedMd5Info;
        }
        response.sendRedirect(orderCommand.getReturnUrl() + result);
    }

    @RequestMapping(value = {"/payByBitCoin"}, method = RequestMethod.POST)
    public String payByBitCoin(HttpServletRequest request) {
        return null;
    }


    // for 403 access denied page
    @RequestMapping(value = "403", method = RequestMethod.GET)
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
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        String amount = request.getParameter("amount");
        orderCommand.setAmount(decimalFormat.format(Float.parseFloat(amount)));
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
        // format price
        String[] goodsPrices = goodsPrice.split(",");
        String goodsPriceString = "";
        for (int i = 0; i < goodsPrices.length; i++) {
            goodsPriceString = goodsPriceString
                    + decimalFormat.format(Float.parseFloat(goodsPrices[i]));
            if (i < (goodsPrices.length - 1))
                goodsPriceString = goodsPriceString + ",";
        }
        goodsPrice = goodsPriceString;
        orderCommand.setGoodsPrice(goodsPrice);
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

        // bank return code, success time and payment status need to be set after payment
        Date date = Calendar.getInstance().getTime();
        Payment payment = new Payment();
        payment.setAmount(paymentCommand.getAmount());
        Currency currency;
        try {
            currency = currencyService.findByName(paymentCommand.getCurrencyName());
            if (currency == null) {
                currency = currencyService.findByName(ResourceProperties.CURRENCY_USD_NAME);
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Currency format is not right.");
        }
        Order order;
        try {
            order = orderService.get(paymentCommand.getOrderId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Order doesn't exist.");
        }
        payment.setOrder(order);

        payment.setCurrency(currency);
        payment.setBankAccountExpDate(date);
        payment.setBankTransactionNumber(paymentCommand.getBankTransactionNumber());
        payment.setBankName(paymentCommand.getBankName());
        payment.setBankAccountNumber(paymentCommand.getBankAccountNumber());
        payment.setRemark(paymentCommand.getRemark());
        payment.setBillFirstName(paymentCommand.getBillFirstName());
        payment.setBillLastName(paymentCommand.getBillLastName());
        payment.setBillAddress1(paymentCommand.getBillAddress1());
        payment.setBillAddress2(paymentCommand.getBillAddress1());
        payment.setBillCity(paymentCommand.getBillCity());
        payment.setBillState(paymentCommand.getBillState());
        payment.setBillCountry(paymentCommand.getBillCountry());
        payment.setBillZipCode(paymentCommand.getBillZipCode());

        PaymentType paymentType;
        String paymentTypeCode = "100";
        if (paymentCommand.getPayMethod().equals("1")) {
            paymentTypeCode = "101"; // 1 means debit card
        }
        try {
            paymentType = paymentTypeService.findByCode(paymentTypeCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Payment type does not exist.");
        }
        payment.setPaymentType(paymentType);

        return payment;
    }

    private String hooppay(List<BasicNameValuePair> params) {

        String result = "";
        // properties params
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

        HttpPost httpPost = new HttpPost(payUrl);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            logger.info("Hooppay return " + httpResponse.getStatusLine());
            result = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consume(httpResponse.getEntity());
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        try {
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        return result;
    }

    private List<BasicNameValuePair> formulateHooppayParams(PaymentCommand paymentCommand,
                                                            OrderCommand orderCommand,
                                                            HttpServletRequest request) {
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
        String md5Info = MDUtil.getMD5Str(merchantId + orderCommand.getOrderNo()
                + orderCommand.getAmount() + orderCommand.getCurrency()
                + merKey + orderCommand.getEmail());
        pairs.add(new BasicNameValuePair("md5Info", md5Info));

        // parse payment command and add those to pairs

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
        pairs.add(new BasicNameValuePair("repayment", "N"));


        // parse order command and add those to pairs
        pairs.add(new BasicNameValuePair("orderNo", orderCommand.getOrderNo()));
        pairs.add(new BasicNameValuePair("email", orderCommand.getEmail()));
        pairs.add(new BasicNameValuePair("phone", orderCommand.getPhone()));
        pairs.add(new BasicNameValuePair("returnURL", orderCommand.getReturnUrl()));
        pairs.add(new BasicNameValuePair("productType", orderCommand.getProductType()));
        pairs.add(new BasicNameValuePair("amount", orderCommand.getAmount()));
        pairs.add(new BasicNameValuePair("currency", orderCommand.getCurrency()));
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
