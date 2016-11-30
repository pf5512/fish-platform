package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.redis.RedisDao;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by isunimp on 16/11/30.
 */
@Service("AuthenticationService")
public class AuthenticationServiceImpl {

    @Autowired
    RedisDao redisDao;

    private String redisKey(String token) {
        return "token_" + token;
    }

    private String newToken() {
        return Random._32String();
    }

    public String certificate(User user) {
        String token;
        String key;
        int count = 0;
        do {
            token = newToken();
            key = redisKey(token);
            if (!redisDao.exists(key)) {
                if (redisDao.set(key, user.getId().toString(), 10 * 24 * 60 * 60)) {
                    return token;
                }
            }
        } while (++count < 3);
        return null;
    }

    public boolean verify(String token) {
        String key = redisKey(token);
        String id = redisDao.get(key);
        return id != null && id.length() > 0;
    }
}
