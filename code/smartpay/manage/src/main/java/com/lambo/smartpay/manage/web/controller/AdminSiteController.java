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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
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

    //@RequestMapping(value = "/auditSite/{id}/{operation}", method = RequestMethod.GET)
    //public String edit(@PathVariable("id") Long id,@PathVariable("operation") String operation,

    @RequestMapping(value = "/showAuditList", method = RequestMethod.GET)
    public String showAuditList(Model model) {
        logger.debug("I've been through here ~~~~~~~~~~ 3 ");

        model.addAttribute("action", "showAuditList");
        return "main";
    }

    //TODO NEED TO CHANGE METHOD NAME
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/showAuditList", method = RequestMethod.GET, produces =
            "application/json")
    public
    @ResponseBody
    String showAuditList(HttpServletRequest request) {

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

    @RequestMapping(value = "/showFreezeList", method = RequestMethod.GET)
    public String showFreezeList(Model model) {
        logger.debug("I've been through here ~~~~~~~~~~ 3 ");

        model.addAttribute("action", "showFreezeList");
        return "main";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/showFreezeList", method = RequestMethod.GET, produces =
            "application/json;charset=UTF-8")
    public
    @ResponseBody
    String showFreezeList(HttpServletRequest request) {

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
            approvedStatus = siteStatusService.findByCode("500");
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find SiteStatus with Code 500");
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

    @RequestMapping(value = "/showUnfreezeList", method = RequestMethod.GET)
    public String showUnfreezeList(Model model) {
        logger.debug("I've been through here ~~~~~~~~~~ 3 ");

        model.addAttribute("action", "showUnfreezeList");
        return "main";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/showUnfreezeList", method = RequestMethod.GET, produces =
            "application/json;charset=UTF-8")
    public
    @ResponseBody
    String showUnfreezeList(HttpServletRequest request) {

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
            approvedStatus = siteStatusService.findByCode("401");
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find SiteStatus with Code 401");
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

    @RequestMapping(value = "/editSite/{operation}/{id}", method = RequestMethod.GET)
    public String editSite(@PathVariable("id") Long id,@PathVariable("operation") String operation,
                           Model model) {

        logger.debug("editSite ========== ");

        Site site ;
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Site.label", null, locale);
        String message = "";


        //SiteStatus siteStatus ;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }

        if(operation.equals("audit")){
            try{
                site.setSiteStatus(siteStatusService.get(Long.parseLong ("2")));
                message = messageSource.getMessage("audit.message",
                        new String[]{label, site.getName()}, locale);
            }catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "SiteStatus  " + id + " not found.");
            }
            model.addAttribute("action", "showAuditList");
        }

        if(operation.equals("freeze")){
            try{
                site.setSiteStatus(siteStatusService.get(Long.parseLong ("3")));
                message = messageSource.getMessage("frozen.message",
                        new String[]{label, site.getName()}, locale);
            }catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "SiteStatus  " + id + " not found.");
            }
            model.addAttribute("action", "showFreezeList");
        }

        if(operation.equals("unfreeze")){
            try{
                site.setSiteStatus(siteStatusService.get(Long.parseLong ("2")));
                message = messageSource.getMessage("unfrozen.message",
                        new String[]{label, site.getName()}, locale);
            }catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "SiteStatus  " + id + " not found.");
            }
            model.addAttribute("action", "showUnfreezeList");
        }

        if(operation.equals("decline")){
            try{
                site.setSiteStatus(siteStatusService.get(Long.parseLong ("4")));
                message = messageSource.getMessage("decline.message",
                        new String[]{label, site.getName()}, locale);
            }catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "SiteStatus  " + id + " not found.");
            }
            model.addAttribute("action", "showAuditList");
        }

        try {
            siteService.update(site);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
        }

        model.addAttribute("message", message);
        return "main";

    }

    @RequestMapping(value = "/showEditInfo/{operation}/{id}", method = RequestMethod.GET)
    public String showEditInfo(@PathVariable("id") Long id,@PathVariable("operation") String operation,
                       Model model) {


        logger.debug("I've been through here ~~~~~~~~~~ 555 ");
        logger.debug("showEditInfo operation" + operation);

        /*
        if (SiteCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        */

        if(operation.equals("auditsite"))
            model.addAttribute("action", "showAuditInfo");
        if(operation.equals("freezesite"))
            model.addAttribute("action", "showFreezeInfo");
        if(operation.equals("unfreezesite"))
            model.addAttribute("action", "showUnfreezeInfo");

        Site site;
        //SiteStatus siteStatus ;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }

        // Modified SiteCommand & add to model and view
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);

        return "main";
    }

    @RequestMapping(value = "/showInfo/{id}", method = RequestMethod.GET)
    public String showInfo(@PathVariable("id") Long id, Model model) {
        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "showInfo");
        return "main";
    }


    @RequestMapping(value = "/showAuditInfo/{id}", method = RequestMethod.GET)
    public String showAuditInfo(@PathVariable("id") Long id, Model model) {
        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "showAuditInfo");
        return "main";
    }

    @RequestMapping(value = "/showFreezeInfo/{id}", method = RequestMethod.GET)
    public String showFreezeInfo(@PathVariable("id") Long id, Model model) {
        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "showFreezeInfo");
        return "main";
    }

    @RequestMapping(value = "/showUnfreezeInfo/{id}", method = RequestMethod.GET)
    public String showUnfreezeInfo(@PathVariable("id") Long id, Model model) {
        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "showUnfreezeInfo");
        return "main";
    }

    @RequestMapping(value = "/editInfo/{id}", method = RequestMethod.GET)
    public String editInfo(@PathVariable("id") Long id, Model model) {


        logger.debug("I've been through here ~~~~~~~~~~ 555 ");

        Site site;
        //SiteStatus siteStatus ;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }

        // Modified SiteCommand & add to model and view
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "edit");

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



/*

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand) {

        Locale locale = LocaleContextHolder.getLocale();

        // check if username already taken
        if (siteService.findByName(siteCommand.getName()) != null) {
            String fieldLabel = messageSource.getMessage("name.label", null, locale);
            model.addAttribute("message", messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, siteCommand.getName()}, locale));
            model.addAttribute("siteCommand", siteCommand);
            model.addAttribute("action", "create");
            return "main";
        }

        //TODO check if all required fields filled

        // create User and set admin to user
        Site site = createSiteCommand(siteCommand)
                user = createUser(userCommand, subDomainRole);
        // set initial password
        user.setPassword(passwordEncoder.encode(ResourceProperties.INITIAL_PASSWORD));
        // persist user
        try {
            user = userService.create(user);
        } catch (MissingRequiredFieldException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (NotUniqueException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        //TODO SHOULD REDIRECT TO SHOW VIEW OF THE USER
        model.addAttribute("action", "index");
        return "main";
    }
*/

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

    // create SiteCommand from User
    private Site creatSite(SiteCommand siteCommand) {

        Site site = new Site();
        //site.setId() = siteCommand.getId();
        site.setName(siteCommand.getName());
        site.setUrl(site.getUrl());
        //site.setActive(active);
        //site.getRemark(siteCommand.getRemark());



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
        return site;
    }

}
