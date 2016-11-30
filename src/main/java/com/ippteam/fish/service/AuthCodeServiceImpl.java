package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.redis.RedisDao;
import com.ippteam.fish.util.Random;
import com.ippteam.fish.util.email.EmailInfo;
import com.ippteam.fish.util.email.ServerInfo;
import com.ippteam.fish.util.email.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by isunimp on 16/11/19.
 */
@Service("AuthCodeService")
public class AuthCodeServiceImpl implements AuthCodeService {

    @Autowired
    RedisDao redisDao;


    private String redisKey(String authCode) {
        return "auth_code_" + authCode;
    }

    private String redisKey(long authCode) {
        return "auth_code_" + authCode;
    }

    private long newAuthCode() {
        return Random._6Number();
    }

    public boolean generate(String account) throws UnsupportedEncodingException, MessagingException {
        long authCode = 0;
        String key;
        int count = 0;
        do {
            authCode = newAuthCode();
            key = redisKey(authCode);
            if (!redisDao.exists(key)) {
                if (redisDao.set(key, account, 5 * 60)) {
                    ServerInfo serverInfo = new ServerInfo();
                    serverInfo.setServerHost("smtp.163.com");
                    serverInfo.setUserName("ansheck@163.com");
                    serverInfo.setPassword("renguiquanyy1");
                    serverInfo.setNick("Fish Service");
                    UserAgent userAgent = new UserAgent(serverInfo);
                    EmailInfo emailInfo = new EmailInfo();
                    emailInfo.setSubject("你正在注册Fish");
                    emailInfo.setContent("验证码：" + Long.toString(authCode));
                    emailInfo.setToAddress(account);
                    userAgent.sendHtmlMail(emailInfo);
                    return true;
                }
            }
        } while (++count < 3);
        return false;
    }

    public boolean verify(String authCode, String account) {
        String key = redisKey(authCode);
        if (!redisDao.exists(key)) return false;

        String result = redisDao.get(key);
        return result.equals(account);
    }

    public boolean delete(String authCode) {
        String key = redisKey(authCode);
        return redisDao.del(key);
    }
}