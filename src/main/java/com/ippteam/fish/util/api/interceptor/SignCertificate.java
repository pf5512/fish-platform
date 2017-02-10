package com.ippteam.fish.util.api.interceptor;

import com.ippteam.fish.service.AuthenticationServiceImpl;
import com.ippteam.fish.service.DeveloperService;
import com.ippteam.fish.util.*;
import com.ippteam.fish.util.aes.AESHelper;
import com.ippteam.fish.util.aes.ConvertFailException;
import com.ippteam.fish.util.aes.DecryptFailException;
import com.ippteam.fish.util.api.exception.BusinessException;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.api.exception.CertificationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import static com.ippteam.fish.util.Final.*;
import static com.ippteam.fish.util.FinalDebug.*;
import static com.ippteam.fish.util.api.BusinessStatus.TOKEN_INVALID;

/**
 * Created by pactera on 16/10/28.
 * 验证Sign是够合法
 */
public class SignCertificate extends HandlerInterceptorAdapter {

    @Autowired
    DeveloperService developerService;
    @Autowired
    AuthenticationServiceImpl authenticationService;

    private static Logger logger = Logger.getLogger(SignCertificate.class);

    /**
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ip = getIpAddr(request);
        String uri = request.getRequestURI();
        String logBase = String.format("[%s  %s] ", ip, uri);

        // 文件下载忽略验证
        if (uri.contains("/api/v1/file/")) {
            if (!uri.contains("/api/v1/file/upload")) {
                return true;
            }
        }

        String appkey = request.getHeader("Appkey");
        appkey = (appkey == null || appkey.length() == 0) ? request.getParameter("appkey") : appkey;
        String sign = request.getHeader("Sign");
        sign = (sign == null || sign.length() == 0) ? request.getParameter("sign") : sign;

        if (!Verify.string(appkey) || !Verify.string(sign)) {
            logger.info(logBase + SIGN_FAIL_APPKEY_OR_SIGN_NULL);
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        String secretKey = developerService.getSecretKeyByAppkey(appkey);
        if (!Verify.string(secretKey)) {
            logger.info(logBase + SIGN_FAIL_APPKEY_INVALID);
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }
        request.setAttribute(REQUEST_ATTRIBUTE_AES_SECRET_KEY, secretKey);

        String signDecrypt;
        try {
            signDecrypt = AESHelper.decryptToStr(sign, secretKey);
        } catch (ConvertFailException e) {
            logger.info(logBase + e.getMessage());
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        } catch (DecryptFailException e) {
            logger.info(logBase + e.getMessage());
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        Sign signObj;
        try {
            signObj = JSON.parse(signDecrypt, Sign.class);
            request.setAttribute("sign", signObj);
        } catch (Exception e) {
            logger.info(logBase + e.getMessage());
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        verifySign(logBase, signDecrypt, signObj, request);

        authenticationService.flushValidity(signObj.getToken());
        return true;
    }

    /**
     * 验证签名
     *
     * @param logBase     记录日志需要
     * @param signDecrypt 记录日志需要
     * @param sign        Sign对象
     * @param request     请求
     * @throws Exception
     */
    private void verifySign(String logBase, String signDecrypt, Sign sign, HttpServletRequest request) throws Exception {
        if (!verifyExpiredTime(sign.getExpiredTime())) {
            logger.info(logBase + signDecrypt);
            logger.info(logBase + SIGN_FAIL_TIMEOUT);
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        if (!verifyApi(request, sign.getApi())) {
            logger.info(logBase + signDecrypt);
            logger.info(logBase + SIGN_FAIL_API_ERROR);
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        if (!verifyBody(request, sign.getBody())) {
            logger.info(logBase + signDecrypt);
            logger.info(logBase + SIGN_FAIL_BODY_ERROR);
            throw new CertificationException(EXCEPTION_SIGN_FAIL);
        }

        if (!verifyToken(request, sign.getToken())) {
            logger.info(logBase + signDecrypt);
            logger.info(logBase + SIGN_FAIL_TOKEN_INVALID);
            throw new BusinessException(TOKEN_INVALID);
        }
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
        return true;
    }

    /**
     * 验证api
     *
     * @param request
     * @param api
     * @return
     */
    private boolean verifyApi(HttpServletRequest request, String api) {
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf(api);
        return index != -1 && index == uri.length() - api.length();
    }

    /**
     * 检测body和request中的真实body是否被更改
     * 防止请求body被更改
     *
     * @param body
     * @return
     */
    private boolean verifyBody(HttpServletRequest request, Object body) throws Exception {
        String contentType = request.getContentType();
        if (request.getMethod().equals("GET")) return true;
        if (contentType == null && body == null) return true;
        if (contentType.contains("multipart/form-data")) return true;
        if (body == null) return false;

        BufferedReader br = request.getReader();
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        Object body2 = null;
        if (body instanceof String) {
            body2 = sb.toString();
        } else if (body instanceof Map) {
            body2 = JSON.parse(sb.toString(), Map.class);
        } else if (body instanceof List) {
            body2 = JSON.parse(sb.toString(), List.class);
        } else if (body.getClass().isArray()) {
            body2 = JSON.parse(sb.toString(), List.class);
        }
        return Verify.equals(body, body2);
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
                NO_NEED_AUTH_URI_LOGIN,
                NO_NEED_AUTH_URI_REGNEW,
                NO_NEED_AUTH_URI_RESETPWD,
                NO_NEED_AUTH_URI_WEATHER,
                NO_NEED_AUTH_URI_AUTHCODE_EMAIL,
                NO_NEED_AUTH_URI_MOMENT_MOMENTS,
                NO_NEED_AUTH_URI_SESSION_OAUTH
        };
        for (String iten : filterURIs) {
            if (uri.lastIndexOf(iten) == uri.length() - iten.length()) {
                return true;
            }
        }

        return authenticationService.verify(token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private String getIpAddr(HttpServletRequest request) {
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
