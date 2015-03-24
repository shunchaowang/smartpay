package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.UserCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesUser;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/18/2015.
 */
@Controller
@RequestMapping("/admin/user")
@Secured({"ROLE_ADMIN"})
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

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
        return "admin/user";
    }

    @ModelAttribute("userStatuses")
    public List<UserStatus> userStatuses() {
        return userStatusService.getAll();
    }

    @RequestMapping(value = {"/{subDomain}", "/index{subDomain}"}, method = RequestMethod.GET)
    public String index(@PathVariable("subDomain") String subDomain, Model model) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        model.addAttribute("subDomain", subDomain);
        return "main";
    }

    @RequestMapping(value = "/list{subDomain}", method = RequestMethod.GET)
    public
    @ResponseBody
    String list(HttpServletRequest request, @PathVariable("subDomain") String subDomain) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        // form role code based on the role parameter
        String roleCode = UserCommand.Role.valueOf(subDomain).getCode();
        // create Role object for query criteria
        Role criteriaRole;
        try {
            criteriaRole = roleService.findByCode(roleCode);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        // create User criteria and attached the role
        User criteriaUser = new User();
        criteriaUser.setRoles(new HashSet<Role>());
        criteriaUser.getRoles().add(criteriaRole);

        // parse sorting column
        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");
        logger.debug("Search Cri: " + search);

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<User> users = userService.findByCriteria(criteriaUser, search, start, length,
                order, ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Long recordsTotal = userService.countByCriteria(criteriaUser);
        // count records filtered
        Long recordsFiltered = userService.countByCriteria(criteriaUser, search);

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

    @RequestMapping(value = "/create{subDomain}", method = RequestMethod.GET)
    public String create(Model model, @PathVariable("subDomain") String subDomain) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }
        model.addAttribute("subDomain", subDomain);
        model.addAttribute("action", "create");
        model.addAttribute("userCommand", new UserCommand());
        return "main";
    }

    @RequestMapping(value = "/create{subDomain}", method = RequestMethod.POST)
    public String save(Model model, @PathVariable("subDomain") String subDomain,
                       @ModelAttribute("userCommand") UserCommand userCommand) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }

        // set subDomain to model
        model.addAttribute("subDomain", subDomain);
        // form role code based on the role parameter
        String roleCode = UserCommand.Role.valueOf(subDomain).getCode();
        Role subDomainRole = null;
        try {
            subDomainRole = roleService.findByCode(roleCode);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find role " + roleCode);
            e.printStackTrace();
        }
        Locale locale = LocaleContextHolder.getLocale();

        // check if username already taken
        if (userService.findByUsername(userCommand.getUsername()) != null) {

            String fieldLabel = messageSource.getMessage("username.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("action", "create");
            return "main";
        }
        // check if email already taken
        if (userService.findByEmail(userCommand.getEmail()) != null) {

            String fieldLabel = messageSource.getMessage("email.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getEmail()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("action", "create");
            return "main";
        }
        //TODO check if all required fields filled

        // create User and set admin to user
        User user = createUser(userCommand, subDomainRole);
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
    @RequestMapping(value = "/show{subDomain}/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, @PathVariable("subDomain") String subDomain,
                       Model model) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }

        // set subDomain to model
        model.addAttribute("subDomain", subDomain);
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

        return "main";
    }

    /**
     * Edit a user.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit{subDomain}/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, @PathVariable("subDomain") String subDomain,
                       Model model) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }

        // set subDomain to model
        model.addAttribute("subDomain", subDomain);
        model.addAttribute("action", "edit");
        // get user by id
        User user;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // create command user and add to model and view
        UserCommand userCommand = createUserCommand(user);
        model.addAttribute("userCommand", userCommand);

        return "main";
    }

    /**
     * Save a user.
     *
     * @param userCommand
     * @return
     */
    @RequestMapping(value = "/edit{subDomain}", method = RequestMethod.POST)
    public String update(Model model, @PathVariable("subDomain") String subDomain,
                         @ModelAttribute("userCommand") UserCommand userCommand) {

        if (UserCommand.Role.valueOf(subDomain) == null) {
            throw new BadRequestException("400", "No role " + subDomain + " found.");
        }

        // set subDomain to model
        model.addAttribute("subDomain", subDomain);
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
                String fieldLabel = messageSource.getMessage("email.label", null, locale);
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

        try {
            userService.update(user);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
        } catch (NotUniqueException e) {
            e.printStackTrace();
        }

        return "main";
    }

    /**
     * ajax calls to delete a user by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    String delete(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        User user;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("User.label", null, locale);
        try {
            user = userService.delete(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notDeleteMessage = messageSource.getMessage("not.deleted.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notDeleteMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String deletedMessage = messageSource.getMessage("deleted.message",
                new String[]{label, user.getUsername()}, locale);
        response.setMessage(deletedMessage);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
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
