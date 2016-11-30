package com.ippteam.fish.controller;

import com.ippteam.fish.service.AuthenticationServiceImpl;
import com.ippteam.fish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pactera on 16/10/27.
 */

public class BaseController {
    @Autowired
    protected UserService userService;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
