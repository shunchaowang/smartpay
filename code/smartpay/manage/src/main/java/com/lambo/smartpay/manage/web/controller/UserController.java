package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.manage.config.SecurityUser;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.UserCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesUser;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.Role;
import com.lambo.smartpay.persistence.entity.User;
import com.lambo.smartpay.persistence.entity.UserStatus;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.service.RoleService;
import com.lambo.smartpay.service.UserService;
import com.lambo.smartpay.service.UserStatusService;
import com.lambo.smartpay.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

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
    private MerchantService merchantService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        // debugging info
//        Enumeration<String> params = request.getParameterNames();
//        while (params.hasMoreElements()) {
//            String paramName = params.nextElement();
//            logger.debug("Parameter Name - " + paramName + ", Value - " + request.getParameter
//                    (paramName));
//        }
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

        List<User> users = null;

        //TODO ADD ACCESS CONTROLL HERE, MERCHANT ADMIN SHOULD ONLY SEE USERS OF THE MERCHANT
        if (StringUtils.isBlank(search)) {
            //users = userService.f
        }
        users = userService.findByCriteria(search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Long recordsTotal = userService.countAll();
        // count records filtered
        Long recordsFiltered = userService.countByCriteria(search);

        if (users == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesUser> dataTablesUsers = new ArrayList<>();

        for (User user : users) {
            DataTablesUser tableUser = new DataTablesUser(user);
            dataTablesUsers.add(tableUser);
        }

        DataTablesResultSet<DataTablesUser> result = new DataTablesResultSet<>();
        result.setData(dataTablesUsers);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

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
        // get admin role
        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find role " + ResourceProperties.ROLE_ADMIN_CODE);
            e.printStackTrace();
        }
        Locale locale = LocaleContextHolder.getLocale();

        // check if username already taken
        if (userService.findByUsername(userCommand.getUsername()) != null) {

            String fieldLabel = messageSource.getMessage("user.username.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("action", "createAdmin");
            return "main";
        }
        // check if email already taken
        if (userService.findByEmail(userCommand.getEmail()) != null) {

            String fieldLabel = messageSource.getMessage("user.email.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getEmail()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("action", "createAdmin");
            return "main";
        }
        //TODO check if all required fields filled

        // create User and set admin to user
        User user = createUser(userCommand, role);
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

    /**
     * Show user action.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN", "ROLE_MERCHANT_ROLE"})
    public String show(@PathVariable("id") Long id, Model model) {

        model.addAttribute("action", "show");
        // get user by id
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // create command user and add to model and view
        UserCommand userCommand = createUserCommand(user);
        model.addAttribute("userCommand", userCommand);

        SecurityUser securityUser = UserResource.getCurrentUser();
        Role adminRole = null;
        try {
            adminRole = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        // only admin can view all users from all merchants
        // merchant admin can only view users from his merchant
        // if current user is admin return to show
        if (securityUser.getRoles().contains(adminRole)) {
            return "main";
        }

        // current user is merchant admin
        // case 1: shown user is an admin return 403
        if (user.getRoles().contains(adminRole)) {
            return "403";
        }

        // case 2: shown user is not an admin, so he must be the same merchant with current user
        // no matter he is a merchant admin or merchant operator
        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403";
        }

        return "main";
    }

    /**
     * Edit a user.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN", "ROLE_MERCHANT_ROLE"})
    public String edit(@PathVariable("id") Long id, Model model) {

        model.addAttribute("action", "edit");
        // get user by id
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // create command user and add to model and view
        UserCommand userCommand = createUserCommand(user);
        model.addAttribute("userCommand", userCommand);

        SecurityUser securityUser = UserResource.getCurrentUser();
        Role adminRole = null;
        try {
            adminRole = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        // only admin can edit all users from all merchants
        // merchant admin can only edit users from his merchant
        // if current user is admin return to edit
        if (securityUser.getRoles().contains(adminRole)) {
            return "main";
        }

        // current user is merchant admin
        // case 1: if shown user is an admin return 403
        if (user.getRoles().contains(adminRole)) {
            return "403";
        }

        // case 2: shown user is not aa merchant admin, so he must be the same merchant with
        // current user no matter he is a merchant admin or merchant operator
        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403";
        }

        return "main";
    }

    /**
     * Save a user.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MERCHANT_ROLE"})
    public String save(Model model, @ModelAttribute("userCommand") UserCommand userCommand) {

        // if the email is change we need to check uniqueness
        User user = null;
        try {
            user = userService.get(userCommand.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new BadRequestException("400", "User not found.");
        }

        // create command user and add to model and view
        if (!userCommand.getEmail().equals(user.getEmail())) {
            User emailUser = userService.findByEmail(userCommand.getEmail());
            if (emailUser != null) {
                // get locale and messages
                Locale locale = LocaleContextHolder.getLocale();
                String fieldLabel = messageSource.getMessage("user.email.label", null, locale);
                model.addAttribute("message",
                        messageSource.getMessage("not.unique.message",
                                new String[]{fieldLabel, userCommand.getEmail()}, locale));
                model.addAttribute("userCommand", userCommand);
                model.addAttribute("action", "edit");
                return "main";
            }
        }

        // pass uniqueness check create the user
        editUser(user, userCommand);
        SecurityUser securityUser = UserResource.getCurrentUser();
        Role adminRole = null;
        try {
            adminRole = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        // only admin can edit all users from all merchants
        // merchant admin can only edit users from his merchant
        // if current user is admin return to edit
        if (securityUser.getRoles().contains(adminRole)) {
            try {
                userService.update(user);
            } catch (MissingRequiredFieldException e) {
                e.printStackTrace();
            } catch (NotUniqueException e) {
                e.printStackTrace();
            }
            return "main";
        }

        // current user is merchant admin
        // case 1: if editing user is an admin return 403
        if (user.getRoles().contains(adminRole)) {
            return "403";
        }

        // case 2: shown user is not a merchant admin, so he must be the same merchant with
        // current user no matter he is a merchant admin or merchant operator
        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403";
        }
        // user is a user edited by merchant admin
        try {
            userService.update(user);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
        }
        return "main";
    }

    // create a new User from a UserCommand
    private User createUser(UserCommand userCommand, Role role) {
        User user = new User();
        if (userCommand.getId() != null) {
            user.setId(userCommand.getId());
        }
        user.setUsername(userCommand.getUsername());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setEmail(userCommand.getEmail());
        user.setRemark(userCommand.getRemark());
        // we set user to be active right now
        user.setActive(true);
        user.setRoles(new HashSet<Role>());
        user.getRoles().add(role);
        // set user merchant if user is not admin
        if (userCommand.getMerchant() != null) {
            Merchant merchant = null;
            try {
                merchant = merchantService.get(userCommand.getMerchant());
            } catch (NoSuchEntityException e) {
                logger.info("Cannot find merchant " + userCommand.getMerchant());
                e.printStackTrace();
            }
            user.setMerchant(merchant);
        }

        // set UserStatus
        UserStatus userStatus = null;
        try {
            userStatus = userStatusService.get(userCommand.getUserStatus());
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find user status " + userCommand.getUserStatus());
            e.printStackTrace();
        }
        user.setUserStatus(userStatus);
        return user;
    }

    // create UserCommand from User
    private UserCommand createUserCommand(User user) {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setUsername(user.getUsername());
        userCommand.setFirstName(user.getFirstName());
        userCommand.setLastName(user.getLastName());
        userCommand.setEmail(user.getEmail());
        userCommand.setActive(user.getActive());
        userCommand.setCreatedTime(user.getCreatedTime());
        userCommand.setRemark(user.getRemark());
        if (user.getMerchant() != null) {
            userCommand.setMerchant(user.getMerchant().getId());
            userCommand.setMerchantName(user.getMerchant().getName());
        }

        if (user.getUserStatus() != null) {
            userCommand.setUserStatus(user.getUserStatus().getId());
            userCommand.setUserStatusName(user.getUserStatus().getName());
        }
        return userCommand;
    }

    // edit a User from a UserCommand
    private void editUser(User user, UserCommand userCommand) {

        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setEmail(userCommand.getEmail());
        user.setRemark(userCommand.getRemark());

        // set UserStatus
        UserStatus userStatus = null;
        try {
            userStatus = userStatusService.get(userCommand.getUserStatus());
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find user status " + userCommand.getUserStatus());
            e.printStackTrace();
        }
        user.setUserStatus(userStatus);
    }

}
