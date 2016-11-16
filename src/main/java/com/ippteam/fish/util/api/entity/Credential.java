package com.ippteam.fish.util.api.entity;

import com.ippteam.fish.util.Random;

/**
 * Created by pactera on 16/10/28.
 * 登陆凭证
 */
public class Credential {
    String userId;
    String token;

    public Credential(Integer userId) {
        this.userId = userId.toString();
        this.token = Random._32String();
    }

    public Boolean isValid() {
        return true;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
