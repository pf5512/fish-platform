package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.pojo.RegNew;
import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.util.Convert;
import com.ippteam.fish.util.Session;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.pojo.Credential;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ippteam.fish.util.api.version.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.ippteam.fish.util.Final.*;
import static com.ippteam.fish.util.api.BusinessStatus.*;

/**
 * Created by pactera on 16/10/24.
 */

@Controller("UserController")
@RequestMapping("/api/{version}/user")
public class UserController extends BaseController {

    @Autowired
    AuthCodeService authCodeService;

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
        String authCode = regNew.getAuthCode();
        String account = regNew.getAccount();
        String pwd = regNew.getPassword();
        if (!Verify.string(pwd)) {
            throw new ParameterException(EXCEPTION_REQUEST_PARAMER_INVALID);
        }

        if (!authCodeService.verify(authCode, account)) {
            throw new BusinessException(AUTHCODE_INVALID);
        }
        authCodeService.delete(authCode);

        String IPString = this.getIpAddr(request);
        Integer ipInt = (int) Convert.ipToLong(IPString);
        Date now = new Date();
        User u = new User();
        u.setPassword(pwd);
        u.setRegisterIp(ipInt);
        u.setRegisterTime(now);
        u.setCreateTime(now);
        u.setUpdateTime(now);
        // 构建user对象
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
                break;
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
                break;
            }
            case PHONE: {
                throw new BusinessException(BusinessStatus.UNDER_CONSTRUCTING_REGNEW_PHONE);
//                if (!Verify.phone(account)) {
//                    throw new BusinessException(BusinessStatus.PHONE_INVALID);
//                }
//                if (userService.getUserByPhone(account) != null) {
//                    throw new BusinessException(BusinessStatus.PHONE_EXISTING);
//                }
//                u.setPhone(account);
//                u.setRegisterWay(REG_WAY_PHONE);
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
    public Result resetpwd(@RequestBody RegNew regNew) {
        String authCode = regNew.getAuthCode();
        String account = regNew.getAccount();
        String pwd = regNew.getPassword();
        if (!Verify.string(pwd)) {
            throw new ParameterException(EXCEPTION_REQUEST_PARAMER_INVALID);
        }

        if (!authCodeService.verify(authCode, account)) {
            throw new BusinessException(AUTHCODE_INVALID);
        }
        authCodeService.delete(authCode);

        User user;
        switch (regNew.getRegisterWay()) {
            case EMAIL: {
                if (!Verify.email(account)) {
                    throw new BusinessException(BusinessStatus.EMAIL_INVALID);
                }
                if (userService.getUserByEmail(account) != null) {
                    throw new BusinessException(BusinessStatus.EMAIL_EXISTING);
                }
                user = userService.getUserByEmail(account);
                break;
            }
            case PHONE: {
                throw new BusinessException(BusinessStatus.UNDER_CONSTRUCTING_REGNEW_PHONE);
            }
            default: {
                throw new ParameterException(EXCEPTION_REQUEST_PARAMER_INVALID);
            }
        }

        user.setPassword(pwd);
        user.setUpdateTime(new Date());
        User u = userService.update(user);
        if (user == null) {
            throw new BusinessException(UNKNOWN_ERROR);
        }
        return new Result(0, null, true);
    }
}