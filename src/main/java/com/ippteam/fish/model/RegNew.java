package com.ippteam.fish.model;

/**
 * Created by isunimp on 16/11/15.
 */
public class RegNew {
    String account;
    String password;
    RegisterWay registerWay;
    String authcode;

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

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}