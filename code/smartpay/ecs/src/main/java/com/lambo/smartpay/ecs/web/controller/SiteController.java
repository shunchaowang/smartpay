package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.SiteCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesSite;
import com.lambo.smartpay.ecs.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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


/**
 * Created by linly on 3/16/2015.
 */
@Controller
@RequestMapping("/site")
@Secured({"ROLE_MERCHANT_ADMIN", "ROLE_MERCHANT_OPERATOR"})
public class SiteController {

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

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
        return "site";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Site";
    }

    @ModelAttribute("siteStatuses")
    public List<SiteStatus> siteStatuses() {
        return siteStatusService.getAll();
    }

    @ModelAttribute("allMerchants")
    public List<Merchant> allMerchants() {
        return merchantService.getAll();
    }

    @RequestMapping(value = {"", "/index", "/indexSite"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = {"/indexDeclineList"}, method = RequestMethod.GET)
    public String indexDeclineList(Model model) {
        model.addAttribute("domain", "DeclineList");
        return "main";
    }

    @RequestMapping(value = "/list{domain}", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listDomain(@PathVariable("domain") String domain, HttpServletRequest request) {

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

        //
        String codeString = "";
        List<Site> sites = null;
        Long recordsTotal;
        Long recordsFiltered;

        Site siteCriteria = new Site();
        User currentUser = UserResource.getCurrentUser();
        siteCriteria.setMerchant(currentUser.getMerchant());

        sites = siteService.findByCriteria(siteCriteria, search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records and filtered records
        recordsTotal = siteService.countByCriteria(siteCriteria);
        recordsFiltered = siteService.countByCriteria(siteCriteria,search);

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

        return JsonUtil.toJson(result);
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        model.addAttribute("action", "create");
        model.addAttribute("siteCommand", new SiteCommand());
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand) {

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields
        // check uniqueness
        if (merchantService.findByName(siteCommand.getName()) != null) {
            String fieldLabel = messageSource.getMessage("name.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, siteCommand.getName()}, locale));
            model.addAttribute("siteCommand", siteCommand);
            model.addAttribute("action", "create");
        }

        Site site = createSite(siteCommand);
        try {
            siteService.create(site);
            String fieldLabel = messageSource.getMessage("Site.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName()+site.getUrl()}, locale));
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("Site.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName()+site.getUrl()}, locale));
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("Site.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName()+site.getUrl()}, locale));
            throw new IntervalServerException("500", e.getMessage());
        }

        return "main";

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        SiteCommand siteCommand = createSiteCommand(site);

        model.addAttribute("siteCommand", siteCommand);
        model.addAttribute("action", "edit");
        return "main";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model,
                       @ModelAttribute("siteCommand") SiteCommand siteCommand) {

        model.addAttribute("siteCommand", siteCommand);

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        Site site = createSite(siteCommand);

        try {
            siteService.update(site);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        return "main";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String delete(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Site site;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Site.label", null, locale);
        try {
            site = siteService.delete(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notDeleteMessage = messageSource.getMessage("not.deleted.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notDeleteMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String deletedMessage = messageSource.getMessage("deleted.message",
                new String[]{label, site.getName()}, locale);
        response.setMessage(deletedMessage);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String approve(@RequestParam(value = "id") Long id) {

        //Initiate
        Site site;
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Site.label", null, locale);
        String message = "";
        //Do approve
        try {
            site = siteService.approveSite(id);
            logger.debug("~~~~~~~~~~ approve ~~~~~~~~~~" + "id= " + id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.approve.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("approve.message", new String[]{label, site.getName()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/show{domain}/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("domain") String domain, @PathVariable("id") Long id, Model
            model) {

        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = createSiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);
        if (domain != null) {
            model.addAttribute("domain", domain);
        }
        model.addAttribute("action", "show");
        return "main";
    }

    // create SiteCommand from User
    private SiteCommand createSiteCommand(Site site) {
        //
        SiteCommand SiteCommand = new SiteCommand();
        //
        SiteCommand.setId(site.getId());
        SiteCommand.setIdentity(site.getIdentity());
        SiteCommand.setName(site.getName());
        SiteCommand.setUrl(site.getUrl());
        SiteCommand.setCreatedTime(site.getCreatedTime());
        SiteCommand.setRemark(site.getRemark());
        SiteCommand.setActive(site.getActive());


        if (site.getSiteStatus() != null) {
            SiteCommand.setSiteStatusId(site.getSiteStatus().getId());
            SiteCommand.setSiteStatusName(site.getSiteStatus().getName());
        }
        return SiteCommand;
    }

    // create SiteCommand from User
    private Site createSite(SiteCommand siteCommand) {
        //
        Site site = new Site();
        SiteStatus siteStatus = null;

        //
        if (siteCommand.getId() != null ){
            try {
                site = siteService.get(siteCommand.getId());
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }

        //Set site status as "1-created"
        try {
            siteStatus = siteStatusService.get(Long.parseLong("1"));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        //
        User user = UserResource.getCurrentUser();
        site.setMerchant(user.getMerchant());
        site.setIdentity(siteCommand.getIdentity());
        site.setName(siteCommand.getName());
        site.setUrl(siteCommand.getUrl());
        site.setSiteStatus(siteStatus);
        site.setActive(true);
        site.setRemark(siteCommand.getRemark());

        return site;
    }
}
