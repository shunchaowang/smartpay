package com.lambo.smartpay.pay.web.controller;

import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class HomeController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SiteService siteService;
    @RequestMapping(value = {"/", "/index"})
    public ModelAndView home() {
        //view.addObject("action", "index");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/pay"}, method = RequestMethod.POST)
    public String pay(HttpServletRequest req) {

        Date date = Calendar.getInstance().getTime();

        // Parse all parameters from request
        // we need to check if the merchant or the site is frozen
        // if so decline the payment request
        // calculate the md5 of the request based on merchant id and key
        // if not correct decline the payment request
        String merNo = formatString(req.getParameter("merNo"));
        // merchant need to pass a site number for site check
        String siteNo = formatString(req.getParameter("siteNo"));
        String orderNo = formatString(req.getParameter("orderNo"));
        String md5Info = formatString(req.getParameter("merMd5info"));
        String returnURL = formatString(req.getParameter("returnURL"));
        String amount = formatString(req.getParameter("amount"));
        String currency = formatString(req.getParameter("currency"));
        String productType = formatString(req.getParameter("productType"));
        String shopName = formatString(req.getParameter("shopName"));
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
        String acceptLanguage = formatString(req.getParameter("acceptLanguage"));
        String userAgent = formatString(req.getParameter("userAgent"));
        String referer = formatString(req.getParameter("referer"));
        String clientIp = formatString(req.getParameter("clientIp"));
        String language = formatString(req.getParameter("language"));



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
