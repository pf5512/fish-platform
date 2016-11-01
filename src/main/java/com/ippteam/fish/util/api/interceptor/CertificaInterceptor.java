package com.ippteam.fish.util.api.interceptor;

import com.ippteam.fish.util.AES;
import com.ippteam.fish.util.ConvertByte;
import com.ippteam.fish.util.Final;
import com.ippteam.fish.util.JSON;
import com.ippteam.fish.util.api.entity.Credential;
import com.ippteam.fish.util.api.entity.Sign;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by pactera on 16/10/28.
 * 验证Sign是够合法
 */
public class CertificaInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        request.getSession();
        String appkey = request.getParameter("appkey");
        String signEncrypt = request.getParameter("sign");

        String securityKey = "根据appkey获取";
        byte[] signBuff = ConvertByte.parseHexStr2Byte(securityKey);

        String signDecrypt = new String(AES.decrypt(signBuff, securityKey));
        Sign sign = JSON.parse(signDecrypt, Sign.class);

        PrintWriter printWriter = response.getWriter();
        if (!verifyExpiredTime(sign.getExpiredTime())) {
            printWriter.println("sign fail.");
            return false;
        }

        if (!verifyBody(request, sign.getBody())) {
            printWriter.println("sign fail.");
            return false;
        }

        if (!verifyToken(request, sign.getToken())) {
            printWriter.println("sign fail.");
            return false;
        }
        return true;
    }

    /**
     * 验证时间戳
     * <p>
     * 必须同时满足两个条件
     * 1. 请求发出时间戳不能大于云端时间戳
     * 2. 请求到云端不能超过30s，有效防止被抓包
     *
     * @param requestExpiredTime
     * @return
     */
    private boolean verifyExpiredTime(long requestExpiredTime) {
        long currentExpiredTime = System.currentTimeMillis();
        return (requestExpiredTime > currentExpiredTime ||
                currentExpiredTime - requestExpiredTime > 30) ? false : true;
    }


    /**
     * 检测body和request中的真实body是否被更改
     * 防止请求body被更改
     *
     * @param bodyString
     * @return
     */
    private boolean verifyBody(HttpServletRequest request, String bodyString) throws Exception {
        BufferedReader br = request.getReader();
        String line, body = "";
        while ((line = br.readLine()) != null) {
            body += line;
        }
        return body.equals(bodyString) ? true : false;
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
