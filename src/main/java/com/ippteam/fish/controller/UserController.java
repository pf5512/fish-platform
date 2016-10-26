package com.ippteam.fish.controller;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.IUserService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ippteam.fish.util.apiversion.*;

/**
 * Created by pactera on 16/10/24.
 */

@RestController
@RequestMapping("/{version}")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User user(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public boolean login(@RequestBody User user) {
        if (userService.login(user.getUserName(), user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public boolean register(@RequestBody User user) {
        if (userService.register(user)) {
            return true;
        } else {
            return false;
        }
    }
}
