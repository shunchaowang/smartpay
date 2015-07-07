package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Site;
import com.lambo.smartpay.core.persistence.entity.SiteStatus;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.SiteCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesSite;
import com.lambo.smartpay.ecs.web.vo.table.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
    private MessageSource messageSource;

//    @ModelAttribute("siteStatuses")
//    public List<SiteStatus> siteStatuses() {
//        return siteStatusService.getAll();
//    }

    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "site/indexAll");
        return "main";
    }


    @RequestMapping(value = "/list/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);

        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }
        List<Site> sites = null;
        Long recordsTotal;
        Long recordsFiltered;

        Site siteCriteria = new Site();
        User currentUser = UserResource.getCurrentUser();
        siteCriteria.setMerchant(currentUser.getMerchant());

        sites = siteService.findByCriteria(siteCriteria, params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records and filtered records
        recordsTotal = siteService.countByCriteria(siteCriteria);
        recordsFiltered = siteService.countByCriteria(siteCriteria, params.getSearch());

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

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            throw new BadRequestException("400", "User has not logged in.");
        }
        Merchant merchant = securityUser.getMerchant();
        Site siteCriteria = new Site();
        siteCriteria.setMerchant(merchant);
        Long count = siteService.countByCriteria(siteCriteria);
        String identity = "S" + String.format("%07d", count);
        while (siteService.findByIdentity(identity) != null) {
            count++;
            identity = "S" + String.format("%07d", count);
        }
        SiteCommand command = new SiteCommand();
        command.setIdentity(identity);

        model.addAttribute("_view", "site/create");
        model.addAttribute("siteCommand", command);
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand,
                         RedirectAttributes attributes) {

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields
        Long count = siteService.countAll();
        String identity = "S" + String.format("%07d", count);
        while (siteService.findByIdentity(identity) != null) {
            count++;
            identity = "S" + String.format("%07d", count);
        }
        siteCommand.setIdentity(identity);
        // check uniqueness
        if (siteService.findByUrl(siteCommand.getUrl()) != null) {
            String fieldLabel = messageSource.getMessage("site.url.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, siteCommand.getName()}, locale));
            model.addAttribute("siteCommand", siteCommand);
            model.addAttribute("_view", "site/create");
        }

        Site site = createSite(siteCommand);
        try {
            siteService.create(site);
            String fieldLabel = messageSource.getMessage("site.label", null, locale);
            attributes.addFlashAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName() + site.getUrl()}, locale));
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("site.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName() + site.getUrl()}, locale));
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("site.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, site.getName() + site.getUrl()}, locale));
            throw new IntervalServerException("500", e.getMessage());
        }

        return "redirect:/site/index/all";

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

        SiteCommand siteCommand = new SiteCommand(site);

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

        return "redirect:/site/index";
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

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = new SiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);

        model.addAttribute("action", "show");
        return "main";
    }

    // create SiteCommand from User
    private Site createSite(SiteCommand siteCommand) {

        Site site = new Site();
        SiteStatus siteStatus = null;

        if (siteCommand.getId() != null) {
            try {
                site = siteService.get(siteCommand.getId());
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }

        //Set site status as "Created"
        try {
            siteStatus = siteStatusService.findByCode(ResourceProperties.SITE_STATUS_CREATED_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        //
        User user = UserResource.getCurrentUser();
        site.setMerchant(user.getMerchant());
        site.setIdentity(siteCommand.getIdentity());
        site.setName(siteCommand.getName());
        site.setUrl(siteCommand.getUrl());
        site.setReturnUrl(siteCommand.getReturnUrl());
        site.setSiteStatus(siteStatus);
        site.setActive(true);
        site.setRemark(siteCommand.getRemark());

        return site;
    }
}
