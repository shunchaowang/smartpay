package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
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
import java.util.Enumeration;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "main";
    }




    /*
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String list(@RequestParam(value = "search[value]") String search,
                @RequestParam(value = "start") int start,
                @RequestParam(value = "length") int length,
                @RequestParam(value = "order[0][column]") String order,
                @RequestParam(value = "order[0][dir]") String orderDir) {

        logger.debug("search: " + search);
        logger.debug("start: " + start);
        logger.debug("length: " + length);
        logger.debug("order: " + order);
        logger.debug("orderDir: " + orderDir);
        logger.debug("OrderDir: " + StringUtils.upperCase(orderDir));

        List<Site> sites = siteService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(StringUtils.upperCase(orderDir)));

        DataTablesResultSet<Site> result = new DataTablesResultSet<>();
        result.setData(sites);
        result.setRecordsFiltered(1);
        result.setRecordsTotal(1);
        return result.toString();
    }
    */


    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String list(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            logger.debug("Parameter Name - " + paramName + ", Value - " + request.getParameter
                    (paramName));
        }
        // parse sorting column
        Integer orderIndex = Integer.valueOf(request.getParameter("order[0][column]"));
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        logger.debug("Parsed Request: " + search + " " + start + " " + length
                + " " + order + " " + orderDir);
        List<Site> sites = null;
        if (StringUtils.isBlank(search)) {
            //sites = siteService.f
        }
        sites = siteService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Integer recordsTotal = siteService.countByCriteria(search).intValue();

        List<DataTablesSite> dataTablesSites = new ArrayList<>();

        if (sites != null) {
            for (Site site : sites) {
                DataTablesSite tableSite = new DataTablesSite(site);
                dataTablesSites.add(tableSite);
            }
        }

        DataTablesResultSet<DataTablesSite> result = new DataTablesResultSet<>();
        result.setData(dataTablesSites);
        result.setRecordsFiltered(dataTablesSites.size());
        result.setRecordsTotal(recordsTotal);
        logger.debug("Result before return: " + result.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }


    @RequestMapping(value = "/audit", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String audit(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            logger.debug("Parameter Name - " + paramName + ", Value - " + request.getParameter
                    (paramName));
        }
        // parse sorting column
        Integer orderIndex = Integer.valueOf(request.getParameter("order[0][column]"));
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        logger.debug("Parsed Request: " + search + " " + start + " " + length
                + " " + order + " " + orderDir);
        List<Site> sites = null;
        if (StringUtils.isBlank(search)) {
            //sites = siteService.f
        }

        Site siteApproved = new Site();
        SiteStatus approvedStatus;
        try {
            approvedStatus = siteStatusService.findByCode("400");
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find SiteStatus with Code 400");
            e.printStackTrace();
            return null;
        }
        siteApproved.setSiteStatus(approvedStatus);

        sites = siteService.findByCriteria(siteApproved, search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));


        // count total records
        Integer recordsTotal = siteService.countByCriteria(search).intValue();

        List<DataTablesSite> dataTablesSites = new ArrayList<>();

        if (sites != null) {
            for (Site site : sites) {
                DataTablesSite tableSite = new DataTablesSite(site);
                dataTablesSites.add(tableSite);
            }
        }

        DataTablesResultSet<DataTablesSite> result = new DataTablesResultSet<>();
        result.setData(dataTablesSites);
        result.setRecordsFiltered(dataTablesSites.size());
        result.setRecordsTotal(recordsTotal);
        logger.debug("Result before return: " + result.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }


    @RequestMapping(value = "/createSite", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN"})
    public String createSite(Model model) {
        model.addAttribute("action", "createSite");
        model.addAttribute("siteCommand", new SiteCommand());
        return "main";
    }

    @RequestMapping(value = "/createSite", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN"})
    public String saveSite(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand) {
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
