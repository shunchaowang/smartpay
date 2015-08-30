package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.CurrencyExchange;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.service.*;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.CurrencyExCommand;
import com.lambo.smartpay.manage.web.vo.SiteCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesCurrencyEx;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesSite;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by chensf on 8/29/2015.
 */
@Controller
@RequestMapping("/currency")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/index/exchange"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "currency/indexAll");
        return "main";
    }

    @RequestMapping(value = "/list/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {
        List<CurrencyExchange> currencyExchanges = currencyExchangeService.getAll();
        List<DataTablesCurrencyEx> dataTablesCurrencyExs = new ArrayList<>();
        for (CurrencyExchange currencyExchange : currencyExchanges) {
            DataTablesCurrencyEx tablesCurrencyEx = new DataTablesCurrencyEx(currencyExchange);
            dataTablesCurrencyExs.add(tablesCurrencyEx);
        }

        DataTablesResultSet<DataTablesCurrencyEx> result = new DataTablesResultSet<>();
        result.setData(dataTablesCurrencyExs);
        result.setRecordsFiltered(dataTablesCurrencyExs.size());
        result.setRecordsTotal(dataTablesCurrencyExs.size());

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {

        String id = request.getParameter("exchangeId");
        if (StringUtils.isBlank(id)) {
            throw new BadRequestException("400", "CurrencyExchange id is blank.");
        }
        Long crexid = Long.valueOf(id);
        CurrencyExchange currencyExchange = null;
        try {
            currencyExchange = currencyExchangeService.get(crexid);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "CurrencyExchange " + id + " not found.");
        }

        CurrencyExCommand currencyExCommand = new CurrencyExCommand(currencyExchange);
        ModelAndView view = new ModelAndView("currency/_editDialog");
        view.addObject("currencyExCommand", currencyExCommand);
        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("currencyExchange.label", null, locale);
        CurrencyExchange currencyExchange = editCurrencyExchange(request);
        try {
            currencyExchange = currencyExchangeService.update(currencyExchange);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{currencyExchange.getCurrencyFrom().getName(), label}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{currencyExchange.getCurrencyFrom().getName(), label}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{currencyExchange.getCurrencyFrom().getName(), label}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }


    private CurrencyExchange editCurrencyExchange(HttpServletRequest request) {

        CurrencyExchange currencyExchange = null;
        try {
            currencyExchange = currencyExchangeService.get(Long.valueOf(request.getParameter("exchangeId")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        currencyExchange.setAmountFrom(Double.parseDouble(request.getParameter("tradingUnit")));
        currencyExchange.setAmountTo(Double.parseDouble(request.getParameter("exchangePrice")));
        currencyExchange.setRemark(request.getParameter("remark"));
        currencyExchange.setUpdatedTime(new Date());
        return currencyExchange;
    }
}
