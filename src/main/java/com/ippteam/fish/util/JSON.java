package com.ippteam.fish.util;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.*;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;

/**
 * Created by pactera on 16/11/1.
 */
public class JSON {

    static public ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String JSONString, Class<T> clazz) throws Exception {
        return JSON.mapper.readValue(JSONString.getBytes(), clazz);
    }

    public static <T> T parse(Object object, Class<T> clazz) throws Exception {
        String string = JSON.stringify(object);
        return JSON.parse(string, clazz);
    }

    public static String stringify(Object obj) throws Exception {
        return JSON.mapper.writeValueAsString(obj);
    }

    public static <T> void addSerializer(Class pojoClazz, JsonSerializer<T> serializer) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(pojoClazz, serializer);
        mapper.registerModule(module);
    }
}