package com.ippteam.fish.util.api.entity;

/**
 * Created by pactera on 16/10/26.
 * 接口返回
 */
public class Result {
    Integer code;
    String reason;
    Object data;

    public Result(Integer code, String des, Object data) {
        this.code = code;
        this.reason = des;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public Object getData() {
        return data;
    }
}