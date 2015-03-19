package com.lambo.smartpay.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class HomeController {

    // here goes all model across the whole controller
    @ModelAttribute("noBreadCrumb")
    public Boolean noBreadCrumb() {
        return true;
    }

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
}
