package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.manage.web.vo.UserCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesUser;
import com.lambo.smartpay.persistence.entity.Role;
import com.lambo.smartpay.persistence.entity.User;
import com.lambo.smartpay.persistence.entity.UserStatus;
import com.lambo.smartpay.service.RoleService;
import com.lambo.smartpay.service.UserService;
import com.lambo.smartpay.service.UserStatusService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
 * Created by swang on 3/15/2015.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "user";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleService.getAll();
    }

    @ModelAttribute("userStatuses")
    public List<UserStatus> userStatuses() {
        return userStatusService.getAll();
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

        List<User> users = userService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(StringUtils.upperCase(orderDir)));

        DataTablesResultSet<User> result = new DataTablesResultSet<>();
        result.setData(users);
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
        String orderIndex = request.getParameter("order[0][column]");
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
        List<User> users = null;

        //TODO ADD ACCESS CONTROLL HERE, MERCHANT ADMIN SHOULD ONLY SEE USERS OF THE MERCHANT
        if (StringUtils.isBlank(search)) {
            //users = userService.f
        }
        users = userService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Integer recordsTotal = userService.countByCriteria(search).intValue();

        List<DataTablesUser> dataTablesUsers = new ArrayList<>();

        if (users != null) {
            for (User user : users) {
                DataTablesUser tableUser = new DataTablesUser(user);
                dataTablesUsers.add(tableUser);
            }
        }

        DataTablesResultSet<DataTablesUser> result = new DataTablesResultSet<>();
        result.setData(dataTablesUsers);
        result.setRecordsFiltered(dataTablesUsers.size());
        result.setRecordsTotal(recordsTotal);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN"})
    public String createAdmin(Model model) {
        model.addAttribute("action", "createAdmin");
        model.addAttribute("userCommand", new UserCommand());
        return "main";
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN"})
    public String saveAdmin(Model model, @ModelAttribute("userCommand") UserCommand userCommand) {
        return "main";
    }

}
