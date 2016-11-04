package com.ippteam.fish.util.api.interceptor;

import com.ippteam.fish.service.DeveloperService;
import com.ippteam.fish.util.*;
import com.ippteam.fish.util.api.entity.Credential;
import com.ippteam.fish.util.api.entity.Result;
import com.ippteam.fish.util.api.entity.Sign;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by pactera on 16/10/28.
 * 验证Sign是够合法
 */
public class CertificaInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    DeveloperService developerService;

    private static Logger logger = Logger.getLogger(CertificaInterceptor.class);

    /**
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String appkey = request.getParameter("appkey");
        String signEncrypt = request.getParameter("sign");

        if (!Verify.isValid(appkey) || !Verify.isValid(signEncrypt)) {
            this.writerData(response);
            return false;
        }

        String securityKey = developerService.getSecurityKeyByAppkey(appkey);
        if (!Verify.isValid(securityKey)) {
            this.writerData(response);
            return false;
        }
        // 加密过的
        byte[] encryptedBuff = ConvertByte.parseHexStr2Byte(signEncrypt);
        if (!Verify.isValid(encryptedBuff)) {
            this.writerData(response);
            return false;
        }
        // 原文buff
        byte[] decryptedBuff = AES.decrypt(encryptedBuff, securityKey);
        if (!Verify.isValid(decryptedBuff)) {
            this.writerData(response);
            return false;
        }

        String signDecrypt = new String(decryptedBuff);
        Sign sign = JSON.parse(signDecrypt, Sign.class);

        if (!verifyExpiredTime(sign.getExpiredTime())) {
            logger.debug("expiredTime验证失败");
            this.writerData(response);
            return false;
        }

        if (!verifyBody(request, sign.getBody())) {
            logger.debug("body验证失败");
            this.writerData(response);
            return false;
        }

        if (!verifyToken(request, sign.getToken())) {
            logger.debug("token验证失败");
            this.writerData(response);
            return false;
        }

        return true;
    }

    private void writerData(HttpServletResponse response) throws Exception {
        PrintWriter printWriter = response.getWriter();
        Result result = new Result(400, "appkey or sign is invalid", null);
        String resultString = JSON.stringify(result);
        printWriter.println(resultString);
        printWriter.close();
    }

    /**
     * 验证时间戳
     * <p>
     * 必须同时满足两个条件
     * 1. 请求发出时间戳不能大于云端时间戳
     * 2. 请求到云端不能超过30s，有效防止被抓包
     * 注意：java获取到的时间戳明确到毫秒，故差值应该为30000
     *
     * @param requestExpiredTime
     * @return
     */
    private boolean verifyExpiredTime(long requestExpiredTime) {
        long currentExpiredTime = System.currentTimeMillis();
        logger.debug("current:" + currentExpiredTime);
        logger.debug("request:" + requestExpiredTime);
        return (requestExpiredTime > currentExpiredTime ||
                currentExpiredTime - requestExpiredTime > 30000) ? false : true;
    }


    /**
     * 检测body和request中的真实body是否被更改
     * 防止请求body被更改
     *
     * @param body
     * @return
     */
    private boolean verifyBody(HttpServletRequest request, Object body) throws Exception {
        BufferedReader br = request.getReader();
        String line, bodyString = "";
        while ((line = br.readLine()) != null) {
            bodyString += line;
        }
        br.close();
        String temp = JSON.stringify(body);
        return temp.trim().equals(bodyString.trim()) ? true : false;
    }

    /**
     * 验证token
     * <p>
     * 从session取出登陆凭证（Credential对象）
     * 判断token是否一致
     * <p>
     * 不需要登陆的请求直接忽略
     *
     * @param request
     * @param token
     * @return
     */
    private boolean verifyToken(HttpServletRequest request, String token) {
        String uri = request.getRequestURI();
        String endChar = uri.substring(uri.length() - 1);
        if (endChar.equals("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        String[] filterURIs = {
                Final.NO_NEED_AUTH_URI_LOGIN,
                Final.NO_NEED_AUTH_URI_REGNEW,
                Final.NO_NEED_AUTH_URI_RESETPWD,
                Final.NO_NEED_AUTH_URI_WEATHER
        };
        for (String iten : filterURIs) {
            if (uri.lastIndexOf(iten) == uri.length() - iten.length()) {
                return true;
            }
        }

        HttpSession session = request.getSession();
        Credential credential = (Credential) session.getAttribute(Final.KEY_CREDENTIAL);
        if (credential == null) {
            return false;
        } else {
            if (credential.isValid() && credential.getToken().equals(token)) {
                return true;
            } else {
                request.getSession().removeAttribute(Final.KEY_CREDENTIAL);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
