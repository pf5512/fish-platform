package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.exception.InvalidRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ippteam.fish.util.api.version.*;

import static com.ippteam.fish.util.Final.*;

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
    public Result regnew(@RequestBody(required = false) User user) {
        if (user == null) throw new InvalidRequest(BUSINESS_EXCEPTION_PARAM_INVALID);
        String regWay = user.getRegisterWay().trim().toLowerCase();
        if (REG_WAY_EMAIL.equals(regWay)) {
            return regnewByEmail(user);
        } else if (REG_WAY_PHONE.equals(regWay)) {
            return regnewByPhone(user);
        } else if (REG_WAY_USERNAME.equals(regWay)) {
            return regnewByUserName(user);
        } else {
            throw new InvalidRequest(BUSINESS_EXCEPTION_PARAM_INVALID);
        }
    }

    private Result regnewByUserName(User user) {
        String userName = user.getUserName();
        String pwd = user.getPassword();
        if (Verify.isValid(userName) && Verify.isValid(pwd)) {
            if (userService.getUserByUserName(userName) == null) {
                if (userService.registerByUserName(userName, pwd)) {
                    return new Result(0, null, true);
                } else {
                    throw new RuntimeException(UNKNOWN_ERROR);
                }
            } else {
                throw new InvalidRequest(BUSINESS_EXCEPTION_USERNAME_EXISTING);
            }
        } else {
            throw new InvalidRequest(BUSINESS_EXCEPTION_PARAM_INVALID);
        }
    }

    private Result regnewByEmail(User user) {
        String email = user.getEmail();
        String pwd = user.getPassword();
        if (Verify.isValid(email) && Verify.isValid(pwd)) {
            if (userService.getUserByEmail(email) == null) {
                if (userService.registerByEmail(email, pwd)) {
                    return new Result(0, null, true);
                } else {
                    throw new RuntimeException(UNKNOWN_ERROR);
                }
            } else {
                throw new InvalidRequest(BUSINESS_EXCEPTION_EMAIL_EXISTING);
            }
        } else {
            throw new InvalidRequest(BUSINESS_EXCEPTION_PARAM_INVALID);
        }
    }

    private Result regnewByPhone(User user) {
        String phone = user.getPhone();
        String pwd = user.getPassword();
        if (Verify.isValid(phone) && Verify.isValid(pwd)) {
            if (userService.getUserByPhone(phone) == null) {
                if (userService.registerByPhone(phone, pwd)) {
                    return new Result(0, null, true);
                } else {
                    throw new RuntimeException(UNKNOWN_ERROR);
                }
            } else {
                throw new InvalidRequest(BUSINESS_EXCEPTION_PHONE_EXISTING);
            }
        } else {
            throw new InvalidRequest(BUSINESS_EXCEPTION_PARAM_INVALID);
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    public Result resetpwd(@RequestBody(required = false) User user) {
        return null;
    }
}