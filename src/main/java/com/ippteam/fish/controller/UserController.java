package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.model.RegNew;
import com.ippteam.fish.model.RegisterWay;
import com.ippteam.fish.util.Session;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.entity.Credential;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.exception.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ippteam.fish.util.api.version.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static com.ippteam.fish.util.Final.*;
import static com.ippteam.fish.util.api.BusinessStatus.UNKNOWN_ERROR;

/**
 * Created by pactera on 16/10/24.
 */

@Controller
@RequestMapping("/api/{version}/user")
public class UserController extends BaseController {


    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result user(@PathVariable(value = "id") Integer id) {
        User u = userService.getUserById(id);
        return new Result(0, null, u);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/regnew", method = RequestMethod.POST)
    public Result regNew(@RequestBody RegNew regNew, HttpServletRequest request) {
        String account = regNew.getAccount();
        String pwd = regNew.getPassword();
        if (!Verify.string(pwd)) {
            throw new ParameterException(EXCEPTION_REQUEST_BODY_PARAM_INVALID);
        }

        User u = new User();
        u.setPassword(pwd);
        switch (regNew.getRegisterWay()) {
            case USERNAME: {
                if (!Verify.userName(account)) {
                    throw new BusinessException(BusinessStatus.USERNAME_INVALID);
                }
                if (userService.getUserByUserName(account) != null) {
                    throw new BusinessException(BusinessStatus.USERNAME_EXISTING);
                }
                u.setUserName(account);
                u.setRegisterWay(REG_WAY_USERNAME);
            }
            case EMAIL: {
                if (!Verify.email(account)) {
                    throw new BusinessException(BusinessStatus.EMAIL_INVALID);
                }
                if (userService.getUserByEmail(account) != null) {
                    throw new BusinessException(BusinessStatus.EMAIL_EXISTING);
                }
                u.setEmail(account);
                u.setRegisterWay(REG_WAY_EMAIL);
            }
            case PHONE: {
                if (!Verify.phone(account)) {
                    throw new BusinessException(BusinessStatus.PHONE_INVALID);
                }
                if (userService.getUserByPhone(account) != null) {
                    throw new BusinessException(BusinessStatus.PHONE_EXISTING);
                }
                u.setPhone(account);
                u.setRegisterWay(REG_WAY_PHONE);
            }
        }
        User user = userService.register(u);
        if (user == null) {
            throw new BusinessException(UNKNOWN_ERROR);
        }
        Credential credential = Session.login(request, user);
        return new Result(0, null, credential.getToken());
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    public Result resetpwd(@RequestBody(required = false) User user) {
        return null;
    }
}