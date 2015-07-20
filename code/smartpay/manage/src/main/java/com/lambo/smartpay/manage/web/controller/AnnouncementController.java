package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.AnnouncementService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.AnnouncementCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesAnnouncement;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
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
import com.lambo.smartpay.manage.util.JsonUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chensf on 5/9/2015.
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    @Autowired
    private AnnouncementService anouncementService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String index( Model model) {
        model.addAttribute("_view", "announcement/indexAll");
        return "main";
    }

    @RequestMapping(value = "/list/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {

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

        Announcement anouncementCriteria = new Announcement();

        List<Announcement> anouncements = anouncementService.findByCriteria(anouncementCriteria, search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records and filtered records
        Long recordsTotal = anouncementService.countByCriteria(anouncementCriteria);
        Long recordsFiltered = anouncementService.countByCriteria(anouncementCriteria, search);

        if (anouncements == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesAnnouncement> dataTablesAnnouncements = new ArrayList<>();
        for (Announcement anouncement : anouncements) {
            DataTablesAnnouncement tablesAnnouncement = new DataTablesAnnouncement(anouncement);
            dataTablesAnnouncements.add(tablesAnnouncement);
        }

        DataTablesResultSet<DataTablesAnnouncement> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesAnnouncements);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model
            model) {

        Announcement announcement;
        try {
            announcement = anouncementService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "announcement  " + id + " not found.");
        }
        AnnouncementCommand announcementCommand = createAnnouncementCommand(announcement);
        model.addAttribute("announcementCommand", announcementCommand);

        model.addAttribute("_view", "announcement/show");
        return "main";
    }


    private AnnouncementCommand createAnnouncementCommand(Announcement announcement) {
        AnnouncementCommand announcementCommand = new AnnouncementCommand();

        if (announcement.getId() != null) {
            announcementCommand.setId(announcement.getId());
        }
        if (announcement.getTitle() != null) {
            announcementCommand.setTitle(announcement.getTitle());
        }

        if (announcement.getContent() != null) {
            announcementCommand.setContent(announcement.getContent());
        }

        if (announcement.getCreatedTime() != null) {
            announcementCommand.setCreatedTime(announcement.getCreatedTime());
        }
        return announcementCommand;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        AnnouncementCommand announcementCommand = new AnnouncementCommand();
        model.addAttribute("announcementCommand", announcementCommand);
        model.addAttribute("_view", "announcement/create");
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(Model model,
                       @ModelAttribute("announcementCommand") AnnouncementCommand announcementCommand) {
        model.addAttribute("announcementCommand", new AnnouncementCommand());
        logger.debug("create announcement here");

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields

        Announcement announcement = createAnnouncement(announcementCommand);
        try {
            anouncementService.create(announcement);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }
        model.addAttribute("_view", "announcement/indexAll");
        return "main";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        Announcement announcement;
        try {
            announcement = anouncementService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        AnnouncementCommand announcementCommand = createAnnouncementCommand(announcement);

        model.addAttribute("announcementCommand", announcementCommand);
        model.addAttribute("_view", "announcement/edit");
        return "main";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model,
                       @ModelAttribute("announcementCommand") AnnouncementCommand announcementCommand) {

        model.addAttribute("announcementCommand", announcementCommand);

        // message locale
        Locale locale = LocaleContextHolder.getLocale();

        Announcement announcement = setAnnouncement(announcementCommand);

        try {
            anouncementService.update(announcement);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        model.addAttribute("_view", "announcement/indexAll");
        return "main";
    }

    /**
     * ajax calls to delete a announcement by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String delete(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Announcement announcement;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("announcement.label", null, locale);
        try {
            announcement = anouncementService.delete(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notDeleteMessage = messageSource.getMessage("not.deleted.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notDeleteMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String deletedMessage = messageSource.getMessage("deleted.message",
                new String[]{label, announcement.getTitle()}, locale);
        response.setMessage(deletedMessage);
        return JsonUtil.toJson(response);
    }

    private Announcement createAnnouncement(AnnouncementCommand announcementCommand) {
        Announcement announcement = new Announcement();
        if (announcementCommand.getId() != null) {
            logger.debug("Announcement " + announcementCommand.getId() + "is not null");
            announcement.setId(announcementCommand.getId());
        }
        announcement.setTitle(announcementCommand.getTitle());
        announcement.setContent(announcementCommand.getContent());
        announcement.setActive(true);
        return announcement;
    }

    private Announcement setAnnouncement(AnnouncementCommand announcementCommand) {
        Announcement announcement = new Announcement();
        try {
            announcement = anouncementService.get(announcementCommand.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Announcement " + announcementCommand.getId()
                    + "is null.");
        }
        //set announcement announcementCommand info
        announcement.setTitle(announcementCommand.getTitle());
        announcement.setContent(announcementCommand.getContent());
        announcement.setActive(true);
        return announcement;
    }
}
