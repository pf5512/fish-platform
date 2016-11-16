package com.ippteam.fish.util;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;
import java.util.Date;

/**
 * Created by isunimp on 16/11/16.
 */
public class Random {

    /**
     * 生成UUID字符串
     *
     * @return
     */
    public static String UUIDString() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 生成UUID（去掉'-'）
     *
     * @return
     */
    public static String _32String() {
        String uuidString = UUIDString();
        int len = uuidString.length();
        int i = -1;
        int bi = -1;
        char[] buff = new char[len];

        while (++i < len) {
            char c = uuidString.charAt(i);
            if (c != '-') {
                buff[++bi] = c;
            }
        }
        return new String(buff, 0, bi + 1);
    }

    public static long _6Number() {
        java.util.Random rad = new java.util.Random();
        return rad.nextInt(900000) + 100000;
    }
}
