package com.ippteam.fish.util;

import java.awt.image.SampleModel;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pactera on 16/10/31.
 */
public class Verify {

    public static final String COMMON_FIELD = "flowID,initiator,";

    /**
     * 验证对象是否为NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
     *
     * @param obj     被验证的对象
     * @param message 异常信息
     */
    @SuppressWarnings("rawtypes")
    public static void notEmpty(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof String && obj.toString().trim().length() == 0) {
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            throw new IllegalArgumentException(message + " must be specified");
        }
    }

    public static boolean string(String string) {
        return string != null && string.length() > 0 ? true : false;
    }

    public static boolean userName(String userName) {
        String regExp = "^[a-zA-Z]\\w{5,17}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    public static boolean buffer(byte[] buff) {
        return buff != null && buff.length > 0 ? true : false;
    }

    public static boolean email(String email) {
        String regExp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean phone(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[^4])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}