package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by isunimp on 16/11/2.
 */

@Controller
@RequestMapping("/api/{version}/session")
public class SessionController extends BaseController {

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody(required = false) User user) {
        if (userService.login(user.getUserName(), user.getPassword())) {
            return new Result(200, null, true);
        } else {
            return new Result(200, "账号或密码错误.", false);
        }
    }
}
