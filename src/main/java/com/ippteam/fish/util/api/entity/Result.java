package com.ippteam.fish.util.api.entity;

/**
 * Created by pactera on 16/10/26.
 */
public class Result {
    Integer code;
    String des;
    Object data;

    public Result(Integer code, String des, Object data) {
        this.code = code;
        this.des = des;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public Object getData() {
        return data;
    }
}