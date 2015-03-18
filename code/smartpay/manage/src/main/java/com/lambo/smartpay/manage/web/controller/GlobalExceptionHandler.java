package com.lambo.smartpay.manage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by swang on 3/18/2015.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    public String notFoundExceptionHandler() {
//        return "404";
//    }
//
//    @ExceptionHandler
//    public String serverErrorHandler() {
//        return "500";
//    }

    @ExceptionHandler(value = {Exception.class})
    public String defaultErrorHandler(HttpServletRequest request, HttpServletResponse response) {
        return "error";
    }
}
