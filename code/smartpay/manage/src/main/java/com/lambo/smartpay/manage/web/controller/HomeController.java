package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.lambo.smartpay.manage.web.vo.PasswordCommand;
import com.lambo.smartpay.manage.config.SecurityUser;
import org.springframework.context.MessageSource;
import com.lambo.smartpay.manage.web.exception.BadRequestException;




import java.util.Locale;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class HomeController {


    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;



    @RequestMapping(value = {"/", "/index"})
    public ModelAndView home() {
        //view.addObject("action", "index");
        return new ModelAndView("main");
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView view = new ModelAndView();
        view.setViewName("403");
        return view;
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model) {

        PasswordCommand passwordCommand = new PasswordCommand();
        model.addAttribute("passwordCommand", passwordCommand);
        model.addAttribute("action", "changePassword");
        return "main";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("passwordCommand") PasswordCommand passwordCommand,
                                 Model model) {

        SecurityUser securityUser = UserResource.getCurrentUser();
        if (securityUser == null) {
            return "403";
        }
        Locale locale = LocaleContextHolder.getLocale();

        if (!passwordCommand.getPassword().equals(passwordCommand.getConfirmPassword())) {
            model.addAttribute("message",
                    messageSource.getMessage("password.not.match.message", null, locale));
            model.addAttribute("action", "changePassword");
            return "main";
        }

        if (!passwordEncoder.matches(passwordCommand.getCurrentPassword(), securityUser
                .getPassword())) {
            model.addAttribute("message",
                    messageSource.getMessage("password.not.correct.message", null, locale));
            model.addAttribute("action", "changePassword");
            return "main";
        }
        User user = null;
        try {
            user = userService.get(securityUser.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Cannot find user.");
        }
        user.setPassword(passwordEncoder.encode(passwordCommand.getPassword()));
        try {
            user = userService.update(user);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Missing fields.");
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Not unique.");
        }
        securityUser.setPassword(user.getPassword());
        model.addAttribute("action", "index");
        return "main";
    }

}
