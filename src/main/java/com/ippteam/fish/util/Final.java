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

    // 非业务异常
    public static final String EXCEPTION_REQUEST_BODY_PARAM_MISSING = "required request body is missing.";
    public static final String EXCEPTION_REQUEST_BODY_PARAM_INVALID = "required request body is invalid.";
    public static final String EXCEPTION_REQUEST_METHOD_NOTSUPPORTED = "Request method not supported or Request not found.";
    public static final String EXCEPTION_UNKNOWN_EXCEPTION = "unknown exception.";
    public static final String EXCEPTION_SIGN_FAIL = "sign fail.";
}
