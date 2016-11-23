package com.ippteam.fish.controller;

import com.ippteam.fish.util.api.pojo.*;
import com.ippteam.fish.util.api.exception.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.ippteam.fish.util.Final.*;

/**
 * Created by pactera on 16/10/26.
 */

@ControllerAdvice
public class AdviceController {

    private static Logger logger = Logger.getLogger(AdviceController.class);

    /**
     * 业务错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result businessException(BusinessException e) {
        return new Result(e.getCode(), e.getReason(), null);
    }

    /**
     * 签名错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CertificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Result certificationException(CertificationException e) {
        return new Result(401, e.getMessage(), null);
    }

    /**
     * 参数无效
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result bodyException(ParameterException e) {
        return new Result(400, e.getMessage(), null);
    }

    /**
     * url请求参数错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result paramerException(MissingServletRequestParameterException e) {
        return new Result(400, EXCEPTION_REQUEST_URL_PARAMER_ERROE, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result paramerException(MethodArgumentTypeMismatchException e) {
        return new Result(400, EXCEPTION_REQUEST_URL_PARAMER_ERROE, null);
    }

    /**
     * body参数错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new Result(400, EXCEPTION_REQUEST_BODY_PARAMER_ERROE, null);
    }

    /**
     * 请求mothod不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Result notSupportedHandler(Exception e) {
        return new Result(405, EXCEPTION_REQUEST_METHOD_NOTSUPPORTED, null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        StringBuffer sb = new StringBuffer();

        sb.append("==================================================\n");
        sb.append(e.getClass().getName() + "\n");
        sb.append(e.getMessage() + "\n");

        for (StackTraceElement item : e.getStackTrace()) {
            String className = item.getClassName();
            int lineNumber = item.getLineNumber();
            sb.append("at\t" + className + "(" + lineNumber + ")" + "\n");
        }
        logger.error(sb.toString());
        return new Result(500, EXCEPTION_UNKNOWN_EXCEPTION, null);
    }
}