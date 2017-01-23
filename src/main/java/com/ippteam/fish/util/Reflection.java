package com.ippteam.fish.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by isunimp on 16/12/22.
 */
public class Reflection {

    /**
     * 对象值转移，用于pojo和entity之间
     *
     * @param toObject    被设置值的对对象
     * @param valueObject 值对象
     * @param ignoreNull  是否忽略null值
     */
    public static void objectValueTransfer(Object toObject, Object valueObject, boolean ignoreNull) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class toClazz = toObject.getClass();
        Class fromClazz = valueObject.getClass();
        Field[] toFields = toObject.getClass().getDeclaredFields();
        Field[] fromFields = valueObject.getClass().getDeclaredFields();

        for (Field field : fromFields) {
            for (Field field1 : toFields) {
                if (field.getName().equals(field1.getName()) &&
                        field.getType().getName().equals(field1.getType().getName())) {
                    String name = field1.getName();
                    String getMethodName = getMethodName(name);
                    String setMethodName = setMethodName(name);

                    // 获取值
                    Method getMethod = fromClazz.getMethod(getMethodName);
                    Object value = getMethod.invoke(valueObject);

                    if (value == null && ignoreNull) break;

                    // set值
                    Method setMethod = toClazz.getMethod(setMethodName, field.getType());
                    setMethod.invoke(toObject, value);
                    break;
                }
            }
        }
    }

    public static String getMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String setMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
