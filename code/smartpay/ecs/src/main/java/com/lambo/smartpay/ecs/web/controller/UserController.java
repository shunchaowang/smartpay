package com.lambo.smartpay.ecs.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Role;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.persistence.entity.UserStatus;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.RoleService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.core.service.UserStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.ecs.config.SecurityUser;
import com.lambo.smartpay.ecs.util.JsonUtil;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.exception.RemoteAjaxException;
import com.lambo.smartpay.ecs.web.vo.UserCommand;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.ecs.web.vo.table.DataTablesUser;
import com.lambo.smartpay.ecs.web.vo.table.JsonResponse;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/15/2015.
 */
@Controller
@RequestMapping("/user")
@Secured({"MERCHANT_ROLE_ADMIN"})
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

    @ModelAttribute("domain")
    public String domain() {
        return "User";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleService.getAll();
    }

    @ModelAttribute("userStatuses")
    public List<UserStatus> userStatuses() {
        return userStatusService.getAll();
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {

        return "main";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    String list(HttpServletRequest request) {

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


        //Merchant admin can only view the users belonged to this merchant
        SecurityUser principal = UserResource.getCurrentUser();
        if (principal.getMerchant() == null) {
            throw new BadRequestException("400", "User doesn't have merchant.");
        }
        User userCriteria = new User();
        userCriteria.setMerchant(principal.getMerchant());
        List<User> users = userService.findByCriteria(userCriteria, search, start, length, order,
                ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total records
        Long recordsTotal = userService.countByCriteria(userCriteria);
        // count records filtered
        Long recordsFiltered = userService.countByCriteria(userCriteria, search);

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

    /**
     * Merchant admin can only create merchant operator.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("userCommand", new UserCommand());
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(Model model, RedirectAttributes attributes,
                       @ModelAttribute("userCommand") UserCommand userCommand) {
        // get merchant operator role
        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_MERCHANT_OPERATOR_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find role " + ResourceProperties.ROLE_MERCHANT_OPERATOR_CODE);
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
        User user = createUser(userCommand, role);
        // set initial password
        user.setPassword(passwordEncoder.encode(ResourceProperties.INITIAL_PASSWORD));
        // persist user
        try {
            user = userService.create(user);
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            attributes.addFlashAttribute("message",
                    messageSource.getMessage("created.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        } catch (MissingRequiredFieldException e) {
            logger.info(e.getMessage());
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            attributes.addFlashAttribute("message",
                    messageSource.getMessage("not.created.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
            e.printStackTrace();
        } catch (NotUniqueException e) {
            logger.info(e.getMessage());
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            attributes.addFlashAttribute("message",
                    messageSource.getMessage("not.created.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
            e.printStackTrace();
        }
        //TODO SHOULD REDIRECT TO SHOW VIEW OF THE USER

        return "redirect:/user/index";
    }

    /**
     * Show user action.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
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

        // merchant admin can only view users from his merchant
        SecurityUser securityUser = UserResource.getCurrentUser();

        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403"; // access denied
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
    public String edit(@PathVariable("id") Long id, Model model) {

        logger.debug("~~~~~~~~~~~edit + " + id);
        model.addAttribute("action", "edit");
        // get user by id
        User user = null;
        if (id==0){
            user = UserResource.getCurrentUser();
            logger.debug("~~~~~~~~~~~user + " + user.getUsername());
            logger.debug("~~~~~~~~~~~user + " + user.getCreatedTime());

        } else {
            try {
                user = userService.get(id);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "User " + id + " not found.");
            }
        }

        // create command user and add to model and view
        UserCommand userCommand = createUserCommand(user);
        model.addAttribute("userCommand", userCommand);

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403"; // not same merchant access denied
        }

        return "main";
    }

    /**
     * Save a user.
     *
     * @param userCommand
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("userCommand") UserCommand userCommand) {
        Locale locale = LocaleContextHolder.getLocale();

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
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (!user.getMerchant().equals(securityUser.getMerchant())) {
            return "403";
        }
        // user is a user edited by merchant admin
        try {
            userService.update(user);
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String fieldLabel = messageSource.getMessage("operator.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.updated.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
        }
        return "main";
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request) {

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            throw new BadRequestException("400", "User id is blank.");
        }
        User user = null;
        try {
            user = userService.get(Long.valueOf(id));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Cannot find user " + id);
        }

        ModelAndView view = new ModelAndView("user/_confirmModal");
        view.addObject("id", id);
        view.addObject("username", user.getUsername());
        return view;
    }

    /**
     * ajax calls to delete a user by id.
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

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("User.label", null, locale);
        User user = null;
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
        return JsonUtil.toJson(response);
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
        //Set user's merchant as Current SecureUser's Merchant;
        user.setMerchant(UserResource.getCurrentUser().getMerchant());
        // set UserStatus as Status:'1-Normal'
        UserStatus userStatus = null;
        try {
            userStatus = userStatusService.get(Long.parseLong("1"));
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
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        userCommand.setCreatedTime(dateFormat.format(user.getCreatedTime()));
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
    }

}
