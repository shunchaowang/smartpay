package com.lambo.smartpay.pay.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.persistence.entity.Currency;
import com.lambo.smartpay.core.service.*;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by swang on 3/11/2015.
 * Pay home.
 */
@Controller
@RequestMapping("/")
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
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @RequestMapping(value = {"index"})
    public ModelAndView home(Model model) {
        //view.addObject("action", "index");
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setMerNo("M0000000");
        orderCommand.setSiteNo("S0000000");
        orderCommand.setOrderNo("O20150829011");
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

    /**
     * ITFPay test payment action.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"paytest"}, method = RequestMethod.POST)
    public String paytest(HttpServletRequest request, HttpServletResponse response, Model model){
        PayUtil payUtil = new PayUtil();

        OrderCommand orderCommand = createOrderCommand(request);
        // create order
        Order order = payUtil.initiateOrder(orderCommand);
        Merchant merchant = merchantService.findByIdentity(orderCommand.getMerNo());
        String merchantKey = merchant.getEncryption().getKey();
        PaymentCommand paymentCommand = createPaymentCommand(request, order);
        paymentCommand.setBankName("V");
        paymentCommand.setBankAccountNumber("1111111111111111");
        paymentCommand.setBillLastName("dfdfdf");
        paymentCommand.setBillFirstName("kkckkjk");
        paymentCommand.setExpireMonth("07");
        paymentCommand.setExpireYear("2019");
        paymentCommand.setBillCountry("US");
        paymentCommand.setBillAddress1("dfkdjfjdlkfjldjflksd");
        paymentCommand.setBillZipCode("33342");
        paymentCommand.setBillCity("fgfgfdfsdf");
        paymentCommand.setBillState("dfdfd");
        paymentCommand.setCvv("456");
        paymentCommand.setPayMethod("1");
        orderCommand.setProductType("dffdf");
        orderCommand.setShipFirstName("kjkjkkjk");
        orderCommand.setShipLastName("df9dkfjkdjf");
        orderCommand.setShipAddress("kdfkdjfkdjkfjd");
        orderCommand.setShipCity("dfvbfgfgdfgdfgfdg");
        orderCommand.setShipState("kkkjkljkjljkl");
        orderCommand.setShipCountry("US");
        orderCommand.setShipZipCode("3985495");
        Payment payment = createPayment(paymentCommand);
        List<BasicNameValuePair> params = formulateITFpayParams(paymentCommand, orderCommand, request);
        String stringFromBase = ITFpay(params);
        //Parameter1  交易号  CHAR(50)
        //Parameter2  订单号  CHAR(50)
        //Parameter3  查询交易号    CHAR(50)
        //Parameter4  返回的 code  CHAR(32)
        //Parameter5  返回的信息  CHAR(500)
        //Parameter6  返回货币代码  CHAR(4)
        //Parameter7  订单金额  Decimal(18,2)
        //Parameter8  加密数据  CHAR(500)
        //Parameter9  返回金额  Decimal(18,2)  人民币
        //Parameter10  返回交易时间  CHAR(20)  YYYYMMDDHHMMSS
        // initial return parameter
        String[] strReturn = stringFromBase.split("&");
        payment.setBankTransactionNumber("0");
        for(int i=0; i< strReturn.length; i++){
            String[] tmpReturn = strReturn[i].split("=");
            if("Parameter3".equals(tmpReturn[0]))payment.setBankTransactionNumber(tmpReturn[1]);
            if("Parameter4".equals(tmpReturn[0]))payment.setBankReturnCode(tmpReturn[1]);
        }
        String succeed = "0";
        String paymentStatusCode = "501";
        if (payment.getBankReturnCode().equals("00")) {
            succeed = "1"; // 交易成功
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
        }else{
            payment.setFee(Float.parseFloat("0.00"));
            payment.setAmount(Float.parseFloat("0.00"));
        }
        payment.setUpdatedTime(Calendar.getInstance().getTime());

        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(paymentStatusCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        payment.setPaymentStatus(paymentStatus);

        try {
            payment = paymentService.create(payment);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        String calculatedMd5Info = MDUtil.getMD5Str(merchantKey+orderCommand.getMerNo()
                + orderCommand.getOrderNo().substring(8) + orderCommand.getAmount()
                + orderCommand.getCurrency() + succeed);

        String result = "succeed=" + succeed + "&amount=" + orderCommand.getAmount()
                + "&orderNo=" + orderCommand.getOrderNo().substring(8)
                + "&currency=" + orderCommand.getCurrency() + "&errcode=" + payment.getBankReturnCode()
                + "&md5info=" + calculatedMd5Info;
        logger.debug("ITFPay result:"+result);
        return result;
    }


    @RequestMapping(value = {"pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest request, HttpServletResponse response, Model model) {

        PayUtil payUtil = new PayUtil();

        if (!payUtil.validateRequestParams(request)) {
            return "403";
        }

        if (!payUtil.ifAllow(request)) {
            return "403";
        }

        // check md5info, md5 summary should be generated based merNo, orderNo, formatted amount,
        // 3 uppercase characters currency, merchantKey and email
        if (!payUtil.validateMd5(request)) {
            try {
                response.sendRedirect(payUtil.generateMd5Response(request, "0",
                        "1002. MD5 Info Checking Error."));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "403";
        }

        // if order status is already paid, don't allow duplicated submission
        if (payUtil.isPaid(request)) {
            try {
                response.sendRedirect(payUtil.generateMd5Response(request, "0",
                        "1004. Do Not Allow Duplicated Submission."));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "403";
        }

        OrderCommand orderCommand = createOrderCommand(request);

        // Order and Customer should be created here
        // if customer exists using current one
        Order order = payUtil.initiateOrder(orderCommand);


        // create order command object and add to model
        orderCommand.setCurrency(order.getCurrency().getName());

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

        //HttpSession session = request.getSession();
        //OrderCommand orderCommand = (OrderCommand) session.getAttribute("orderCommand");
        OrderCommand orderCommand = createOrderCommand(request);
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

    /**
     * Inline payment action.
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inlinePay", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String inlinePay(HttpServletRequest request, HttpServletResponse response) {

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

        // if order status is already paid, don't allow duplicated submission
        Order order = orderService.findByMerchantNumber(orderNo);
        OrderStatus paidOrderStatus = null;
        try {
            paidOrderStatus =
                    orderStatusService.findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", "Cannot find order status.");
        }
        if (order != null && order.getOrderStatus().equals(paidOrderStatus)) {
            String succeed = "0";
            String errcode = "1004. Do Not Allow Duplicated Submission.";
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
            return "403";
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

        return "pay";
    }

    /**
     * ITF payment action.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ITFPay", method = RequestMethod.POST,
            produces = "text/plain;charset=UTF-8")
    public
    @ResponseBody
    String ITFPay(HttpServletRequest request){
        PayUtil payUtil = new PayUtil();

        if (!payUtil.validateRequestParams(request)) {
            return "404. Missing Required Params.";
        }

        if (!payUtil.ifAllow(request)) {
            return "403. Operation Not Allowed.";
        }

        if (!payUtil.validateMd5(request)) {
            return "403. MD5 Checking Error.";
        }

        // if order status is already paid, don't allow duplicated submission
        if (payUtil.isPaid(request)) {
            return "403. Do Not Allow Duplicated Submission.";
        }
        OrderCommand orderCommand = createOrderCommand(request);
        // create order
        Order order = payUtil.initiateOrder(orderCommand);
        Merchant merchant = merchantService.findByIdentity(orderCommand.getMerNo());
        String merchantKey = merchant.getEncryption().getKey();
        // create payment object
        PaymentCommand paymentCommand = createPaymentCommand(request, order);
        Payment payment = createPayment(paymentCommand);
        // create ITFPay params and make the pay request
        List<BasicNameValuePair> params = formulateITFpayParams(paymentCommand, orderCommand, request);

        String stringFromBase = ITFpay(params);
        //Parameter1  交易号  CHAR(50)
        //Parameter2  订单号  CHAR(50)
        //Parameter3  查询交易号    CHAR(50)
        //Parameter4  返回的 code  CHAR(32)
        //Parameter5  返回的信息  CHAR(500)
        //Parameter6  返回货币代码  CHAR(4)
        //Parameter7  订单金额  Decimal(18,2)
        //Parameter8  加密数据  CHAR(500)
        //Parameter9  返回金额  Decimal(18,2)  人民币
        //Parameter10  返回交易时间  CHAR(20)  YYYYMMDDHHMMSS
        // initial return parameter
        String[] strReturn = stringFromBase.split("&");
        payment.setBankTransactionNumber("0");
        for(int i=0; i< strReturn.length; i++){
            String[] tmpReturn = strReturn[i].split("=");
            if("Parameter3".equals(tmpReturn[0]))payment.setBankTransactionNumber(tmpReturn[1]);
            if("Parameter4".equals(tmpReturn[0]))payment.setBankReturnCode(tmpReturn[1]);
        }

        String succeed = "0";
        String paymentStatusCode = "501";
        if (payment.getBankReturnCode().equals("00")) {
            succeed = "1"; // 交易成功
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
        }else{
            payment.setFee(Float.parseFloat("0.00"));
            payment.setAmount(Float.parseFloat("0.00"));
        }
        payment.setUpdatedTime(Calendar.getInstance().getTime());

        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentStatusService.findByCode(paymentStatusCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        payment.setPaymentStatus(paymentStatus);

        try {
            payment = paymentService.create(payment);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        String calculatedMd5Info = MDUtil.getMD5Str(merchantKey+orderCommand.getMerNo()
                + orderCommand.getOrderNo().substring(8) + orderCommand.getAmount()
                + orderCommand.getCurrency() + succeed);

        String result = "succeed=" + succeed + "&amount=" + orderCommand.getAmount()
                + "&orderNo=" + orderCommand.getOrderNo().substring(8)
                + "&currency=" + orderCommand.getCurrency() + "&errcode=" + payment.getBankReturnCode()
                + "&md5info=" + calculatedMd5Info;
        logger.debug("ITFPay result:"+result);
        return result;
    }


    private String ITFpay(List<BasicNameValuePair> params) {
        String result = "";
        // properties params
        // params obtained from properties
        String payUrl = PayConfiguration.getInstance().getValue(ResourceProperties.ITFPAY_URL_KEY);

        HttpPost httpPost = new HttpPost(payUrl);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            logger.debug("request string:"+httpPost.getEntity().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            logger.info("ITFPay return " + httpResponse.getStatusLine());
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

    private List<BasicNameValuePair> formulateITFpayParams(PaymentCommand paymentCommand,
                                                           OrderCommand orderCommand,
                                                           HttpServletRequest request) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

        //AcctNo 交易号 必填
        String AcctNo = PayConfiguration.getInstance().getValue(ResourceProperties.ITFPAY_ACCTNO_KEY);
        pairs.add(new BasicNameValuePair("AcctNo", AcctNo));
        //Agent_AcctNo 商户交易号 必填
        String Agent_AcctNo = PayConfiguration.getInstance().getValue(ResourceProperties.ITFPAY_AGENT_ACCTNO_KEY);
        pairs.add(new BasicNameValuePair("Agent_AcctNo", Agent_AcctNo));
        //OrderID 订单号 （不允许重复大于 10 位）商户的订单号，必填项目
        String OrderID = orderCommand.getOrderNo();
        pairs.add(new BasicNameValuePair("OrderID", OrderID));
        //CurrCode 货币代码 必填项目 CurrCode  =840  代表美元
        String CurrCode = getCurrCode(orderCommand.getCurrency());
        pairs.add(new BasicNameValuePair("CurrCode", CurrCode));
        //Amount 商家交易的金额（以分为单位上传，不保留小数点）,必填项目
        String Amount = BigDecimal.valueOf(Double.parseDouble(orderCommand.getAmount())).multiply(new BigDecimal(100)).toString();
        if(Amount.indexOf(".")>0) Amount = Amount.substring(0,Amount.indexOf("."));
        pairs.add(new BasicNameValuePair("Amount", Amount));
        //IPAddress 持卡人的 IP 地址  必填
        String clientIp = request.getParameter("clientIp");
        if(clientIp ==null || clientIp == "") {
            clientIp = request.getHeader("X-FORWARDED-FOR");
        }
        if (clientIp == null || clientIp == "") {
            clientIp = request.getRemoteAddr();
        }
        logger.debug("Client ip is " + clientIp);
        pairs.add(new BasicNameValuePair("IPAddress", clientIp));
        //CardType 卡种 必填
        pairs.add(new BasicNameValuePair("CardType", paymentCommand.getBankName()));
        //CardPAN 卡号 必填
        pairs.add(new BasicNameValuePair("CardPAN", paymentCommand.getBankAccountNumber()));
        //CName 持卡人姓名 必填
        String CNname = paymentCommand.getBillFirstName()+" "+ paymentCommand.getBillLastName();
        pairs.add(new BasicNameValuePair("CName", CNname.getBytes().length <= 16 ? CNname:CNname.substring(0, 16)));
        //ExpDate 有效期 必填
        pairs.add(new BasicNameValuePair("ExpDate", paymentCommand.getExpireYear().substring(2)+paymentCommand.getExpireMonth()));
        //CVV2 卡背面的三位 必填
        pairs.add(new BasicNameValuePair("CVV2", paymentCommand.getCvv()));
        //Issuer 发卡行
        pairs.add(new BasicNameValuePair("Issuer", paymentCommand.getBankName()));
        //IssCountry 账单国家 必填
        pairs.add(new BasicNameValuePair("IssCountry", paymentCommand.getBillCountry()));
        //BAddress 账单地址 必填
        pairs.add(new BasicNameValuePair("BAddress", paymentCommand.getBillAddress1()));
        //BCity 账单城市 必填
        pairs.add(new BasicNameValuePair("BCity", paymentCommand.getBillCity()));
        //PostCode 账单邮编必填
        pairs.add(new BasicNameValuePair("PostCode", paymentCommand.getBillZipCode()));
        //IVersion 版本号 必填项目（默认 V5.0）
        String ver = PayConfiguration.getInstance().getValue(ResourceProperties.ITFPAY_VER_KEY);
        pairs.add(new BasicNameValuePair("IVersion", ver));
        //Telephone 持卡人电话 必填
        pairs.add(new BasicNameValuePair("Telephone", orderCommand.getPhone()));
        //RetURL 持卡人购物的网站 必填项目（不带 http 和后缀）
        pairs.add(new BasicNameValuePair("RetURL", orderCommand.getReferer()));
        //Email 邮箱地址 必填
        pairs.add(new BasicNameValuePair("Email", orderCommand.getEmail()));

        //HashValue 代理商加密数据 必填项目  HashValue    = MD5(Md5Key + AcctNo + OrderID + Amount +  CurrCode)
        String szHashValue = "";
        String Md5Key = new String(Base64.decodeBase64(PayConfiguration.getInstance().getValue(ResourceProperties.ITFPAY_MD5_KEY)));
        String sz_Key = Md5Key + AcctNo + OrderID + Amount + CurrCode;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
            szHashValue = baseEncoder.encode(md5.digest(sz_Key.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            logger.error("数据签名失败----", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("数据签名失败----", e);
        }
        pairs.add(new BasicNameValuePair("HashValue",szHashValue));
        //BrowserDate浏览器时间
        pairs.add(new BasicNameValuePair("BrowserDate", ""));
        //BrowserDateTimezone 时区
        pairs.add(new BasicNameValuePair("BrowserDateTimezone", ""));
        //BrowserUserAgent BrowserUserAgent 必填
        String BrowserUserAgent = request.getHeader("User-Agent");
        logger.debug("User agent is " + BrowserUserAgent);
        if (BrowserUserAgent == null) {
            BrowserUserAgent = "Mozilla/5.0";
        }
        pairs.add(new BasicNameValuePair("BrowserUserAgent", BrowserUserAgent));
        //BrowserName  浏览器名称  CHAR(32)
        pairs.add(new BasicNameValuePair("BrowserName", BrowserUserAgent.getBytes().length <=32 ? BrowserUserAgent:BrowserUserAgent.substring(0, 32)));
        //BrowserLanguage  浏览器语言  CHAR(50)
        String BrowserLanguage = request.getHeader("Accept-Language");
        if (BrowserLanguage == null) {
            BrowserLanguage = "en_US";
        }
        pairs.add(new BasicNameValuePair("BrowserLanguage", BrowserLanguage));
        //BrowserSystemLanguage 浏览器系统语言  CHAR(50)
        Locale locale = request.getLocale();
        String BrowserSystemLanguage = locale.getLanguage();
        pairs.add(new BasicNameValuePair("BrowserSystemLanguage", BrowserSystemLanguage));
        //Resolution  分辨率  CHAR(20)
        pairs.add(new BasicNameValuePair("Resolution", ""));
        //CommodityBrand  品牌  CHAR(100)  必填项目
        pairs.add(new BasicNameValuePair("CommodityBrand", orderCommand.getProductType()));
        //ShipName  收货名称  CHAR(50)  必填项目
        pairs.add(new BasicNameValuePair("ShipName", orderCommand.getGoodsName()));
        //ShipAddress  地址  CHAR(200)  必填项目
        pairs.add(new BasicNameValuePair("ShipAddress", orderCommand.getShipAddress()));
        //ShipCity  城市  CHAR(30)  必填项目
        pairs.add(new BasicNameValuePair("ShipCity", orderCommand.getShipCity()));
        //Shipstate  州  CHAR(30)  必填项目
        pairs.add(new BasicNameValuePair("Shipstate", orderCommand.getShipState()));
        //ShipCountry  国家  CHAR(30)  必填项目
        pairs.add(new BasicNameValuePair("ShipCountry", orderCommand.getShipCountry()));
        //ShipPostCode  邮编  CHAR(20)  必填项目
        pairs.add(new BasicNameValuePair("ShipPostCode", orderCommand.getShipZipCode()));
        //Shipphone  电话  CHAR(20)  必填项目
        pairs.add(new BasicNameValuePair("Shipphone", orderCommand.getPhone()));
        //CMSName  框架名称  CHAR(20)
        pairs.add(new BasicNameValuePair("CMSName", ""));
        //TxnType  01 代表消费 CHAR（20）  必填
        pairs.add(new BasicNameValuePair("TxnType", "01"));

        return pairs;
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
        String referer = formatString(request.getParameter("referer"));
        orderCommand.setReferer(referer);
        Site site = siteService.findByUrl(referer);
        orderCommand.setSiteNo(site.getIdentity());
        orderCommand.setOrderNo(formatString(site.getIdentity() + request.getParameter("orderNo")));
        orderCommand.setMerNo(request.getParameter("merNo"));
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

    PaymentCommand createPaymentCommand(HttpServletRequest request, Order order) {

        // bank return code, success time and payment status need to be set after payment
        Date date = Calendar.getInstance().getTime();
        PaymentCommand paymentCommand = new PaymentCommand();
        paymentCommand.setBankName(formatString(request.getParameter("bankName")));
        paymentCommand
                .setBankAccountNumber(formatString(request.getParameter("bankAccountNumber")));
        paymentCommand.setExpireMonth(formatString(request.getParameter("expireMonth")));
        paymentCommand.setExpireYear(formatString(request.getParameter("expireYear")));
        paymentCommand.setCvv(formatString(request.getParameter("cvv")));

        paymentCommand.setOrderId(order.getId()); // set payment command order id
        paymentCommand.setAmount(order.getAmount()); // set payment command order amount
        paymentCommand.setCurrencyName(order.getCurrency().getName()); // set currency name

        if(!order.getCurrency().getName().equals(ResourceProperties.CURRENCY_RMB_NAME)) {
            List<CurrencyExchange> currencyExchanges = currencyExchangeService.getAll();
            for(CurrencyExchange currencyExchange : currencyExchanges){
                if(currencyExchange.getActive() == true
                        && currencyExchange.getCurrencyFrom().getName().equals(order.getCurrency().getName())
                        && currencyExchange.getCurrencyTo().getName().equals(ResourceProperties.CURRENCY_RMB_NAME)){
                    BigDecimal orderAmt = new BigDecimal(order.getAmount());
                    BigDecimal fromAmt = new BigDecimal(currencyExchange.getAmountFrom());
                    BigDecimal toAmt = new BigDecimal(currencyExchange.getAmountTo());
                    BigDecimal amt = orderAmt.multiply(toAmt).divide(fromAmt, 3,BigDecimal.ROUND_HALF_DOWN);
                    paymentCommand.setAmount(amt.floatValue());
                    paymentCommand.setCurrencyName(ResourceProperties.CURRENCY_RMB_NAME);
                }
            }
        }
        Merchant merchant = order.getSite().getMerchant();
        Iterator<Fee> feeIterator = merchant.getFees().iterator();
        while(feeIterator.hasNext()){
            Fee fee = feeIterator.next();
            if(fee.getFeeCategory().getName().indexOf(paymentCommand.getBankName().toUpperCase()) >= 0) {
                if(fee.getFeeType().getCode().equals(ResourceProperties.FEE_TYPE_STATIC_CODE))
                        paymentCommand.setFee(fee.getValue());
                else paymentCommand.setFee(paymentCommand.getAmount() * fee.getValue() / 10 * 10);
                if(paymentCommand.getAmount() .compareTo(paymentCommand.getFee()) >= 0)
                    paymentCommand.setAmount(paymentCommand.getAmount() - paymentCommand.getFee());
                else
                    paymentCommand.setAmount(Float.parseFloat("0"));
            }
        }

        paymentCommand.setPayMethod(formatString(request.getParameter("payMethod")));
        paymentCommand.setBillFirstName(formatString(request.getParameter("billFirstName")));
        paymentCommand.setBillLastName(formatString(request.getParameter("billLastName")));
        paymentCommand.setBillAddress1(formatString(request.getParameter("billAddress1")));
        paymentCommand.setBillCity(formatString(request.getParameter("billCity")));
        paymentCommand.setBillState(formatString(request.getParameter("billState")));
        paymentCommand.setBillZipCode(formatString(request.getParameter("billZipCode")));
        paymentCommand.setBillCountry(formatString(request.getParameter("billCountry")));
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        paymentCommand.setPaymentClientIp(clientIp);


        return paymentCommand;
    }

    Payment createPayment(PaymentCommand paymentCommand) {

        // bank return code, success time and payment status need to be set after payment
        Date date = Calendar.getInstance().getTime();
        Payment payment = new Payment();
        payment.setAmount(paymentCommand.getAmount());
        payment.setFee(paymentCommand.getFee());
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
        payment.setClientIp(paymentCommand.getPaymentClientIp());

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


    private class PayUtil {

        private Logger logger = LoggerFactory.getLogger(PayUtil.class);

        public Boolean validateRequestParams(HttpServletRequest request) {

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

            String bankName = formatString(request.getParameter("bankName"));
            logger.debug("bankName is " + bankName);
            if (StringUtils.isBlank(bankName)) {
                throw new BadRequestException("400", "bankName is blank.");
            }

            if("VISA".indexOf(bankName.toUpperCase())<0 && "MASTER".indexOf(bankName.toUpperCase())<0
                    && "JCB".indexOf(bankName.toUpperCase())<0)
                throw new BadRequestException("400", "bankName is illegal.");

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
             // referer is the site url in our system, used for site approval
            String clientIp = formatString(request.getParameter("clientIp"));
            logger.debug("clent_ip is " + clientIp);
            if(clientIp ==null || clientIp == "") {
                clientIp = request.getHeader("X-FORWARDED-FOR");
                logger.debug("clent_ip is " + clientIp);
            }
            if (clientIp == null || clientIp == "") {
                clientIp = request.getRemoteAddr();
                logger.debug("clent_ip is " + clientIp);
            }
            return true;
        }

        public Boolean ifAllow(HttpServletRequest request) {

            // we need to check if the merchant or the site is frozen
            // if so decline the payment request
            String merNo = formatString(request.getParameter("merNo"));
            logger.debug("MerNO...: " + merNo);
            Merchant merchant = merchantService.findByIdentity(merNo);
            String referer = formatString(request.getParameter("referer"));
            Site site = siteService.findByUrl(referer);
            if (merchant == null) {
                logger.debug("Merchant " + merNo + " is null.");
                return false;
            }
            if (site == null) {
                logger.debug("Site " + referer + " is null.");
                return false;
            }
            Boolean canOperate;
            try {
                canOperate = merchantService.canOperate(merchant.getId())
                        && siteService.canOperate(site.getId());
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                return false;
            }
            if (!canOperate) {
                return false;
            }
            return true;
        }

        public Boolean validateMd5(HttpServletRequest request) {

            String merNo = formatString(request.getParameter("merNo"));
            String orderNo = formatString(request.getParameter("orderNo"));
            String amount = formatString(request.getParameter("amount"));
            DecimalFormat decimalFormat = new DecimalFormat("###.00");
            amount = decimalFormat.format(Float.parseFloat(amount));
            String currency = formatString(request.getParameter("currency"));
            String email = formatString(request.getParameter("email"));
            String md5Info = formatString(request.getParameter("md5Info"));
            Merchant merchant = merchantService.findByIdentity(merNo);
            if (merchant == null) {
                logger.debug("Merchant " + merNo + " is null.");
                return false;
            }
            String merchantKey = merchant.getEncryption().getKey();
            // check md5info, md5 summary should be generated based merNo, orderNo, formatted
            // amount,
            // 3 uppercase characters currency, merchantKey and email
            String calculatedMd5Info = MDUtil.getMD5Str(merNo + orderNo + amount + currency
                    + merchantKey + email);
            if (!md5Info.equals(calculatedMd5Info)) {
                return false;
            }
            return true;
        }

        public String generateMd5Response(HttpServletRequest request, String succeed,
                                          String errCode) {
            String merNo = formatString(request.getParameter("merNo"));
            String orderNo = formatString(request.getParameter("orderNo"));
            String amount = formatString(request.getParameter("amount"));
            DecimalFormat decimalFormat = new DecimalFormat("###.00");
            amount = decimalFormat.format(Float.parseFloat(amount));
            String currency = formatString(request.getParameter("currency"));
            String returnURL = formatString(request.getParameter("returnURL"));
            Merchant merchant = merchantService.findByIdentity(merNo);
            if (merchant == null) {
                logger.debug("Merchant " + merNo + " is null.");
                return "";
            }
            String merchantKey = merchant.getEncryption().getKey();
            String resultMd5Info = MDUtil.getMD5Str(merchantKey + merNo + orderNo + amount +
                    currency + succeed);

            return returnURL + "&succeed=" + succeed + "&amount=" +
                    amount + "&orderNo=" + orderNo
                    + "&currency=" + currency + "&errcode=" + errCode + "&md5info=" +
                    resultMd5Info;
        }

        public Boolean isPaid(HttpServletRequest request) {

            String referer = formatString(request.getParameter("referer"));
            Site site = siteService.findByUrl(referer);
            String orderNo = formatString(site.getIdentity() + request.getParameter("orderNo"));
            // if order status is already paid, don't allow duplicated submission
            Order order = orderService.findByMerchantNumber(orderNo);
            OrderStatus paidOrderStatus = null;
            try {
                paidOrderStatus =
                        orderStatusService.findByCode(ResourceProperties.ORDER_STATUS_PAID_CODE);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new IntervalServerException("500", "Cannot find order status.");
            }
            if (order != null && order.getOrderStatus().equals(paidOrderStatus)) {
                return true;
            }

            return false;
        }

        public Order initiateOrder(OrderCommand orderCommand) {

            String email = formatString(orderCommand.getEmail());
            String referer = formatString(orderCommand.getReferer());
            String shipFirstName = formatString(orderCommand.getShipFirstName());
            String shipLastName = formatString(orderCommand.getShipLastName());
            String shipAddress = formatString(orderCommand.getShipAddress());
            String shipCity = formatString(orderCommand.getShipCity());
            String shipState = formatString(orderCommand.getShipState());
            String shipZipCode = formatString(orderCommand.getShipZipCode());
            String shipCountry = formatString(orderCommand.getShipCountry());
            String currency = formatString(orderCommand.getCurrency());
            String orderNo = formatString(orderCommand.getOrderNo());
            String amount = formatString(orderCommand.getAmount());
            DecimalFormat decimalFormat = new DecimalFormat("###.00");
            amount = decimalFormat.format(Float.parseFloat(amount));
            String productType = formatString(orderCommand.getProductType());
            String goodsName = formatString(orderCommand.getGoodsName());
            String goodsNumber = formatString(orderCommand.getGoodsNumber());
            String goodsPrice = formatString(orderCommand.getGoodsPrice());

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

            // if order does not exist create new
            Site site = siteService.findByUrl(referer);
            Order order = orderService.findByMerchantNumber(orderNo);

            if (order == null) {
                // create Order
                order = new Order();
                order.setMerchantNumber(orderNo);
                order.setAmount(Float.valueOf(amount));
                order.setGoodsName(goodsName);
                order.setGoodsAmount(goodsNumber);
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
                order.setGoodsName(goodsName);
                order.setGoodsAmount(goodsNumber);
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

            return order;
        }
    }
    private Map<String, String> hashMap = new HashMap<String, String>() {
        {
            put("AUD", "36");
            put("CAD", "124");
            put("CNY", "156");
            put("DKK", "208");
            put("HKD", "344");
            put("INR", "356");
            put("IDR", "360");
            put("ILS", "376");
            put("JPY", "392");
            put("KRW", "410");
            put("MOP", "446");
            put("MYR", "458");
            put("NOK", "578");
            put("PHP", "608");
            put("RUB", "643");
            put("SGD", "702");
            put("ZAR", "710");
            put("SEK", "752");
            put("CHF", "756");
            put("GBP", "826");
            put("USD", "840");
            put("TWD", "901");
            put("TRY", "949");
            put("EUR", "978");
            put("NZD", "554");
        }
    };


    private String getCurrCode(String currency){
        return hashMap.get(currency);
    }


}
