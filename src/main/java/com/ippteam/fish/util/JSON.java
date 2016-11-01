package com.ippteam.fish.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Stack;

/**
 * Created by pactera on 16/11/1.
 */
public class JSON {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String JSONString, Class<T> clazz) throws Exception {
        return JSON.mapper.readValue(JSONString.getBytes(), clazz);
    }

    public static <T> String stringify(T t) throws Exception {
        return JSON.mapper.writeValueAsString(t);
    }
}