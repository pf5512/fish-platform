package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.redis.RedisDao;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.Random;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by isunimp on 16/11/30.
 */
@Service("AuthenticationService")
public class AuthenticationServiceImpl {

    // 有效期 10 天
    final Integer validity = 10 * 24 * 60 * 60;

    @Autowired
    RedisDao redisDao;

    private String redisKey(String token) {
        return "token_" + token;
    }

    private String newToken() {
        return Random._32String();
    }

    /**
     * 添加认证
     * 认证有效期10天
     *
     * @param identify 与改认证相关联的标识，通常是用户id
     * @return token
     */
    public String certificate(String identify) {
        return certificate(identify, false);
    }

    public String certificate(String identify, boolean isManager) {
        String token;
        String key;
        int count = 0;
        do {
            token = newToken();
            key = redisKey(token);
            if (isManager) identify += ":manager";
            if (!redisDao.exists(key)) {
                if (redisDao.set(key, identify, validity)) {
                    return token;
                }
            }
        } while (++count < 3);
        return null;
    }

    /**
     * 获取标识
     *
     * @param token 凭证
     * @return
     */
    public String getIdentify(String token) {
        String key = redisKey(token);
        String identify = redisDao.get(key);
        return identify;
    }

    /**
     * 验证凭证是否有效
     *
     * @param token token
     * @return
     */
    public boolean verify(String token) {
        String key = redisKey(token);
        String identify = redisDao.get(key);
        return identify != null && identify.length() > 0;
    }

    public boolean isManager(String token) {
        String key = redisKey(token);
        String identify = redisDao.get(key);
        String[] subs = identify.split(":");
        return subs.length == 2 && subs[1].equals("manager");
    }

    /**
     * 更新token有效期
     *
     * @param token
     */
    public void flushValidity(String token) {
        String key = redisKey(token);
        redisDao.expire(key, validity);
    }
}
