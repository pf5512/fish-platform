package com.ippteam.fish.util.api.entity;

/**
 * Created by pactera on 16/10/31.
 */
public class Sign {
    long expiredTime;
    Object body;
    String token;

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
