package com.ippteam.fish.util.api.exception;

import com.ippteam.fish.util.api.BusinessStatus;

/**
 * Created by isunimp on 16/11/4.
 */
public class BusinessException extends RuntimeException {
    Integer code;
    String reason;

    public BusinessException(BusinessStatus businessStatus) {
        super(businessStatus.getDes());
        this.code = businessStatus.getCode();
        this.reason = businessStatus.getDes();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
