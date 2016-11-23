package com.ippteam.fish.pojo;

/**
 * Created by isunimp on 16/11/15.
 */
public class RegNew {
    String account;
    String password;
    RegisterWay registerWay;
    String authCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterWay getRegisterWay() {
        return registerWay;
    }

    public void setRegisterWay(RegisterWay registerWay) {
        this.registerWay = registerWay;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}