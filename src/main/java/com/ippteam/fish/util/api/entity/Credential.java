package com.ippteam.fish.util.api.entity;

/**
 * Created by pactera on 16/10/28.
 * 登陆凭证
 */
public class Credential {
    String userId;
    String userName;
    String token;

    public Boolean isValid() {
        return true;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
