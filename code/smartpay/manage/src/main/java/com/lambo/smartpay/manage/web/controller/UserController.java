package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.Role;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.persistence.entity.UserStatus;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.RoleService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.core.service.UserStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.config.SecurityUser;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.UserCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesUser;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/18/2015.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
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

//    @RequestMapping(value = {"/index/{target}"}, method = RequestMethod.GET)
//    public String index(Model model, @PathVariable("target") String target) {
//
//        if (StringUtils.isBlank(target)
//                || (!target.equals("operator") && !target.equals("merchantAdmin")
//                && !target.equals("merchantOperator") && !target.equals("archive"))) {
//            return "404";
//        }
//
//        model.addAttribute("target", target);
//        model.addAttribute("_view", "user/index" + StringUtils.capitalize(target));
//        return "main";
//    }

    @RequestMapping(value = {"/index/operator"}, method = RequestMethod.GET)
    public String indexOperator(Model model) {

        model.addAttribute("_view", "user/indexOperator");
        return "main";
    }

    @RequestMapping(value = {"/index/merchantAdmin"}, method = RequestMethod.GET)
    public String indexMerchantAdmin(Model model) {

        model.addAttribute("_view", "user/indexMerchantAdmin");
        return "main";
    }

    @RequestMapping(value = {"/index/merchantOperator"}, method = RequestMethod.GET)
    public String indexMerchantOperator(Model model) {

        model.addAttribute("_view", "user/indexMerchantOperator");
        return "main";
    }

    @RequestMapping(value = {"/index/archive"}, method = RequestMethod.GET)
    public String indexArchive(Model model) {

        model.addAttribute("_view", "user/indexArchive");
        return "main";
    }

//    @RequestMapping(value = "/list/{target}", method = RequestMethod.GET,
//            produces = "application/json;charset=UTF-8")
//    public
//    @ResponseBody
//    String list(HttpServletRequest request, @PathVariable("target") String target) {
//
//        if (StringUtils.isBlank(target)
//                || (!target.equals("operator") && !target.equals("merchantAdmin")
//                && !target.equals("merchantOperator") && !target.equals("archive"))) {
//            return "404";
//        }
//
//        // exclude current user
//        SecurityUser securityUser = UserResource.getCurrentUser();
//        if (securityUser == null) {
//            return "403";
//        }
//
//        Role role = null;
//        Role operatorRole = null;
//        Role merchantAdminRole = null;
//        Role merchantOperatorRole = null;
//        try {
//            operatorRole = roleService.findByCode(ResourceProperties.ROLE_ADMIN_OPERATOR_CODE);
//            merchantAdminRole = roleService
//                    .findByCode(ResourceProperties.ROLE_MERCHANT_ADMIN_CODE);
//            merchantOperatorRole = roleService
//                    .findByCode(ResourceProperties.ROLE_MERCHANT_OPERATOR_CODE);
//        } catch (NoSuchEntityException e) {
//            e.printStackTrace();
//            throw new BadRequestException("400", "No role found.");
//        }
//
//        Boolean active = true;
//
//        switch (target) {
//            case "operator":
//                role = operatorRole;
//                break;
//            case "merchantAdmin":
//                role = merchantAdminRole;
//                break;
//            case "merchantOperator":
//                role = merchantOperatorRole;
//                break;
//            case "archive":
//                active = false;
//                break;
//            default:
//                return "403";
//        }
//
//        // formulate criteria query
//        // if active == false means archive, no role
//        // support ad hoc search on username only
//        // support order on id and createdTime only
//        User includedUser = new User();
//        includedUser.setActive(active);
//        if (role != null) {
//            includedUser.setRoles(new HashSet<Role>());
//            includedUser.getRoles().add(role);
//        }
//
//        DataTablesParams params = new DataTablesParams(request);
//        if (params.getOffset() == null || params.getMax() == null
//                || params.getOrder() == null || params.getOrderDir() == null) {
//            throw new BadRequestException("400", "Bad Request.");
//        }
//
//        List<User> users = userService.findByCriteriaWithExclusion(
//                includedUser,
//                securityUser,
//                params.getSearch(),
//                Integer.valueOf(params.getOffset()),
//                Integer.valueOf(params.getMax()), params.getOrder(),
//                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
//
//        // count total records
//        Long recordsTotal = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser);
//        // count records filtered
//        Long recordsFiltered = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser, params.getSearch());
//
//        if (users == null || recordsTotal == null || recordsFiltered == null) {
//            throw new RemoteAjaxException("500", "Internal Server Error.");
//        }
//
//        List<DataTablesUser> dataTablesUsers = new ArrayList<>();
//
//        for (User user : users) {
//            DataTablesUser tableUser = new DataTablesUser(user);
//            dataTablesUsers.add(tableUser);
//        }
//
//        DataTablesResultSet<DataTablesUser> result = new DataTablesResultSet<>();
//        result.setData(dataTablesUsers);
//        result.setRecordsFiltered(recordsFiltered.intValue());
//        result.setRecordsTotal(recordsTotal.intValue());
//
//        return JsonUtil.toJson(result);
//    }

    @RequestMapping(value = "/list/operator", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listOperator(HttpServletRequest request) {

        // exclude current user
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            return "403";
        }

        Role operatorRole = null;
        try {
            operatorRole = roleService.findByCode(ResourceProperties.ROLE_ADMIN_OPERATOR_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "No role found.");
        }

        // formulate criteria query
        // if active == false means archive, no role
        // support ad hoc search on username only
        // support order on id and createdTime only
        User includedUser = new User();
        includedUser.setActive(true);
        includedUser.setRoles(new HashSet<Role>());
        includedUser.getRoles().add(operatorRole);

        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }


        List<User> users = userService.findByCriteria(
                includedUser,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records
        Long recordsTotal = userService
                .countByCriteria(includedUser);
        // count records filtered
        Long recordsFiltered = userService
                .countByCriteria(includedUser, params.getSearch());
//        List<User> users = userService.findByCriteriaWithExclusion(
//                includedUser,
//                securityUser,
//                params.getSearch(),
//                Integer.valueOf(params.getOffset()),
//                Integer.valueOf(params.getMax()), params.getOrder(),
//                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
//
//        // count total records
//        Long recordsTotal = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser);
//        // count records filtered
//        Long recordsFiltered = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser, params.getSearch());

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

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/list/merchantAdmin", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listMerchantAdmin(HttpServletRequest request) {

        Role merchantAdminRole = null;
        try {
            merchantAdminRole = roleService
                    .findByCode(ResourceProperties.ROLE_MERCHANT_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "No role found.");
        }

        // formulate criteria query
        // if active == false means archive, no role
        // support ad hoc search on username only
        // support order on id and createdTime only
        User includedUser = new User();
        includedUser.setActive(true);
        includedUser.setRoles(new HashSet<Role>());
        includedUser.getRoles().add(merchantAdminRole);

        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<User> users = userService.findByCriteria(
                includedUser,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records
        Long recordsTotal = userService
                .countByCriteria(includedUser);
        // count records filtered
        Long recordsFiltered = userService
                .countByCriteria(includedUser, params.getSearch());

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

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/list/merchantOperator", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        Role merchantOperatorRole = null;
        try {
            merchantOperatorRole = roleService
                    .findByCode(ResourceProperties.ROLE_MERCHANT_OPERATOR_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "No role found.");
        }

        // formulate criteria query
        // if active == false means archive, no role
        // support ad hoc search on username only
        // support order on id and createdTime only
        User includedUser = new User();
        includedUser.setActive(true);
        includedUser.setRoles(new HashSet<Role>());
        includedUser.getRoles().add(merchantOperatorRole);

        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<User> users = userService.findByCriteria(
                includedUser,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records
        Long recordsTotal = userService
                .countByCriteria(includedUser);
        // count records filtered
        Long recordsFiltered = userService
                .countByCriteria(includedUser, params.getSearch());

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

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/list/archive", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String listArchive(HttpServletRequest request) {

        // exclude current user
        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            return "403";
        }

        // formulate criteria query
        // if active == false means archive, no role
        // support ad hoc search on username only
        // support order on id and createdTime only
        User includedUser = new User();
        includedUser.setActive(false);

        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }


        List<User> users = userService.findByCriteria(
                includedUser,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));

        // count total records
        Long recordsTotal = userService
                .countByCriteria(includedUser);
        // count records filtered
        Long recordsFiltered = userService
                .countByCriteria(includedUser, params.getSearch());


//        List<User> users = userService.findByCriteriaWithExclusion(
//                includedUser,
//                securityUser,
//                params.getSearch(),
//                Integer.valueOf(params.getOffset()),
//                Integer.valueOf(params.getMax()), params.getOrder(),
//                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
//
//        // count total records
//        Long recordsTotal = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser);
//        // count records filtered
//        Long recordsFiltered = userService
//                .countByCriteriaWithExclusion(includedUser, securityUser, params.getSearch());

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

        return JsonUtil.toJson(result);
    }

    @RequestMapping(value = "/create/operator", method = RequestMethod.GET)
    public String createOperator(Model model) {

        model.addAttribute("_view", "user/createOperator");
        model.addAttribute("userCommand", new UserCommand());
        model.addAttribute("userStatuses", userStatusService.getAll());
        return "main";
    }

    @RequestMapping(value = "/create/merchantAdmin", method = RequestMethod.GET)
    public String createMerchantAdmin(Model model) {

        List<Merchant> merchants = merchantService.getAll();
        model.addAttribute("_view", "user/createMerchantAdmin");
        model.addAttribute("userCommand", new UserCommand());
        return "main";
    }

    @RequestMapping(value = "/create/operator", method = RequestMethod.POST)
    public String saveOperator(Model model, @ModelAttribute("userCommand") UserCommand
            userCommand) {

        Locale locale = LocaleContextHolder.getLocale();
        // check if username contains /
        if (StringUtils.contains(userCommand.getUsername(), "/")) {
            model.addAttribute("message",
                    messageSource.getMessage("username.cannot.contain.slash.message",
                            new String[]{}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("userStatuses", userStatusService.getAll());
            model.addAttribute("_view", "user/createOperator");
            return "main";
        }

        /*
        // check if username already taken
        if (userService.findByUsername(userCommand.getUsername()) != null) {

            String fieldLabel = messageSource.getMessage("username.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, userCommand.getUsername()}, locale));
            model.addAttribute("userCommand", userCommand);
            model.addAttribute("userStatuses", userStatusService.getAll());
            model.addAttribute("_view", "user/createOperator");
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
            model.addAttribute("_view", "user/createOperator");
            return "main";
        }
        */
        //TODO check if all required fields filled

        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_OPERATOR_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find merchant admin role.");
            e.printStackTrace();
        }
        // create User and set admin to user
        User user = createUser(userCommand, role);
        // set initial password
        user.setPassword(passwordEncoder.encode(ResourceProperties.INITIAL_PASSWORD));
        // persist user
        try {
            user = userService.create(user);
        } catch (MissingRequiredFieldException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return "redirect:/404";
        } catch (NotUniqueException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return "redirect:/404";
        }
        logger.debug("here goes user: " + user.getId());
        return "redirect:/user/index/operator";
    }

    @RequestMapping(value = "/create/merchantAdmin", method = RequestMethod.POST)
    public String saveMerchantAdmin(Model model,
                                    @ModelAttribute("userCommand") UserCommand userCommand) {

        // set subDomain to model
        model.addAttribute("domain", "MerchantAdmin");

        List<Merchant> merchants = merchantService.getAll();
        model.addAttribute("merchants", merchants);

        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_MERCHANT_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find merchant admin role.");
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
        } catch (MissingRequiredFieldException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (NotUniqueException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        //TODO SHOULD REDIRECT TO SHOW VIEW OF THE USER

        return "main";
    }

    /**
     * Show user action.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        // set subDomain to model
        model.addAttribute("action", "show");
        // get user by id
        User user = null;
        try {
            user = userService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        // set domain based on user's role
        String domain = "MerchantAdmin";
        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find admin role.");
            e.printStackTrace();
        }

        model.addAttribute("domain", domain);
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
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        model.addAttribute("action", "edit");
        // get user by id

        User user;

        if (id == 0) {
            user = UserResource.getCurrentUser();
        } else {
            try {
                user = userService.get(id);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("400", "User " + id + " not found.");
            }
        }
        // set domain based on user's role
        String domain = "MerchantAdmin";
        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find admin role.");
            e.printStackTrace();
        }

        model.addAttribute("domain", domain);
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
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("userCommand") UserCommand userCommand) {

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

        // set domain and action based on user's role
        String domain = "MerchantAdmin";
        Role role = null;
        try {
            role = roleService.findByCode(ResourceProperties.ROLE_ADMIN_CODE);
        } catch (NoSuchEntityException e) {
            logger.info("Cannot find admin role.");
            e.printStackTrace();
        }

        model.addAttribute("domain", domain);

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
        if (role != null) {
            user.setRoles(new HashSet<Role>());
            user.getRoles().add(role);
        }
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
