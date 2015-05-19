package com.lambo.smartpay.manage.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by swang on 3/18/2015.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ModelAndView badRequestExceptionHandler(BadRequestException exception) {
        ModelAndView view = new ModelAndView("404");
        view.addObject("exception", exception);
        return view;
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    public String accessDenied(AccessDeniedException exception) {
        logger.debug("in access denied: " + exception.getMessage());
        return "403";
    }

    @ExceptionHandler(IntervalServerException.class)
    public ModelAndView intervalServerExceptionHandler(IntervalServerException exception) {
        ModelAndView view = new ModelAndView("500");
        view.addObject("exception", exception);
        return view;
    }

    @ExceptionHandler(RemoteAjaxException.class)
    public
    @ResponseBody
    RemoteAjaxException remoteAjaxExceptionHandler(
            RemoteAjaxException exception) {
        return exception;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public String defaultErrorHandler(HttpServletRequest request, HttpServletResponse response) {
        return "error";
    }
}
