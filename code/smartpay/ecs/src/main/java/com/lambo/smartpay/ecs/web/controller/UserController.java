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
import com.lambo.smartpay.ecs.util.DataTablesParams;
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
@Secured({"ROLE_MERCHANT_ADMIN"})
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

    @RequestMapping(value = {"/index/operator"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_view", "user/indexOperator");
        return "main";
    }

    @RequestMapping(value = "/list/operator", method = RequestMethod.GET)
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        // exclude current user
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            return "403";
        }
        if (securityUser.getMerchant() == null) {
            return "403";
        }

        Role merchantOperatorRole = null;
        try {
            merchantOperatorRole =
                    roleService.findByCode(ResourceProperties.ROLE_MERCHANT_OPERATOR_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "No role found.");
        }

        // formulate criteria query
        // if active == false means archive, no role
        // support ad hoc search on username only
        // support order on id and createdTime only
        User userCriteria = new User();
        userCriteria.setActive(true);
        userCriteria.setRoles(new HashSet<Role>());
        userCriteria.getRoles().add(merchantOperatorRole);
        userCriteria.setMerchant(securityUser.getMerchant());

        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<User> users = userService.findByCriteria(
                userCriteria,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records
        Long recordsTotal = userService.countByCriteria(userCriteria);
        // count records filtered
        Long recordsFiltered = userService.countByCriteria(userCriteria, params.getSearch());

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
    @RequestMapping(value = "/create/operator", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("_view", "user/createOperator");
        model.addAttribute("userStatuses", userStatusService.getAll());
        model.addAttribute("userCommand", new UserCommand());
        return "main";
    }

    @RequestMapping(value = "/create/operator", method = RequestMethod.POST)
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
            model.addAttribute("userStatuses", userStatusService.getAll());
            model.addAttribute("_view", "user/create/operator");
            return "main";
        }
        // check if email already taken
        if (userService.findByEmail(userCommand.getEmail()) != null) {

            String fieldLabel = messageSource.getMessage("email.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getEmail()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("userStatuses", userStatusService.getAll());
            model.addAttribute("_view", "user/create/operator");
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

        String fieldLabel = messageSource.getMessage("operator.label", null, locale);
        attributes.addFlashAttribute("message",
                messageSource.getMessage("created.message",
                        new String[]{fieldLabel, user.getUsername()}, locale));

        return "redirect:/user/index/operator";
    }

    /**
     * Show user action.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        // get user by id
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // create command user and add to model and view
        model.addAttribute("_view", "user/showOperator");
        model.addAttribute("userCommand", new UserCommand(user));

        return "main";
    }

    /**
     * Edit a user.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {

        String userId = request.getParameter("userId");
        if (StringUtils.isBlank(userId)) {
            throw new BadRequestException("400", "User id is blank.");
        }
        Long id = Long.valueOf(userId);
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        UserCommand userCommand = new UserCommand(user);
        ModelAndView view = new ModelAndView("user/_editDialog");
        view.addObject("userCommand", userCommand);
        view.addObject("userStatuses", userStatusService.getAll());
        return view;
    }

    /**
     * Save a user.
     *
     * @param request request form client
     * @return message
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request) {
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("user.label", null, locale);
        User user = editUser(request);
        try {
            user = userService.update(user);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, user.getUsername()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, user.getUsername()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{label, user.getUsername()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/freeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String freeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("user.label", null, locale);
        String message = "";

        try {
            user = userService.freezeUser(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.frozen.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("frozen.message", new String[]{label, user.getUsername()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }


    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String unfreeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("user.label", null, locale);
        String message = "";
        //Do approve
        try {
            user = userService.unfreezeUser(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.unfrozen.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("unfrozen.message", new String[]{label, user.getUsername()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
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
    private User editUser(HttpServletRequest request) {

        User user = null;
        try {
            user = userService.get(Long.valueOf(request.getParameter("id")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setRemark(request.getParameter("remark"));
        Long userStatusId = Long.valueOf(request.getParameter("userStatus"));
        UserStatus userStatus = null;
        try {
            userStatus = userStatusService.get(userStatusId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }
        user.setUserStatus(userStatus);
        return user;
    }

}
