package com.ippteam.fish.controller;

import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.util.HttpUtils;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.exception.BusinessException;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import com.ippteam.fish.util.email.EmailInfo;
import com.ippteam.fish.util.email.ServerInfo;
import com.ippteam.fish.util.email.UserAgent;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.ippteam.fish.util.api.BusinessStatus.EMAIL_INVALID;
import static com.ippteam.fish.util.api.BusinessStatus.PHONE_INVALID;

/**
 * Created by isunimp on 16/11/17.
 */

@Controller("AuthCodeController")
@RequestMapping("/api/{version}/authcode")
public class AuthCodeController {

    @Autowired
    AuthCodeService authCodeService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public Result email(@RequestParam String email) throws Exception {
        if (!Verify.email(email)) {
            throw new BusinessException(EMAIL_INVALID);
        }
        long authCode = authCodeService.generate(email);
        if (authCode == 0) {
            return new Result(0, null, false);
        }

        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerHost("smtp.163.com");
        serverInfo.setUserName("ansheck@163.com");
        serverInfo.setPassword("renguiquanyy1");
        serverInfo.setNick("Fish Service");

        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setSubject("你正在注册Fish");
        emailInfo.setContent("验证码：" + Long.toString(authCode));
        emailInfo.setToAddress(email);

        UserAgent userAgent = new UserAgent(serverInfo);
        userAgent.sendHtmlMail(emailInfo);

        return new Result(0, null, true);
    }

    @ApiVersion(1)
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public Result phone(@RequestParam String phone) throws Exception {
        throw new BusinessException(BusinessStatus.UNDER_CONSTRUCTING);

//        if (!Verify.phone(phone)) {
//            throw new BusinessException(PHONE_INVALID);
//        }
//        long authCode = authCodeService.generate(phone);
//
//        String host = "http://www.qf106.com";
//        String path = "/sms.aspx";
//        String method = "GET";
//
//        Map<String, String> headers = new HashMap<String, String>();
//
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("userid", "14960");
//        querys.put("account", "tz112");
//        querys.put("password", "we1234");
//        querys.put("mobile", phone);
//        querys.put("content", Long.toString(authCode));
//        querys.put("sendTime", null);
//        querys.put("action", "send");
//        querys.put("checkcontent", "0");
//        querys.put("taskName", null);
//        querys.put("countnumber", "1");
//        querys.put("mobilenumber", "1");
//        querys.put("telephonenumber", "0");
//
//        HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//        EntityUtils.toString(response.getEntity());
//
//        return new Result(0, null, true);
    }
}
