package com.lambo.smartpay.pay.web.controller;

import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.util.PropertiesLoader;
import com.lambo.smartpay.pay.util.PayConfiguration;
import com.lambo.smartpay.pay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SiteService siteService;

    @RequestMapping(value = {"", "/index"})
    public ModelAndView home() {
        //view.addObject("action", "index");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest req) {

        Date date = Calendar.getInstance().getTime();


        // params obtained from properties
        String payUrl = PayConfiguration.getInstance()
                .getValue(ResourceProperties.HOOPPAY_URL_KEY);
        String merchantId = PayConfiguration.getInstance()
                .getValue(ResourceProperties.MERCHANT_ID_KEY);
        String merKey = PayConfiguration.getInstance().getValue(ResourceProperties.MER_KEY_KEY);
        String referer = PayConfiguration.getInstance().getValue(ResourceProperties.REFERER_KEY);
        String shopName = PayConfiguration.getInstance()
                .getValue(ResourceProperties.SHOP_NAME_KEY);

        // repayment flag needs to be set on UI form

        // Parse all parameters from request
        String acceptLanguage = req.getHeader("Accept-Language");
        String userAgent = req.getHeader("User-Agent");
        Locale locale = req.getLocale();
        String language = locale.getLanguage();
        //is client behind something?
        String clientIp = req.getHeader("X-FORWARDED-FOR");
        if (clientIp == null) {
            clientIp = req.getRemoteAddr();
        }


        // params passed from client
        String merNo = formatString(req.getParameter("merNo"));
        // merchant need to pass a site number for site check
        String siteNo = formatString(req.getParameter("siteNo"));
        String orderNo = formatString(req.getParameter("orderNo"));
        String md5Info = formatString(req.getParameter("merMd5info"));
        String returnURL = formatString(req.getParameter("returnURL"));
        String amount = formatString(req.getParameter("amount"));
        String currency = formatString(req.getParameter("currency"));
        String productType = formatString(req.getParameter("productType"));
        String goodsName = formatString(req.getParameter("goodsName"));
        String goodsNumber = formatString(req.getParameter("goodsNumber"));
        String goodsPrice = formatString(req.getParameter("goodsPrice"));
        String email = formatString(req.getParameter("email"));
        String phone = formatString(req.getParameter("phone"));
        String shipFirstName = formatString(req.getParameter("shipFirstName"));
        String shipLastName = formatString(req.getParameter("shipLastName"));
        String shipAddress = formatString(req.getParameter("shipAddress"));
        String shipCity = formatString(req.getParameter("shipCity"));
        String shipState = formatString(req.getParameter("shipState"));
        String shipCountry = formatString(req.getParameter("shipCountry"));
        String shipZipCode = formatString(req.getParameter("shipZipCode"));
        String remark = formatString(req.getParameter("remark"));

        // params obtained from container or browser
        // del String acceptLanguage = formatString(req.getParameter("acceptLanguage"));
        // del String userAgent = formatString(req.getParameter("userAgent"));

        // del String clientIp = formatString(req.getParameter("clientIp"));
        // del String language = formatString(req.getParameter("language"));

        // we need to check if the merchant or the site is frozen
        if (StringUtils.isBlank(merNo) || StringUtils.isBlank(siteNo)) {
            return "403";
        }
        // if so decline the payment request
        Merchant merchant = merchantService.findByIdentity(merNo);
        Site site = siteService.findByIdentity(siteNo);
        if (merchant == null || site == null) {
            return "403";
        }
        Boolean canOperate = true;
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
}
