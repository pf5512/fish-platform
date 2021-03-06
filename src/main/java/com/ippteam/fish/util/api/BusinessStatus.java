package com.ippteam.fish.util.api;

/**
 * Created by isunimp on 16/11/4.
 */
public enum BusinessStatus {

    DONE(00000, null),
    UNKNOWN_ERROR(10000, "unknown error"),

    UNDER_CONSTRUCTING(10001, "under-constructing"),
    UNDER_CONSTRUCTING_REGNEW_PHONE(10002, "phone regnew under-constructing"),

    USERNAME_EXISTING(10021, "username is existing"),
    USERNAME_INVALID(10022, "username is invalid"),

    EMAIL_EXISTING(10023, "email is existing"),
    EMAIL_INVALID(10024, "email is invalid"),

    PHONE_EXISTING(10025, "phone is existing"),
    PHONE_INVALID(10026, "phone is invalid"),

    USERNAME_OR_PASSWORD_INVALID(10027, "username or password is invalid"),
    AUTHCODE_INVALID(10028, "auth code is invalid"),
    ACCOUNT_NOT_EXIST(10029, "account not exist"),
    OAUTH_FAIL(10030,"oauth fail"),

    TOKEN_INVALID(10040, "token is invalid, please login again"),
    PERMISSION_DENIED(10041, "permission denied"),
    NOT_ADMINISTRATOR(10042, "not the administrator"),
    AES_DECRYPT_FAIL(10043, "aes decrypt fail");

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
