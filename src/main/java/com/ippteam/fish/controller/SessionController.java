package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.pojo.Login;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.exception.*;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.ippteam.fish.util.Final.*;

/**
 * Created by isunimp on 16/11/2.
 */

@Controller
@RequestMapping("/api/{version}/session")
public class SessionController extends BaseController {
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Login login, HttpServletRequest request) {
        if (!Verify.string(login.getAccount()) ||
                !Verify.string(login.getPassword())) {
            throw new ParameterException(EXCEPTION_REQUEST_PARAMER_INVALID);
        }

        User user = userService.login(login.getAccount(), login.getPassword());
        if (user == null) {
            throw new BusinessException(BusinessStatus.USERNAME_OR_PASSWORD_INVALID);
        }

        String token = authenticationService.certificate(user);
        if (!Verify.string(token)) {
            throw new BusinessException(BusinessStatus.UNKNOWN_ERROR);
        }
        return new Result(0, null, token);
    }
}