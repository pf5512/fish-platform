package com.ippteam.fish.util.api.model;

/**
 * Created by pactera on 16/10/26.
 * 接口返回
 */
public class Result {
    // 业务状态码(0代表没有异常)
    Integer code;
    // 异常原因
    String reason;
    // 业务数据
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