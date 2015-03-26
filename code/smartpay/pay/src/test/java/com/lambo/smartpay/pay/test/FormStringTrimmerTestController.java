package com.lambo.smartpay.pay.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by swang on 3/26/2015.
 */
@Controller
public class FormStringTrimmerTestController {
    @RequestMapping("/test")
    public String test(String test) {
        return test;
    }
}
