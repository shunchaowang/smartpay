package com.lambo.smartpay.pay.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Customer;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.CustomerService;
import com.lambo.smartpay.core.service.CustomerStatusService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.PropertiesLoader;
import com.lambo.smartpay.pay.util.PayConfiguration;
import com.lambo.smartpay.pay.util.ResourceProperties;
import com.lambo.smartpay.pay.web.exception.BadRequestException;
import com.lambo.smartpay.pay.web.vo.OrderCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
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

    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView home() {
        //view.addObject("action", "index");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest request, Model model) {

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

        // create order command object and add to model
        OrderCommand orderCommand = createOrderCommand(request);
        model.addAttribute("orderCommand", orderCommand);

        // Order and Customer should be created here
        // if customer exists using current one
        Customer customer = customerService.findByEmail(email);
        if (customer == null) {
            customer = new Customer();
            customer.setFirstName(shipFirstName);
            customer.setLastName(shipLastName);
            customer.setEmail(email);
        }


        return "pay";
    }

    @RequestMapping(value = {"/payByCard"}, method = RequestMethod.POST)
    public String payByCard(HttpServletRequest request) {

        Date date = Calendar.getInstance().getTime();
        DecimalFormat decimalFormat = new DecimalFormat("###.##");

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
        String md5Info = formatString(request.getParameter("merMd5info"));
        logger.debug("MD5 summary is " + md5Info);
        if (StringUtils.isBlank(md5Info)) {
            throw new BadRequestException("400", "MD5 summary is blank.");
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
}
