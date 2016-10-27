package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.api.entity.*;
import com.ippteam.fish.util.api.exception.InvalidRequest;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by pactera on 16/10/26.
 */

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(InvalidRequest.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result InvalidRequestHandler(Exception e) {
        return new Result(400, e.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        return new Result(500, e.getClass().getName() + "." + e.getMessage(), null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Result NotSupportedHandler(Exception e) {
        return new Result(405, "Request method not supported or Request not found", null);
    }
}