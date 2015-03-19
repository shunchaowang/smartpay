package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.SiteCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesSite;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.Site;
import com.lambo.smartpay.persistence.entity.SiteStatus;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.service.SiteService;
import com.lambo.smartpay.service.SiteStatusService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by linly on 3/16/2015.
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteStatusService siteStatusService;
    @Autowired
    private MerchantService merchantService;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "site";
    }

    @ModelAttribute("siteStatuses")
    public List<SiteStatus> siteStatuses() {
        return siteStatusService.getAll();
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        // parse sorting column
        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<Site> sites = siteService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records and filtered records
        Long recordsTotal = siteService.countAll();
        Long recordsFiltered = siteService.countByCriteria(search);

        if (sites == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesSite> dataTablesSites = new ArrayList<>();

        for (Site site : sites) {
            DataTablesSite tableSite = new DataTablesSite(site);
            dataTablesSites.add(tableSite);
        }

        DataTablesResultSet<DataTablesSite> result = new DataTablesResultSet<>();
        result.setData(dataTablesSites);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public String audit(Model model) {
        model.addAttribute("action", "audit");
        return "main";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/auditList", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String audit(HttpServletRequest request) {

        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }
        Site siteApproved = new Site();
        SiteStatus approvedStatus = null;
        try {
            approvedStatus = siteStatusService.findByCode("400");
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find SiteStatus with Code 400");
            e.printStackTrace();
            return null;
        }
        siteApproved.setSiteStatus(approvedStatus);

        List<Site> sites = siteService.findByCriteria(siteApproved, search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records and filtered records
        //TODO SHOULD PASS sititeApproved here,
        // eg countByCriteria(siteApproved)
        // countByCriteria(siteApproved, search)
        Long recordsTotal = siteService.countAll();
        Long recordsFiltered = siteService.countByCriteria(search);

        if (sites == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesSite> dataTablesSites = new ArrayList<>();

        for (Site site : sites) {
            DataTablesSite tableSite = new DataTablesSite(site);
            dataTablesSites.add(tableSite);
        }

        DataTablesResultSet<DataTablesSite> result = new DataTablesResultSet<>();
        result.setData(dataTablesSites);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }


    @Secured({"ROLE_MERCHANT_ADMIN", "ROLE_MERCHANT_OPERATOR"})
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("siteCommand", new SiteCommand());
        return "main";
    }

    @Secured({"ROLE_MERCHANT_ADMIN", "ROLE_MERCHANT_OPERATOR"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand) {
        // get admin role
        /*Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find role " + ResourceProperties.ROLE_ADMIN_CODE);
            e.printStackTrace();
        }
        */

        // create Site and set admin to site
        Site site = createSite(siteCommand);
        // set initial password
        // persist site
        try {
            site = siteService.create(site);
        } catch (MissingRequiredFieldException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (NotUniqueException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        //TODO SHOULD REDIRECT TO SHOW VIEW OF THE SITE
        model.addAttribute("action", "index");
        return "main";
    }


    // create a new Site from a SiteCommand
    private Site createSite(SiteCommand siteCommand) {
        Site site = new Site();
        site.setName(siteCommand.getName());
        site.setUrl(siteCommand.getUrl());
        site.setRemark(siteCommand.getRemark());
        // we set site to be active right now
        site.setActive(true);

        // set site merchant if site is not admin
        if (siteCommand.getMerchant() != null) {
            Merchant merchant = null;
            try {
                merchant = merchantService.get(siteCommand.getMerchant());
            } catch (NoSuchEntityException e) {
                logger.info("Cannot find merchant " + siteCommand.getMerchant());
                e.printStackTrace();
            }
            site.setMerchant(merchant);
        }

        // set SiteStatus
        SiteStatus siteStatus = null;
        try {
            siteStatus = siteStatusService.get(siteCommand.getSiteStatus());
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find site status " + siteCommand.getSiteStatus());
            e.printStackTrace();
        }
        site.setSiteStatus(siteStatus);
        return site;
    }
}
