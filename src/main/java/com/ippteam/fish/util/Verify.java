package com.ippteam.fish.util;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by isunimp on 16/10/31.
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
        return string != null && string.length() > 0;
    }

    public static boolean userName(String userName) {
        String regExp = "^[a-zA-Z]\\w{5,17}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    public static boolean buffer(byte[] buff) {
        return buff != null && buff.length > 0;
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

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null && obj2 != null) {
            return obj2 instanceof String && !string((String) obj2);
        }
        if (obj1 != null && obj2 == null) {
            return obj1 instanceof String && !string((String) obj1);
        }

        Class clazz1 = obj1.getClass();
        Class clazz2 = obj2.getClass();

        if (obj1 instanceof String && obj2 instanceof String) {
            String string1 = (String) obj1;
            String string2 = (String) obj2;
            return equals(string1, string2);
        } else if (obj1 instanceof Map && obj2 instanceof Map) {
            Map map1 = (Map) obj1;
            Map map2 = (Map) obj2;
            return equals(map1, map2);
        } else if (obj1 instanceof List && obj2 instanceof List) {
            List list1 = (List) obj1;
            List list2 = (List) obj2;
            return equals(list1, list2);
        }
        return obj1.equals(obj2);
    }

    public static boolean equals(String string1, String string2) {
        return !string(string1) && !string(string2) || string1.equals(string2);
    }

    public static boolean equals(Map map1, Map map2) {
        Set keys1 = map1.keySet();
        Iterator it1 = keys1.iterator();
        while (it1.hasNext()) {
            Object key = it1.next();
            Object v1 = map1.get(key);
            Object v2 = map2.get(key);
            if (!equals(v1, v2)) return false;
        }

        Set keys2 = map2.keySet();
        Iterator it2 = keys2.iterator();
        while (it2.hasNext()) {
            Object key = it2.next();
            if (!map1.containsKey(key)) {
                Object v = map2.get(key);
                if (!(v == null)) {
                    if (v instanceof String) {
                        if (string((String) v)) return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean equals(List list1, List list2) {
        if (list1.size() != list2.size()) return false;
        Iterator it1 = list1.iterator();
        while (it1.hasNext()) {
            Object v1 = it1.next();
            Iterator it2 = list2.iterator();
            if (!list2.contains(v1)) return false;
        }
        return true;
    }
}