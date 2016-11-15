package com.ippteam.fish.util;

/**
 * Created by isunimp on 16/11/15.
 */
public class UUID {

    public static String UUIDTrim() {
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

    public static String UUIDString() {
        return java.util.UUID.randomUUID().toString();
    }
}
