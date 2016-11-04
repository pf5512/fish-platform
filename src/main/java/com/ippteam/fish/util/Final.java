package com.ippteam.fish.util;

/**
 * Created by pactera on 16/10/28.
 */
public class Final {

    // 无需登录接口
    public static final String NO_NEED_AUTH_URI_LOGIN = "login";

    public static final String NO_NEED_AUTH_URI_REGNEW = "regnew";

    public static final String NO_NEED_AUTH_URI_RESETPWD = "resetpwd";

    public static final String NO_NEED_AUTH_URI_WEATHER = "weather";

    // 注册方式
    public static final String REG_WAY_EMAIL = "email";
    public static final String REG_WAY_PHONE = "phone";
    public static final String REG_WAY_USERNAME = "username";

    // 登陆凭证
    public static final String KEY_CREDENTIAL = "credential";


    // 业务异常Business exception
    public static final String BUSINESS_EXCEPTION_USERNAME_OR_PASSWORD_INVALID = "username or password is invalid";
    public static final String BUSINESS_EXCEPTION_PARAM_INVALID = "invalid body param";
    public static final String BUSINESS_EXCEPTION_USERNAME_EXISTING = "username is existing";
    public static final String BUSINESS_EXCEPTION_EMAIL_EXISTING = "email is existing";
    public static final String BUSINESS_EXCEPTION_PHONE_EXISTING = "phone is existing";

    public static final String UNKNOWN_ERROR = "unknown error";
    // 非业务异常

}
