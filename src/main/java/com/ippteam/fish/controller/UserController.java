package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.exception.InvalidRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ippteam.fish.util.api.version.*;

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
        return new Result(200, null, u);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/regnew", method = RequestMethod.POST)
    public Result regnew(@RequestBody(required = false) User user) {
        if (user == null) throw new InvalidRequest("invalid body param.");
        if (userService.register(user)) {
            return new Result(200, null, true);
        } else {
            return new Result(200, null, false);
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    public Result resetpwd(@RequestBody(required = false) User user) {
        return null;
    }
}