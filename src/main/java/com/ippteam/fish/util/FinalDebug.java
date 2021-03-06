package com.ippteam.fish.util;

import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;

/**
 * Created by isunimp on 16/11/14.
 */
public class FinalDebug {

    static public final String SIGN_FAIL_APPKEY_OR_SIGN_NULL = "appkey or sign is null.";
    static public final String SIGN_FAIL_APPKEY_INVALID = "appkey is invalid.";
    static public final String SIGN_FAIL_SIGN_CONVERT_BUFFER_ERROR = "sign convert buffer error.";
    static public final String SIGN_FAIL_SIGN_CONVERT_BUFFER_FAIL = "sign convert buffer fail.";
    static public final String SIGN_FAIL_SIGN_BUFFER_DECRYPTED_FAIL = "sign buffer decrypted fail.";
    static public final String SIGN_FAIL_TIMEOUT = "expiredTime timeout.";
    static public final String SIGN_FAIL_API_ERROR = "api error.";
    static public final String SIGN_FAIL_BODY_ERROR = "body param is invalid.";
    static public final String SIGN_FAIL_TOKEN_INVALID = "token invalid.";
}
