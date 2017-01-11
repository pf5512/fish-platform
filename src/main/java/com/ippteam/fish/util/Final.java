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
    public static final String NO_NEED_AUTH_URI_AUTHCODE_EMAIL = "authcode/email";

    // 注册方式
    public static final String REG_WAY_EMAIL = "email";
    public static final String REG_WAY_PHONE = "phone";
    public static final String REG_WAY_USERNAME = "username";

    // key
    public static final String REQUEST_ATTRIBUTE_SIGN = "sign";
    public static final String REQUEST_ATTRIBUTE_AES_SECRET_KEY = "secretKey";

    // 非业务异常
    public static final String EXCEPTION_REQUEST_URL_PARAMER_ERROE = "required request url paramer is error.";
    public static final String EXCEPTION_REQUEST_BODY_PARAMER_ERROE = "required request body paramer is error.";
    public static final String EXCEPTION_REQUEST_PARAMER_INVALID = "required request paramer is invalid.";
    public static final String EXCEPTION_REQUEST_ID_INVALID = "required request id is invalid.";
    public static final String EXCEPTION_REQUEST_METHOD_NOTSUPPORTED = "Request method not supported or Request not found.";
    public static final String EXCEPTION_UNKNOWN_EXCEPTION = "unknown exception.";
    public static final String EXCEPTION_SIGN_FAIL = "sign fail.";
    public static final String EXCEPTION_AES_DECRYPT_FAIL = "aes decrypt fail.";
}
