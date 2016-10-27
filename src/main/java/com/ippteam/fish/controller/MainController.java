package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.exception.InvalidRequest;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pactera on 16/10/27.
 */

@Controller
@RequestMapping("/api/{version}")
public class MainController extends BaseController {

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody(required = false) User user) {
        if (userService.login(user.getUserName(), user.getPassword())) {
            return new Result(200, null, true);
        } else {
            return new Result(200, null, false);
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody(required = false) User user) {
        if (user == null) throw new InvalidRequest("invalid body param.");
        if (userService.register(user)) {
            return new Result(200, null, true);
        } else {
            return new Result(200, null, false);
        }
    }
}
