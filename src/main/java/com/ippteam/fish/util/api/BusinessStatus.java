package com.ippteam.fish.util.api;

/**
 * Created by isunimp on 16/11/4.
 */
public enum BusinessStatus {

    DONE(00000, null),
    UNKNOWN_ERROR(10000, "unknown error"),

    USERNAME_EXISTING(10001, "username is existing"),
    USERNAME_INVALID(10002, "username is invalid"),

    EMAIL_EXISTING(10003, "email is existing"),
    EMAIL_INVALID(10004, "email is invalid"),

    PHONE_EXISTING(10005, "phone is existing"),
    PHONE_INVALID(10006, "phone is invalid"),

    USERNAME_OR_PASSWORD_INVALID(10004, "username or password is invalid");

    private int code;
    private String des;

    BusinessStatus(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
