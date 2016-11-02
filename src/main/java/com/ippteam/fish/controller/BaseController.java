package com.ippteam.fish.controller;

import com.ippteam.fish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by pactera on 16/10/27.
 */

public class BaseController {
    @Autowired
    protected UserService userService;
}
