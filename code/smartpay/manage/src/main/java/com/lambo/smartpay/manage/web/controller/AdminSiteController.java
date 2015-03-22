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
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;



/**
 * Created by linly on 3/16/2015.
 */
@Controller
@RequestMapping("/admin/site")
public class AdminSiteController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSiteController.class);

    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteStatusService siteStatusService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "admin/site";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Site";
    }

    @ModelAttribute("siteStatuses")
    public List<SiteStatus> siteStatuses() {
        return siteStatusService.getAll();
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        logger.debug("I've been through here ~~~~~~~~~~ 1 ");
        return "main";
    }

    /*
    @RequestMapping(value = {"{subDomain}", "/index{subDomain}"}, method = RequestMethod.GET)
    public String index(@PathVariable("subDomain") String subDomain, Model model) {

        if (SiteCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        model.addAttribute("subDomain", subDomain);
        return "main";
    }
    */

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        logger.debug("I've been through here ~~~~~~~~~~ 2 ");

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

    @RequestMapping(value = "/auditList", method = RequestMethod.GET)
    public String audit(Model model) {
        logger.debug("I've been through here ~~~~~~~~~~ 3 ");

        model.addAttribute("action", "auditList");
        return "main";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/auditList", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String audit(HttpServletRequest request) {

        logger.debug("I've been through here ~~~~~~~~~~ 4 ");


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

    /*

    @RequestMapping(value = "/auditSite", method = RequestMethod.GET)
    public String auditSite(Model model) {
        logger.debug("I've been through here ~~~~~~~~~~ 5 ");

        model.addAttribute("action", "audit");
        return "main";
    }
    */

    @RequestMapping(value = "/auditSite/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {


        logger.debug("I've been through here ~~~~~~~~~~ 555 ");

        /*
        if (SiteCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        */

        // set subDomain to model
        //model.addAttribute("subDomain", subDomain);
        model.addAttribute("action", "auditSite");
        // get user by id
        Site site;
        SiteStatus siteStatus ;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // Modified SiteCommand & add to model and view
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);

        return "main";
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
/*
        // create Site and set admin to site
        Site site = createSiteCommand(siteCommand);
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
        */
        return "main";

    }



    // create SiteCommand from User
    private SiteCommand createSiteCommand(Site site) {
        SiteCommand SiteCommand = new SiteCommand();
        SiteCommand.setId(site.getId());
        SiteCommand.setName(site.getName());
        SiteCommand.setUrl(site.getUrl());
        SiteCommand.setActive(site.getActive());
        SiteCommand.setCreatedTime(site.getCreatedTime());
        SiteCommand.setRemark(site.getRemark());
        if (site.getMerchant() != null) {
            SiteCommand.setMerchant(site.getMerchant().getId());
            SiteCommand.setMerchantName(site.getMerchant().getName());
            logger.debug("SiteMerchant ---" +  SiteCommand.getMerchant());
            logger.debug("SiteMerchant ---" +  SiteCommand.getMerchantName());

        }

        if (site.getSiteStatus() != null) {
            SiteCommand.setSiteStatus(site.getSiteStatus().getId());
            SiteCommand.setSiteStatusName(site.getSiteStatus().getName());
        }
        return SiteCommand;
    }

    /*

    // create a new Site from a SiteCommand
    private Site createSiteCommand(SiteCommand siteCommand) {
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

*/


    // edit a User from a SiteCommand
    private void auditSiteCommond(Site site,SiteCommand siteCommand) {

        site.setName(siteCommand.getName());
        site.setUrl(siteCommand.getUrl());
        site.setRemark(site.getRemark());
       // site.setSiteStatus(site.getSiteStatus());

        // set SiteStatus
        SiteStatus siteStatus = null;
        try {
            siteStatus = siteStatusService.get(siteCommand.getSiteStatus());
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find site status " + siteCommand.getSiteStatus());
            e.printStackTrace();
        }
        site.setSiteStatus(siteStatus);

        //return site;
    }

}
